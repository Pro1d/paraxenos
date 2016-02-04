package fr.insa.clubinfo.paraxenos.entities.maps;

import fr.insa.clubinfo.paraxelib.physics.body.Polygon;
import fr.insa.clubinfo.paraxelib.utils.Size;
import fr.insa.clubinfo.paraxenos.entities.Map;
import fr.insa.clubinfo.paraxenos.entities.MapCreator;

public class DebugMapCreator implements MapCreator {

	@Override
	public Map create(Size size) {
		Map map = new Map(size);
		Polygon shape = (Polygon) map.getActor().getBody();
		
		int n = 12;
		double h = size.getHeight(), w = size.getWidth();
		double r = 0.25;
		double[] points = new double[n*2*2];
		double a = -Math.PI/n;
		for(int i = 0; i < n*2; i++) {
			points[i*2+0] = h * ((i%2==0?r:r*0.75)*Math.cos(a*i) + 0.5);
			points[i*2+1] = h * ((i%2==0?r:r*0.75)*Math.sin(a*i) + 0.5);
		}
		
		shape.addPath(points, true);
		
		for(int i = 0; i < n*2; i++) {
			points[i*2+0] = h * ((i%2==0?r:r*0.75)*Math.cos(a*i) + 1.25);
			points[i*2+1] = h * ((i%2==0?r:r*0.75)*Math.sin(a*i) + 0.5);
		}
		
		shape.addPath(points, true);
		
		double m = 0.01;
		points = new double[]{
			m, 	 m,
			w-m, m,
			w-m, h-m,
			m,   h-m,
		};
		
		shape.addPath(points, true);
		
		m = 0.41;
		points = new double[]{
			m, 	 m,
			w-m, m,
			w-m, h-m,
			m,   h-m,
		};
		
		shape.addPath(points, true);
		
		/*points = new double[40];
		for(int i = 0; i < points.length; i++)
			points[i] = Math.random();
		shape.addPath(points, true);
		*/
		return map;
	}

}
