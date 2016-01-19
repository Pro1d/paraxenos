package fr.insa.clubinfo.paraxenos.entities.maps;

import fr.insa.clubinfo.paraxelib.physics.body.Polygon;
import fr.insa.clubinfo.paraxelib.utils.Size;
import fr.insa.clubinfo.paraxenos.entities.Map;
import fr.insa.clubinfo.paraxenos.entities.MapCreator;

public class DefaultMapCreator implements MapCreator {

	@Override
	public Map create(Size size) {
		Map map = new Map(size);
		Polygon shape = (Polygon) map.getActor().getBody();
		
		double h = size.getHeight(), w = size.getWidth();
		double m = 0.01;
		double[] points = {
			m, 	 m,
			w-m, m,
			w-m, h-m,
			m,   h-m,
		};
		
		shape.addPath(points, true);
		
		return map;
	}

}
