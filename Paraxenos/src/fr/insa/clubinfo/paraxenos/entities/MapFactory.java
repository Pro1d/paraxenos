package fr.insa.clubinfo.paraxenos.entities;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import fr.insa.clubinfo.paraxelib.utils.Size;


public class MapFactory {
	
	private static HashMap<String, MapCreator> maps = new HashMap<>();
	private static SortedSet<String> mapNames = new TreeSet<String>();
	
	
	public static void addMapCreator(String name, MapCreator mc) {
		maps.put(name, mc);
		mapNames.add(name);
	}
	
	public static Map createMap(String name, Size size) {
		if(!maps.containsKey(name))
			return null;
		
		return maps.get(name).create(size);
	}
	
	public static SortedSet<String> getMapNames() {
		return mapNames;
	}
}
