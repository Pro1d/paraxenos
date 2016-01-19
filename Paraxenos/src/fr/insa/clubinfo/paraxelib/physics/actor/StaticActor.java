package fr.insa.clubinfo.paraxelib.physics.actor;

import fr.insa.clubinfo.paraxelib.physics.Actor;
import fr.insa.clubinfo.paraxelib.physics.Body;

public class StaticActor extends Actor {
	private Body body;

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}
