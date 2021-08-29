package renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Comparator;
import java.util.List;

import maths.Vec3;
import objects.Camera;
import objects.LightSource;
import renderer.vectors.PointConverter;
import scene.Scene;

public class Renderer {
	
	private int width, height;
	private Scene scene;
	private Graphics g;
	private boolean drawNormals = false;
	private boolean drawLights = true;
	private Camera cam;
	
	public Renderer() {
		super();
	}

	public void render(Scene scene, Graphics g) {
		this.scene = scene;
		this.g = g;
		cam = scene.getActiveCamera();
		List<Face> faces = scene.getFaces();
		/**
		 * sort
		 */
		faces.sort(new Comparator<Face>() {
			public int compare(Face a, Face b) {
				return b.getAverageDistance() - a.getAverageDistance() < 0 ? -1 : b.getAverageDistance() - a.getAverageDistance() > 0 ? 1 : faces.indexOf(a) > faces.indexOf(b) ? 1 : -1;
			}
		});
		/**
		 * render
		 */
		for (Face face : faces) {
			renderFace(face);
		}
		if(drawLights) {
			for (LightSource light : scene.getLightSources()) {
				renderLight(light);
			}
		}
	}
	
	private void renderLight(LightSource light) {
		LightSource clone = light.clone();
//		Vec3 point = cam.toSpace(clone.getLoc());
		cam.toSpace(clone.getLoc());
		if(clone.getLoc().x < 0) return;
		Point p = PointConverter.convertPoint(clone.getLoc(), width, height, cam.getFov());
		g.setColor(Color.yellow);
		g.fillOval(p.x, p.y, 5, 5);
	}
	
	private void renderFace(Face face) {
		Vec3 center = face.getCenter();
		double dist = center.getLength();
		if(dist > scene.getVisibility()) return;
		/**
		 * polygon
		 */
		Polygon poly = new Polygon();
		for (Vec3 point : face.getVertecies()) {
			if(point.x < 0) continue;
			Point p = PointConverter.convertPoint(point, width, height, cam.getFov());
			poly.addPoint(p.x, p.y);
		}
		/**
		 * Color / Lights
		 */
		Color c = face.getColor();
		if(scene.isUseMist()) {
			c = fadeMist(c, scene.getBg(), dist / scene.getVisibility());
		}
		Vec3 lightColor = getLightAt(center, face.getNormal());
		
		Color color = ColorHelper.multiply(c, lightColor);
		
		g.setColor(new Color(Math.max(scene.getBg().getRed(), color.getRed()), Math.max(scene.getBg().getGreen(), color.getGreen()), Math.max(scene.getBg().getBlue(), color.getBlue())));
		g.fillPolygon(poly);
		/**
		 * Debug
		 */
		if(drawNormals) {
			Point a = PointConverter.convertPoint(center, width, height, cam.getFov());
			Point b = PointConverter.convertPoint(center.clone().add(face.getNormal()), width, height, cam.getFov());
			g.setColor(Color.gray);
			g.drawLine(a.x, a.y, b.x, b.y);
		}
		if(face.isUseWireframe()) {
			renderWireframe(face);
		}
	}
	
	private Vec3 getLightAt(Vec3 loc, Vec3 normal) {
		double r = 0;
		double g = 0;
		double b = 0;
		for (LightSource lightSource : scene.getLightSources()) {
			LightSource clone = lightSource.clone();
//			clone.setLoc(cam.toSpace(clone.getLoc()));
			cam.toSpace(clone.getLoc());
			Vec3 c = clone.getLightAt(loc, normal);
//			System.out.println(clone.getLoc());
			r += c.x;
			g += c.y;
			b += c.z;
			
//			Point a = PointConverter.convertPoint(loc, width, height, cam.getFov());
//			Point d = PointConverter.convertPoint(loc.clone().add(normal), width, height, cam.getFov());
//			this.g.setColor(Color.gray);
//			this.g.drawLine(a.x, a.y, d.x, d.y);
//			
//			Point p = PointConverter.convertPoint(clone.getLoc(), width, height, cam.getFov());
//			this.g.setColor(Color.yellow);
//			this.g.fillOval(p.x, p.y, 5, 5);
		}
		return new Vec3(Math.min(1, r), Math.min(1, g), Math.min(1, b));
	}
	
	private void renderWireframe(Face face) {
		List<Vec3> verts = face.getVertecies();
		g.setColor(Color.black);
		for (int i = 0; i < verts.size() - 1; i++) {
			if(verts.get(i).x < 0 || verts.get(i + 1).x < 0) continue;
			Point p1 = PointConverter.convertPoint(verts.get(i), width, height, scene.getActiveCamera().getFov());
			Point p2 = PointConverter.convertPoint(verts.get(i + 1), width, height, scene.getActiveCamera().getFov());
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
	
	public Color fadeMist(Color color, Color mist, double progress) {
		progress = Math.min(1, progress);
		progress = Math.max(0, progress);
		int r3 = mist.getRed() - color.getRed();
		int g3 = mist.getGreen() - color.getGreen();
		int b3 = mist.getBlue() - color.getBlue();
		return new Color((int) (color.getRed() + progress * r3), (int) (color.getGreen() + progress * g3), (int) (color.getBlue() + progress * b3));
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}