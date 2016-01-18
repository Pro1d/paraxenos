package fr.insa.clubinfo.paraxenos.graphics;

import java.util.HashMap;

import android.graphics.Color;

public class ColorScheme {

	private HashMap<String, Integer> colors;

	public ColorScheme() {
		colors = new HashMap<String, Integer>();
	}

	public int get(String colorName, int defaultColor) {
		Integer color = colors.get(colorName);
		return color != null ? color : defaultColor;
	}

	public int get(String colorName) {
		return colors.get(colorName);
	}

	public void set(String colorName, int color) {
		colors.put(colorName, color);
	}

	public void set(String colorName, String color) {
		colors.put(colorName, Color.parseColor(color));
	}
}
