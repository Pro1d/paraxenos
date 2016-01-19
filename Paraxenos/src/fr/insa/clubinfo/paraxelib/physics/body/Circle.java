package fr.insa.clubinfo.paraxelib.physics.body;

import fr.insa.clubinfo.paraxelib.physics.Body;

public class Circle extends Body {
	private double radius;

	public Circle(double radius) {
		super(Type.CIRCLE);
		this.setRadius(radius);
	}

	/**
	 * @params mass per squared distance unit. Default value could be 1.0
	 **/
	public double getMass(double massPerL2) {
		return getRadius() * getRadius() * Math.PI * massPerL2;
	}

	/**
	 * @return mass per squared distance unit. Default value could be 1.0
	 **/
	public double getMassPerAreaUnit(double mass) {
		return mass / (getRadius() * getRadius() * Math.PI);
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
}
