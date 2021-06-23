package objects;

import java.awt.Color;

public class Arrow extends MyObject{

	public Arrow() {
		this(0, 0, 0);
	}
	
	public Arrow(double x, double y, double z) {
		this(x, y, z, 1, Color.orange);
	}

	public Arrow(double x, double y, double z, double scale, Color color) {
		super(x, y, z);
		Cube base = new Cube(2.5,0,0,1);
		base.setColor(color);
		base.setScale(5, 0.2, 0.2);
		addChild(base);
		
		Cube left = new Cube(4.5, .6, 0, 0.4);
		left.setColor(color);
		left.setScale(5, .7, .5);
		left.getRot().z = -Math.PI / 4;
		addChild(left);
		
		Cube right = new Cube(4.5, -.6, 0, 0.4);
		right.setColor(color);
		right.setScale(5, .7, .5);
		right.getRot().z = Math.PI / 4;
		addChild(right);
		
		setUseWireFrame(false);
	}
}