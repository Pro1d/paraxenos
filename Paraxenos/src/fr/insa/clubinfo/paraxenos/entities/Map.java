package fr.insa.clubinfo.paraxenos.entities;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import fr.insa.clubinfo.paraxelib.graphics.ColorScheme;
import fr.insa.clubinfo.paraxelib.graphics.ColorSchemeProvider;
import fr.insa.clubinfo.paraxelib.graphics.Drawable;
import fr.insa.clubinfo.paraxelib.physics.actor.StaticActor;
import fr.insa.clubinfo.paraxelib.physics.body.Polygon;
import fr.insa.clubinfo.paraxelib.utils.Edge;
import fr.insa.clubinfo.paraxelib.utils.Size;

public class Map implements Drawable {
	private Paint backgroundPaint;
	private Paint borderPaint;
	private Size size;

	private StaticActor actor;
	
	public Map(Size size) {
		this.size = size;
		backgroundPaint = new Paint();
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		ColorScheme colors = ColorSchemeProvider.getColorScheme();

		backgroundPaint.setColor(colors.get("game_bg"));
		borderPaint.setColor(colors.get("game_border"));
		borderPaint.setStrokeWidth(0.01f);
		
		actor = new StaticActor();
		getActor().setBody(new Polygon());
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(0, 0, size.getWidth(), size.getHeight(), backgroundPaint);
		Polygon shape = (Polygon) getActor().getBody();
		for(int i = shape.getEdgeCount(); --i >= 0; ) {
			Edge e = shape.getEdge(i);
			canvas.drawLine((float) e.vertexStart.x, (float) e.vertexStart.y,
					(float) e.vertexEnd.x, (float) e.vertexEnd.y, borderPaint);
		}
	}

	public StaticActor getActor() {
		return actor;
	}
	
}
