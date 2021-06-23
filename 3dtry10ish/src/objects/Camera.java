package objects;

import maths.EulerRotation;
import maths.EulerRotationMode;
import maths.Vec3;

public class Camera extends MyObject {

	private double fov = 120;
	
	public static final int STEER_MODE_ORBIT = 0;
	public static final int STEER_MODE_WASD = 1;
	public static final int STEER_MODE_STATIONARY = 2;
	private int steermode = STEER_MODE_ORBIT;
	
	private Vec3 centerOrbit = new Vec3();
	
	public Camera() {
		this(-2.5, 0, 1.5);
	}
	
	public Camera(double x, double y, double z) {
		super(x, y, z);
		this.setCameraTransform(true);
		((EulerRotation) getRot()).setMode(EulerRotationMode.ZYX_EULER);
		rotateOrbit(0, 0);
	}

	public double getFov() {
		return fov;
	}

	public void setFov(double fov) {
		this.fov = fov;
	}

	public int getSteermode() {
		return steermode;
	}

	public void setSteermode(int steermode) {
		this.steermode = steermode;
	}

	public Vec3 getCenterOrbit() {
		return centerOrbit;
	}

	public void setCenterOrbit(Vec3 centerOrbit) {
		this.centerOrbit = centerOrbit;
	}
	
	public void rotateOrbit(double x, double y) {
		x = Math.toRadians(x);
		y = Math.toRadians(y);
		Vec3 orbit = getLoc().clone().subtract(centerOrbit);
		orbit.rotateZ(x);
		orbit.rotateHeight(-y, 89);
		getLoc().setFrom(centerOrbit.clone().add(orbit));
		setRot(getLoc().rotToZUp(centerOrbit));
		((EulerRotation) getRot()).setMode(EulerRotationMode.ZYX_EULER);
	}
	
	public void moveOrbit(double x, double y) {
		Vec3 diff = getLoc().clone().subtract(centerOrbit);
		double speed = diff.getLength() / 700;
		//x
		Vec3 xMove = new Vec3(0, speed * x, 0);
		xMove.rotateZ(-getRot().z);
		getLoc().add(xMove);
		centerOrbit.add(xMove);
		//z
		Vec3 zMove = new Vec3(0, 0, -speed * y);
		zMove.rotateY(-getRot().y);
		zMove.rotateZ(-getRot().z);
		getLoc().add(zMove);
		centerOrbit.add(zMove);
		this.setRot(this.getLoc().rotToZUp(centerOrbit));
		((EulerRotation) getRot()).setMode(EulerRotationMode.ZYX_EULER);
//		this.getRot().setFrom();
	}
	
	public void scaleOrbit(double scale) {
		Vec3 orbit = getLoc().clone().subtract(centerOrbit);
		orbit.multiply(scale);
		this.getLoc().setFrom(centerOrbit.clone().add(orbit));
	}
}