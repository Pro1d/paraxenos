package fr.insa.clubinfo.paraxelib.physics.actor;

import fr.insa.clubinfo.paraxelib.physics.Actor;
import fr.insa.clubinfo.paraxelib.physics.body.Circle;
import fr.insa.clubinfo.paraxelib.utils.Vector;

public class MovingActor extends Actor {
	public Circle body;
	public Vector velocity = new Vector(0, 0);
	public Vector acceleration = new Vector(0, 0);
	public Vector moving = new Vector(0, 0);
	public double mass = 1.0;
	public double massPerAreaUnit = 1.0;
	
	public MovingActor(double radius) {
		body = new Circle(radius);
		mass = body.getMass(massPerAreaUnit);
	}
	
	public void setRadius(double radius) {
		// update the mass with the last mass per area unit
		body.radius = radius;
		this.mass = body.getMass(massPerAreaUnit);
	}
	
	public void setMass(double mass) {
		// update the mass per area distance unit with the last mass
		this.mass = mass;
		massPerAreaUnit = body.getMassPerAreaUnit(mass);
	}
	
	public void clearAcceleration() {
		acceleration.set(0,0);
	}
	public void addAcceleration(Vector acc) {
		acceleration.add(acc);
	}
	
	public void updateMovingVector(double time) {
		moving.set(velocity).mul(time);
	}
	
	public void addAccelerationToVelocity(double time) {
		velocity.add(acceleration.x*time, acceleration.y*time);
	}

	public void move(double dt) {
		position.add(moving.x*dt, moving.y*dt);
	}
}
