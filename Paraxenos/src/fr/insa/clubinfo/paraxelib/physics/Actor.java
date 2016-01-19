package fr.insa.clubinfo.paraxelib.physics;

import fr.insa.clubinfo.paraxelib.utils.Vector;

public class Actor {
	private Vector position = new Vector(0, 0);

	public Vector getPosition() {
		return position;
	}

	public void setPosition(double px, double py) {
		this.position.set(px, py);
	}
	
	// boolean onCollision	
}
