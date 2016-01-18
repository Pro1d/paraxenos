package fr.insa.clubinfo.paraxenos.graphics;

public class ColorSchemeProvider {
	private static ColorScheme scheme;

	public static void setColorScheme(ColorScheme scheme) {
		ColorSchemeProvider.scheme = scheme;

	}

	public static ColorScheme getColorScheme() {
		return scheme;
	}

}
