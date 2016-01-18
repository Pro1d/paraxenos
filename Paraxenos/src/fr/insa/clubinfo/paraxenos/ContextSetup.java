package fr.insa.clubinfo.paraxenos;

import fr.insa.clubinfo.paraxenos.graphics.ColorScheme;
import fr.insa.clubinfo.paraxenos.graphics.ColorSchemeProvider;

public class ContextSetup {

	public ContextSetup() {

	}

	public void setup() {
		createColorScheme();
	}

	private void createColorScheme() {
		ColorScheme scheme = new ColorScheme();

		ColorSchemeProvider.setColorScheme(scheme);
		scheme.set("game_bg", "#edeae3");
		scheme.set("game_border", "#516087");
	}
}
