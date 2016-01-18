package fr.insa.clubinfo.paraxenos.graphics;

import java.util.HashSet;
import java.util.Set;

import android.graphics.Canvas;

class RenderLayout {

	private Set<Drawable> drawables = new HashSet<Drawable>();

	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	public void removeDrawable(Drawable drawable) {
		drawables.remove(drawable);
	}

	public void removeAll() {
		drawables.clear();
	}

	public void render(Canvas canvas) {
		for (Drawable drawable : drawables) {
			drawable.draw(canvas);
		}
	}

}
