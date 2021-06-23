package objects;

import java.awt.Color;

import maths.Vec3;
import renderer.Face;

public class Cube extends MyObject {

	public Cube() {
		this(0, 0, 0);
	}
	
	public Cube(double x, double y, double z) {
		this(x, y, z, 5);
	}
	
	public Cube(double x, double y, double z, double s) {
		super(x, y, z);
		Vec3 p1 = new Vec3(s/2, -s/2, -s/2);
		Vec3 p2 = new Vec3(s/2, s/2, -s/2);
		Vec3 p3 = new Vec3(s/2, s/2, s/2);
		Vec3 p4 = new Vec3(s/2, -s/2, s/2);
		Vec3 p5 = new Vec3(-s/2, -s/2, -s/2);
		Vec3 p6 = new Vec3(-s/2, s/2, -s/2);
		Vec3 p7 = new Vec3(-s/2, s/2, s/2);
		Vec3 p8 = new Vec3(-s/2, -s/2, s/2);
		Vec3 normal1 = new Vec3(1, 0, 0);
		Vec3 normal2 = new Vec3(-1, 0, 0);
		Vec3 normal3 = new Vec3(0, 0, -1);
		Vec3 normal4 = new Vec3(0, -1, 0);
		Vec3 normal5 = new Vec3(0, 1, 0);
		Vec3 normal6 = new Vec3(0, 0, 1);
		addFaces(new Face(Color.white, normal1, p1, p2, p3, p4),
				new Face(Color.white, normal2, p5, p6, p7, p8),
				new Face(Color.white, normal3, p1, p2, p6, p5),
				new Face(Color.white, normal4, p1, p5, p8, p4),
				new Face(Color.white, normal5, p2, p6, p7, p3),
				new Face(Color.white, normal6, p4, p3, p7, p8));
	}
}