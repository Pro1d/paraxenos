package fr.insa.clubinfo.paraxelib.physics;

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
