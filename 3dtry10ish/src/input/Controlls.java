package input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controlls implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	private int mouseX = -1;
	private int mouseY = -1;
	private Map<Integer, ClickType> mouseB = new HashMap<>();
	private int scroll = 0;
	private static Map<Integer, ClickType> clickTypes = new HashMap<>();
	private List<Integer> keys = new ArrayList<>();
	private boolean mouseInside = false;
	static {
		clickTypes.put(1, ClickType.LeftClick);
		clickTypes.put(2, ClickType.MiddleClick);
		clickTypes.put(3, ClickType.RightClick);
		clickTypes.put(4, ClickType.BackPage);
		clickTypes.put(5, ClickType.ForwardPage);
	}
	
	public Controlls(Component container) {
		super();
		container.addMouseListener(this);
		container.addMouseMotionListener(this);
		container.addMouseWheelListener(this);
		container.addKeyListener(this);
	}
	
	public int getX() {
		return mouseX;
	}
	
	public int getY() {
		return mouseY;
	}
	
	public static ClickType getClickType(int button) {
		if(!clickTypes.containsKey(button)) {
			return ClickType.Unknown;
		}
		return clickTypes.get(button);
	}
	
	public boolean isScrollingUp() {
		if(scroll <= 0) return false;
		scroll = Math.max(0, scroll - 1);
		return scroll + 1 > 0;
	}
	
	public boolean isScrollingDown() {
		if(scroll >= 0) return false;
		scroll = Math.min(0, scroll + 1);
		return scroll - 1 < 0;
	}
	
	public Map<Integer, ClickType> getButtons() {
		return mouseB;
	}
	
	public boolean isButtonDown(ClickType type) {
		return mouseB.containsValue(type);
	}
	
	public boolean isKeyDown(int keyCode) {
		return keys.contains(keyCode);
	}
	
	public boolean isMouseInside() {
		return mouseInside;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseB.put(e.getButton(), clickTypes.get(e.getButton()));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseB.remove(e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInside = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInside = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!keys.contains(e.getKeyCode()))
			keys.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys.remove(keys.indexOf(e.getKeyCode()));
	}
}