package objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maths.Vec3;
import renderer.Face;
import renderer.vectors.Transform;

public class MyObject extends Transform {
	
	protected List<Face> faces = new ArrayList<>();
	private List<MyObject> children = new ArrayList<>();
	protected boolean visible = true;
	
	public MyObject() {
		this(0, 0, 0);
	}
	
	public MyObject(double x, double y, double z) {
		super(new Vec3(x, y, z));
	}
	
	public void addFaces(Color color, Face... faces) {
		for (Face face : faces) {
			face.setColor(color);
		}
		this.faces.addAll(Arrays.asList(faces));
	}
	
	public void addFaces(Face... faces) {
		this.faces.addAll(Arrays.asList(faces));
	}

	public List<Face> getFaces() {
		List<Face> newFaces = new ArrayList<>();
		for (MyObject child : children) {
			for (Face face : child.getFaces()) {
				newFaces.add(toSpace(face));
			}
		}
		if(!visible) {
			return newFaces;
		}
		for (Face face : this.faces) {
			newFaces.add(toSpace(face));
		}
		return newFaces;
	}
	
//	public void rotate(double x, double y, double z) {
//		this.getRot().add(new Vec3(x, y, z));
//	}
//	
//	public void rotate(Vec3 rot) {
//		this.getRot().add(rot);
//	}
	
	public void move(double x, double y, double z) {
		this.getLoc().add(new Vec3(x, y, z));
	}
	
	public void move(Vec3 translation) {
		this.getLoc().add(translation);
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}
	
	public boolean addChild(MyObject child) {
		if(child instanceof LightSource) {
			System.err.println("A light source cannot be a children yet :/");
			return false;
		}
		return children.add(child);
	}
	
	public boolean removeChild(MyObject child) {
		return children.remove(child);
	}

	public List<MyObject> getChildren() {
		return children;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setColor(Color c) {
		for (Face face : faces) {
			face.setColor(c);
		}
	}
	
	public void setUseWireFrame(boolean useWireFrame) {
		setUseWireFrame(useWireFrame, true);
	}

	public void setUseWireFrame(boolean useWireFrame, boolean recursive) {
		if(recursive) {
			for (MyObject object : children) {
				object.setUseWireFrame(useWireFrame, true);
			}
		}
		for (Face face : faces) {
			face.setUseWireframe(useWireFrame);
		}
	}
}