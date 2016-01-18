package fr.insa.clubinfo.paraxenos.navigation;

import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.text.TextPaint;
import fr.insa.clubinfo.paraxenos.graphics.Drawable;
import fr.insa.clubinfo.paraxenos.graphics.TextPaintFactory;

public class BasicMenuItem implements MenuItem, Drawable {

	public interface Callback {
		public void onSelected();
	}

	private Callback callback;
	private String label;
	private TextPaint paint;
	private int baseColor;
	private int highlightColor;
	private float height = 0.1f;

	public BasicMenuItem(String label, int base, int highlight) {
		this.label = label;
		baseColor = base;
		highlightColor = highlight;
		paint = TextPaintFactory.createTextPaint(height);
		paint.setTextAlign(Align.CENTER);
		paint.setColor(base);

	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public void setLabel(String newLabel) {
		label = newLabel;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText(label, 0, height / 2, paint);
	}

	@Override
	public void onEntered() {
		paint.setColor(highlightColor);
	}

	@Override
	public void onLeft() {
		paint.setColor(baseColor);
	}

	@Override
	public void onSelected() {
		if (callback != null) {
			callback.onSelected();
		}
	}

	public void setTextSize(float textSize) {
		paint.setTextSize(textSize);
		height = textSize;
	}

	public void setFont(Typeface typeface) {
		paint.setTypeface(typeface);

	}

}
