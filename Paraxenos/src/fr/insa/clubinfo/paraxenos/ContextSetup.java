package fr.insa.clubinfo.paraxenos;

import fr.insa.clubinfo.paraxenos.entities.MapFactory;
import fr.insa.clubinfo.paraxenos.entities.maps.DefaultMapCreator;

public class ContextSetup {

	private static boolean staticFieldsInitialized = false; 
	
	public ContextSetup() {

	}

	public void setup() {
		if(!staticFieldsInitialized) {
			createColorScheme();
			initializeMapFactory();
			
			staticFieldsInitialized = true;
		}
	}
	
	private void initializeMapFactory() {
		MapFactory.addMapCreator("Default", new DefaultMapCreator());
	}

	private void createColorScheme() {

	}
}
