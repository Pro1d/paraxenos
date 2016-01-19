package fr.insa.clubinfo.paraxenos;

import fr.insa.clubinfo.paraxelib.graphics.Theme;
import fr.insa.clubinfo.paraxelib.graphics.ThemeProvider;
import fr.insa.clubinfo.paraxenos.entities.MapFactory;
import fr.insa.clubinfo.paraxenos.entities.maps.DefaultMapCreator;

public class ContextSetup {

	private static boolean staticFieldsInitialized = false;

	public ContextSetup() {

	}

	public void setup() {
		if (!staticFieldsInitialized) {
			createBaseTheme();
			initializeMapFactory();

			staticFieldsInitialized = true;
		}
	}

	private void initializeMapFactory() {
		MapFactory.addMapCreator("Default", new DefaultMapCreator());
	}

	private void createBaseTheme() {
		Theme t = ThemeProvider.getBase();
		t.setColor("game_bg", 0xff00ff00);
		t.setColor("game_border", 0xff555555);
	}
}
