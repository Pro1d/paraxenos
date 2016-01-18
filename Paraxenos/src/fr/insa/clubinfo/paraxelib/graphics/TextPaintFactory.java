package fr.insa.clubinfo.paraxelib.graphics;

import android.text.TextPaint;

public class TextPaintFactory {
	public static TextPaint createTextPaint(float height) {
		TextPaint tp = new TextPaint();
		// tp.setSubpixelText(true);
		tp.setAntiAlias(true);
		tp.setLinearText(true);
		tp.setTextSize(height);
		return tp;
	}
}
