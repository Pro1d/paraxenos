package fr.insa.clubinfo.paraxelib.graphics;

public class ThemeProvider {
	private static Theme baseTheme = Theme.getBaseTheme();
	private static Theme theme = baseTheme;

	public static void setTheme(Theme newTheme) {
		theme = newTheme;
	}

	public static Theme getBase() {
		return baseTheme;
	}

	public static Theme getTheme() {
		return theme;
	}
}
