package fr.insa.clubinfo.paraxelib.physics.actor;

import fr.insa.clubinfo.paraxelib.physics.Actor;
import fr.insa.clubinfo.paraxelib.physics.body.Circle;
import fr.insa.clubinfo.paraxelib.utils.Vector;

public class MovingActor extends Actor {
	private Circle body;
	private Vector velocity = new Vector(0, 0);
	protected Vector acceleration = new Vector(0, 0);
	private Vector moving = new Vector(0, 0);
	private double mass = 1.0;
	protected double massPerAreaUnit = 1.0;
	
	public MovingActor(double radius) {
		setBody(new Circle(radius));
		setMass(getBody().getMass(massPerAreaUnit));
	}
	
	public void setRadius(double radius) {
		// update the mass with the last mass per area unit
		getBody().setRadius(radius);
		this.setMass(getBody().getMass(massPerAreaUnit));
	}
	
	public void setMass(double mass) {
		// update the mass per area distance unit with the last mass
		this.mass = mass;
		massPerAreaUnit = getBody().getMassPerAreaUnit(mass);
	}
	
	public void clearAcceleration() {
		acceleration.set(0,0);
	}
	public void addAcceleration(Vector acc) {
		acceleration.add(acc);
	}
	
	public void updateMovingVector(double time) {
		getMoving().set(getVelocity()).mul(time);
	}
	
	public void addAccelerationToVelocity(double time) {
		getVelocity().add(acceleration.x*time, acceleration.y*time);
	}

	public void move(double dt) {
		getPosition().add(getMoving().x*dt, getMoving().y*dt);
	}

	public Circle getBody() {
		return body;
	}

	public void setBody(Circle body) {
		this.body = body;
	}

	public Vector getMoving() {
		return moving;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(double vx, double vy) {
		this.velocity.set(vx, vy);
	}

	public double getMass() {
		return mass;
	}
}
