package fr.insa.clubinfo.paraxenos.physics.body;

import fr.insa.clubinfo.paraxenos.physics.Body;

public class Circle extends Body {
	public double radius;

	public Circle(double radius) {
		super(Type.CIRCLE);
		this.radius = radius;
	}

	/**
	 * @params mass per squared distance unit. Default value could be 1.0
	 **/
	public double getMass(double massPerL2) {
		return radius * radius * Math.PI * massPerL2;
	}

	/**
	 * @return mass per squared distance unit. Default value could be 1.0
	 **/
	public double getMassPerAreaUnit(double mass) {
		return mass / (radius * radius * Math.PI);
	}
}
