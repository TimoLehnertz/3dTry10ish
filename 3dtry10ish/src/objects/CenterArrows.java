package objects;

import java.awt.Color;

public class CenterArrows extends MyObject {

	public CenterArrows() {
		this(1);
	}
	
	public CenterArrows(double scale) {
		super();
		Arrow xArrow = new Arrow(0,0,0,scale, Color.red);
		Arrow yArrow = new Arrow(0,0,0,scale, Color.green);
		yArrow.getRot().z = Math.PI / 2;
		Arrow zArrow = new Arrow(0,0,0,scale, Color.blue);
		zArrow.getRot().y = -Math.PI / 2;
		addChild(xArrow);
		addChild(yArrow);
		addChild(zArrow);
	}
}
