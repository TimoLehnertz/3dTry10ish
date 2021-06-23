package renderer;

import java.awt.Color;

import maths.Vec3;

public class ColorHelper {

	public static final double TO_VEC = 1.0 / 255.0;
	
	public static Color multiply(Color a, Vec3 b) {
		return new Color((int) (a.getRed() * b.x), (int) (a.getGreen() * b.y), (int) (a.getBlue() * b.z));
	}
	
	public static Vec3 toVec(Color c) {
		return new Vec3(c.getRed() * TO_VEC, c.getGreen() * TO_VEC, c.getBlue() * TO_VEC);
	}
}
