package objects;

import java.awt.Color;

import maths.Vec3;

public class Drone extends MyObject {

	private final Color baseColor = Color.gray;
	private final Color chipColor = Color.green;
	private final Color motorColor = Color.white;
	
	public Drone() {
		this(0, 0, 0, 4);
	}

	public Drone(double x, double y, double z, int arms) {
		super(x, y, z);
		/**
		 * base
		 */
		Cube base = new Cube(0,0,0,1);
		base.getScale().z = 0.3;
		base.getScale().x = 4;
		base.setColor(baseColor);
		addChild(base);
		
		/**
		 * chip
		 */
		Cube chip = new Cube(0,0,0.2,.5);
		chip.getScale().z = 0.1;
		chip.setColor(chipColor);
		addChild(chip);
		
		/**
		 * arms
		 */
		for (double deg = 360 / arms - 360/ arms * 0.5; deg <= 360; deg += 360 / arms) {
			MyObject armParent = new MyObject();
			Cube arm = new Cube(3, 0, -0.3, 1);
			arm.setColor(baseColor);
			arm.setScale(new Vec3(6, 0.3, 0.3));
			armParent.getRot().z = Math.toRadians(deg);
			armParent.addChild(arm);
			addChild(armParent);
			
			Cube motor = new Cube(6, 0, 0, 0.5);
			motor.setColor(motorColor);
			armParent.addChild(motor);
		}
		setUseWireFrame(true);
		/**
		 * arrow
		 */
		Arrow arrow = new Arrow(2, 0, 0);
		addChild(arrow);
	}
}