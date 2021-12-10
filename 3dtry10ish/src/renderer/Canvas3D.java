package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import input.ClickType;
import input.Controlls;
import objects.Camera;
import objects.MyObject;
import scene.Scene;

public class Canvas3D extends Canvas implements Runnable, ComponentListener {

	private static final long serialVersionUID = 1L;
	
	private List<Scene> scenes = new ArrayList<>();
	private Scene activeScene;
	private Renderer renderer = new Renderer();
	private Thread thread;
	private volatile boolean running = false;
	private Controlls controlls;
	private long startMs = 0;
	
	public Canvas3D(boolean useDefaultScene) {
		super();
		setBackground(Color.DARK_GRAY);
		addComponentListener(this);
		if(useDefaultScene) {
			Scene startScene = new Scene();
			startScene.initStartup();
			addScene(startScene);
			loadScene(0);
		} else {
			addScene(new Scene());
			loadScene(0);
		}
	}
	
	public synchronized void start() {
		Timer t = new Timer(1000, e -> {
			((Timer) e.getSource()).stop();
			controlls = new Controlls(this);
			startMs = System.currentTimeMillis();
			running = true;
			thread = new Thread(this, "Display" + Math.random());
			thread.start();
		});
		t.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 40;
		double delta = 0;
//		int frames = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				delta--;
				if(delta < 2) {
					render();
//					frames++;
				}
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
//				System.out.println(frames + " FPS");
//				frame.setTitle(title + " | " + frames + " fps");
//				frames = 0;
			}
			if(!isDisplayable()) {
				running = false;
			}
		}
		stop();
	}

	private void render() {
		if(!running) return;
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			try {
				createBufferStrategy(3);
				revalidate();
				repaint();
			} catch(Exception e) {
				e.printStackTrace();;
			}
			return;
		}
		/**
		 * background
		 */
		try {
			Graphics g = bs.getDrawGraphics();
			g.setColor(activeScene.getBg());
			g.fillRect(0, 0, getWidth(), getHeight());
			renderer.render(activeScene, g);
			long now = System.currentTimeMillis();
			double fadeIn = 3000;
			if(now - startMs <= fadeIn && now - startMs >= 0) {
				double progress = (now - startMs) / fadeIn;
				progress = Math.sqrt(1 - Math.pow(progress - 1, 2));//easeOutCirc
				progress = (progress < 0.5) ? 2 * progress * progress : 1 - Math.pow(-2 * progress + 2, 2) / 2;//easeInOutQuad
				g.setColor(new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(), (int) ((1 - progress) * 255)));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
			g.dispose();
			try {
				bs.show();
			} catch(Exception e) {
//				e.printStackTrace();
			}
		} catch(Exception e) {
			running = false;
			e.printStackTrace();
		}
	}
	
	boolean prevClicked = false;
	int initialX;
	int initialY;
	private void update() {
		Camera cam = activeScene.getActiveCamera();
		switch(cam.getSteermode()) {
		case Camera.STEER_MODE_ORBIT:
			int deltaX = 0;
			int deltaY = 0;
			if(!prevClicked && controlls.isButtonDown(ClickType.MiddleClick)) {
				initialX = controlls.getX();
				initialY = controlls.getY();
			}
			prevClicked = controlls.isButtonDown(ClickType.MiddleClick);
			if(prevClicked) {
				deltaX = initialX - controlls.getX();
				deltaY = initialY - controlls.getY();
				initialX = controlls.getX();
				initialY = controlls.getY();
			}
			if(controlls.isKeyDown(KeyEvent.VK_SHIFT)) {
				/**
				 * move
				 */
				cam.moveOrbit(deltaX, deltaY);
			} else if(prevClicked) {
				/**
				 * rotate
				 */
				cam.rotateOrbit(deltaX * 0.5, deltaY * 0.5);
			}
			/**
			 * zoom
			 */
			while(controlls.isScrollingDown()) {
				cam.scaleOrbit(0.8);
			}
			while(controlls.isScrollingUp()) {
				cam.scaleOrbit(1.2);
			}
			break;
		}
	}
	
	public boolean addObject(MyObject object) {
		return activeScene.addObject(object);
	}
	
	public boolean addScene(Scene scene) {
		return scenes.add(scene);
	}
	
	public boolean removeScene(Scene scene) {
		return scenes.remove(scene);
	}
	
	public boolean loadScene(int index) {
		if(index < 0 || index >= scenes.size()) {
			return false;
		}
		activeScene = scenes.get(index);
		return true;
	}
	
	public Scene getActiveScene() {
		return activeScene;
	}

	public List<Scene> getScenes() {
		return scenes;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		renderer.setWidth(getWidth());
		renderer.setHeight(getHeight());
		revalidate();
		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
		renderer.setWidth(getWidth());
		renderer.setHeight(getHeight());
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
}