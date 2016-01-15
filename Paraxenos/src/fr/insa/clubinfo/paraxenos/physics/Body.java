package fr.insa.clubinfo.paraxenos.physics;

public class Body {
	// Type
	protected enum Type {
		CIRCLE, POLYGON, SET
	};

	protected Type type;

	protected Body(Type type) {
		this.type = type;
	}
}
