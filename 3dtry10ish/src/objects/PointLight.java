package objects;

import java.awt.Color;

public class PointLight extends LightSource {

	public PointLight() {
		this(0,0,0);
	}

	public PointLight(double x, double y, double z) {
		this(x, y, z, 80);
	}
	
	public PointLight(double x, double y, double z, double range) {
		super(x, y, z, range, Color.white);
	}
	
	public PointLight(double x, double y, double z, double range, Color color) {
		super(x, y, z, range, color);
	}
}