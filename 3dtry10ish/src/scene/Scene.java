package scene;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import maths.Vec3;
import objects.Camera;
import objects.Cube;
import objects.Drone;
import objects.LightSource;
import objects.MyObject;
import objects.PointLight;
import renderer.Face;

public class Scene {

	private List<MyObject> objects;
	private Color backgroundColor;
	private double visibility = 400;
	private boolean useMist = true;
	private Camera activeCamera;
	private List<LightSource> lightSources = new ArrayList<>();
	private boolean isStartup = false;
	
	public Scene() {
		super();
		
		this.objects = new ArrayList<>();
		this.activeCamera = new Camera();
		this.backgroundColor = new Color(5, 10, 14);
	}
	
	public void animate() {
		if(objects.size() == 0) {
			return;
		}
		objects.get(2).getLoc().x = Math.tan(System.currentTimeMillis() / 3000.0) * 10;
		
		objects.get(3).getLoc().x = Math.tan((System.currentTimeMillis() + 4000)  / 3000.0) * 10;
		
		objects.get(4).getLoc().x = Math.sin((System.currentTimeMillis()) / 1000.0) * 40;
		objects.get(4).getLoc().y = Math.cos((System.currentTimeMillis()) / 1000.0) * 40;
	}
	
	public void initStartup() {
		isStartup = true;
		Cube defaultCube = new Cube();
		addObject(defaultCube);
		defaultCube.setVisible(false);
		defaultCube.getScale().z = 1;
		int a = 6;
		double z = -5;
		for (int x = -a; x < a; x++) {
			for (double y = -a / 1.5; y < a / 1.5; y++) {
				Cube c = new Cube(x * 10, y * 10, z - Math.tan(Math.sqrt(x*x + y*y) * 0.3) * 10, 5);
				defaultCube.addChild(c);
			}
		}
		Drone drone = new Drone();
		addObject(drone);
		
		PointLight p = new PointLight(0, 0, 10);
		p.setColor(new Color(100, 100, 255));
		addObject(p);
		PointLight p2 = new PointLight(0, 0, 10);
		addObject(p2);
		p2.setColor(new Color(255, 100, 100));
		
		PointLight p3 = new PointLight(0, 0, 10);
		addObject(p3);
		p3.setColor(new Color(0, 0, 255));
	}
	
	public List<Face> getFaces() {
		if(isStartup) {			
			animate();
		}
		List<Face> faces = new ArrayList<>();
		for (MyObject object : objects) {
			for (Face face : object.getFaces()) {
				faces.add(activeCamera.toSpace(face));
			}
		}
		return faces;
	}
	
	public void setActiveCamera(Camera camera) {
		activeCamera = camera;
	}
	
	public Camera getActiveCamera() {
		return activeCamera;
	}
	
	public boolean addObject(MyObject object) {
		if(object instanceof LightSource) {
			lightSources.add((LightSource) object);
		}
		return objects.add(object);
	}
	
	public boolean removeObject(MyObject object) {
		if(object instanceof LightSource) {
			lightSources.remove((LightSource) object);
		}
		return objects.remove(object);
	}

	public List<MyObject> getObjects() {
		return objects;
	}

	public void setObjects(List<MyObject> objects) {
		this.objects = objects;
	}

	public Color getBg() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public double getVisibility() {
		return visibility;
	}

	public void setVisibility(double visibility) {
		this.visibility = visibility;
	}

	public boolean isUseMist() {
		return useMist;
	}

	public void setUseMist(boolean useMist) {
		this.useMist = useMist;
	}

	public List<LightSource> getLightSources() {
		return lightSources;
	}
	
	public Vec3 getLightAt(Vec3 vec, Vec3 normal) {
		double r = 0;
		double g = 0;
		double b = 0;
		for (LightSource lightSource : lightSources) {
			Vec3 c = lightSource.getLightAt(vec, normal);
			r += c.x;
			g += c.y;
			b += c.z;
		}
		return new Vec3(r, g, b);
	}
	
	public void empty() {
		objects.clear();
		lightSources.clear();
	}
}