package fr.insa.clubinfo.paraxelib.graphics;

import java.util.HashSet;
import java.util.Set;

import android.graphics.Canvas;

class RenderLayout {

	private Set<Drawable> drawables = new HashSet<Drawable>();
	private float width = 1;

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
		int count = canvas.save();
		canvas.scale(width, width);
		for (Drawable drawable : drawables) {
			drawable.draw(canvas);
		}
		canvas.restoreToCount(count);
	}

	public void setWidth(float width) {
		this.width = width;
	}
}
