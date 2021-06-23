package main;

import maths.EulerRotation;
import maths.EulerRotationMode;
import maths.Quaternion;
import maths.Vec3;

public class Main {
	
	public static void main(String[] args) {
//		Canvas3D canvas = new Canvas3D(true);
//		Frame frame = new Frame();
//		frame.add(canvas);
//		canvas.start();
//		
//		PointLight p = new PointLight(-5, 0, 5);
//		s.addObject(p);
//		Cube c = new Cube(0,0,0);
//		c.getRot().z = Math.PI;
//		Cube c2 = new Cube(0, 6, 0);
//		c.addChild(c2);
//		
//		c.setUseWireFrame(true);
//		s.addObject(c);
		
//		Vec3 v = new  Vec3(1, 0, 0);
//		Quaternion q = new Quaternion(new Vec3(0, 0, 1), Math.PI / 2);
//		q.rotate(v);
//		System.out.println(v);
//		q.rotateReverse(v);
//		System.out.println(v);
		
//		Quaternion q1 = new Quaternion(new Vec3(0, 0, 1), Math.PI / 2);
//		Vec3 v1 = new Vec3(1,0,0);
//		q1.rotate(v1);
//		System.out.println(v1);
//		
//		Quaternion q2 = new Quaternion(new Vec3(0, 0, 1), Math.PI);
//		Quaternion q3 = Quaternion.slerp(q1, q2, 0.5);
//		Vec3 v2 = new Vec3(1,0,0);
//		q3.rotate(v2);
//		System.out.println(v2);
		
		Quaternion a = new Quaternion(new EulerRotation(0,0, Math.PI / 2, EulerRotationMode.ZYX_EULER));
		Vec3 v = new Vec3(1,0,0);
		a.rotate(v);
		System.out.println(v);
	}
}