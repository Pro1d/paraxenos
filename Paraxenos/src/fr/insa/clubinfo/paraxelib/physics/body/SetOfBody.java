package fr.insa.clubinfo.paraxelib.physics.body;

import java.util.ArrayList;

import fr.insa.clubinfo.paraxelib.physics.Body;

public class SetOfBody extends Body {

	ArrayList<Body> set = new ArrayList<>();

	protected SetOfBody() {
		super(Type.SET);
	}

	public void addBody(Body b) {
		if (b instanceof SetOfBody)
			throw new RuntimeException("SetOfBody cannot contain a SetOfBody");
		set.add(b);
	}

	public int getCount() {
		return set.size();
	}

	public Body getBody(int index) {
		return set.get(index);
	}
}
