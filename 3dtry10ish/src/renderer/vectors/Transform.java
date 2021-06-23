package renderer.vectors;

import maths.EulerRotation;
import maths.Rotation;
import maths.Vec3;
import renderer.Face;

public class Transform extends maths.Transform {

	public Transform() {
		this(new Vec3());
	}
	
	public Transform(Vec3 loc) {
		this(loc, new EulerRotation(), new Vec3(1,1,1));
	}
	
	public Transform(Vec3 loc, Rotation rot, Vec3 scale) {
		super();
		this.loc = loc;
		this.rot = rot;
		this.scale = scale;
	}
	
	public Face toSpace(Face inFace) {
		Face face = inFace.clone();
		for (Vec3 vec : face.getVertecies()) {
			toSpace(vec);
		}
		toSpace(face.getNormal(), true);
		return face;
	}
}