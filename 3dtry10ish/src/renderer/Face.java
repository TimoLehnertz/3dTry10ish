package renderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maths.Vec3;

public class Face {

	private List<Vec3> vertecies;
	private Vec3 normal;
	private boolean useWireframe = false;
	Color color;
	
	public Face() {
		super();
		this.color = Color.white;
		this.vertecies = new ArrayList<>();
	}
	
	public Face(Vec3 normal, Vec3... vertecies) {
		this(Color.white, normal, Arrays.asList(vertecies));
	}
	
	public Face(Color color, Vec3 normal, Vec3... vertecies) {
		this(color, normal, Arrays.asList(vertecies));
	}
	
	public Face(Color color, Vec3 normal, List<Vec3> vertecies) {
		super();
		this.color = color;
		this.normal = normal;
		this.vertecies = vertecies;
	}
	
	public double getAverageX() {
		double sum = 0;
		for (Vec3 point : vertecies) {
			sum += point.x;
		}
		return sum / vertecies.size();
	}

	public List<Vec3> getVertecies() {
		return vertecies;
	}

	public void setVertecies(List<Vec3> vertecies) {
		this.vertecies = vertecies;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getAverageDistance() {
		double sum = 0;
		for (Vec3 point : vertecies) {
			sum += point.getLength();
		}
		return sum / vertecies.size();	
	}
	
	public double getMaxX() {
		double max = -100000;
		for (Vec3 vec3 : vertecies) {
			max = Math.max(vec3.x, max);
		}
		return max;
	}

	public double getMinX() {
		double min = 100000;
		for (Vec3 vec3 : vertecies) {
			min = Math.min(vec3.x, min);
		}
		return min;
	}

	public boolean isUseWireframe() {
		return useWireframe;
	}

	public void setUseWireframe(boolean useWireframe) {
		this.useWireframe = useWireframe;
	}
	
	public Vec3 getNormal() {
		return normal;
	}
	
	public Vec3 getCenter() {
		Vec3 center = new Vec3();
		for (Vec3 vertex : vertecies) {
			center.add(vertex);
		}
		return center.divide(vertecies.size());
	}

	public void setNormal(Vec3 normal) {
		this.normal = normal;
	}

	@Override
	public Face clone() {
		Face clone = new Face();
		for (Vec3 vec3 : vertecies) {
			clone.vertecies.add(vec3.clone());
		}
		clone.normal = normal.clone();
		clone.useWireframe = useWireframe;
		clone.setColor(color);
		return clone;
	}
}