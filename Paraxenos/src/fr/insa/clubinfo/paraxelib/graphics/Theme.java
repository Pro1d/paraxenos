package fr.insa.clubinfo.paraxelib.graphics;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.util.Log;

public class Theme {

	private String name;
	private Theme parent;
	private Map<String, Integer> colors = new HashMap<>();
	private static Theme baseTheme;

	public Theme(String name, Theme parent) {
		if (baseTheme != null && parent == null) {
			throw new RuntimeException("A Theme must have a parent Theme. Use theme ThemeProvider.getBaseTheme to get the base instance");
		}
		this.name = name;
		this.parent = parent;
	}

	public Theme getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public void setColor(String path, int color) {
		colors.put(path, color);
	}

	public int getColor(String path) {
		Integer color = recursiveSearchColor(path);
		if (color == null) {
			Log.w("Theme", "Color " + path + " doesn't exists");
			color = Color.parseColor("#ff00ff");
		}
		return color;
	}

	private Integer recursiveSearchColor(String path) {
		Integer color = colors.get(path);
		if (color == null && parent != null) {
			return parent.getColor(path);
		}
		return color;
	}

}
