package objects;

import java.awt.Color;

import maths.Vec3;
import renderer.ColorHelper;

public class LightSource extends MyObject {

	/**
	 * the area that this light has effect in
	 * falloff till this range is linear
	 */
	private double range;
	
	/**
	 * Color of this light
	 */
	private Color color;
	
	
	public LightSource() {
		this(0, 0, 0, 500, Color.WHITE);
	}

	public LightSource(double x, double y, double z, double range, Color color) {
		super(x, y, z);
		this.range = range;
		this.color = color;
	}
	
	/**
	 * Default implementation for Point light
	 * Meant to be ovverriden
	 * @param vec Vector to check this light for
	 * @return Color of light at that point
	 */
	public Vec3 getLightAt(Vec3 vec, Vec3 normal) {
		if(!isVisible()) return new Vec3();
		double distance = vec.getDistanceFrom(this);
		double intensity = 1 - (distance / range);
		intensity = Math.max(0, intensity);
		Vec3 path = getLoc().clone().subtract(vec);
		double normalShadow = path.toUnitLength().dotProduct(normal.clone().toUnitLength());
		normalShadow = Math.max(0, normalShadow);
		intensity *= normalShadow;
		intensity = Math.min(1, intensity);
		Vec3 color1 = ColorHelper.toVec(this.color);
		return new Vec3(color1.x * intensity, color1.y * intensity, color1.z * intensity);
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public LightSource clone() {
		LightSource clone = new LightSource(0, 0, 0, range, color);
		clone.setTransformFrom(this);
		return clone;
	}
}