package renderer.vectors;

import java.awt.Point;

import maths.Vec3;

public class PointConverter {

	public static Point convertPoint(Vec3 point3D, int width, int height, double fov) {
		double x2d = point3D.y / (point3D.x * (90 / (180 - fov)));
		double y2d = point3D.z / (point3D.x * (90 / (180 - fov)));
		
		int maxSize = Math.max(width, height);
		
		x2d = (int) (width / 2 + x2d * maxSize);
		y2d = (int) (height / 2 - y2d * maxSize);
		
		Point point2D = new Point((int) x2d, (int) y2d);
		
		return point2D;
	}
	
	public static void rotateAxisX(Vec3 p, boolean cw, double degrees) {
		double radius = Math.sqrt(p.y*p.y + p.z*p.z);
		double theta = Math.atan2(p.z, p.y);
		theta += 2 * Math.PI / 360 * degrees * (cw?-1:1);
		p.y = radius * Math.cos(theta);
		p.z = radius * Math.sin(theta);
	}
	
	public static void rotateAxisY(Vec3 p, boolean cw, double degrees) {
		double radius = Math.sqrt(p.x*p.x + p.z*p.z);
		double theta = Math.atan2(p.x, p.z);
		theta += 2 * Math.PI / 360 * degrees * (cw?-1:1);
		p.x = radius * Math.sin(theta);
		p.z = radius * Math.cos(theta);
	}
	
	public static void rotateAxisZ(Vec3 p, boolean cw, double degrees) {
		double radius = Math.sqrt(p.y*p.y + p.x*p.x);
		double theta = Math.atan2(p.y, p.x);
		theta += 2 * Math.PI / 360 * degrees * (cw?-1:1);
		p.y = radius * Math.sin(theta);
		p.x = radius * Math.cos(theta);
	}
}