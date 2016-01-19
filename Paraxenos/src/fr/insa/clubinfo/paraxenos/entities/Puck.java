package fr.insa.clubinfo.paraxenos.entities;

import android.graphics.Canvas;
import android.graphics.Paint;
import fr.insa.clubinfo.paraxelib.graphics.Drawable;
import fr.insa.clubinfo.paraxelib.physics.actor.MovingActor;
import fr.insa.clubinfo.paraxelib.utils.Vector;

public class Puck implements Drawable {

	protected MovingActor actor;
	protected Paint paint;
	
	public Puck(double radius, int color, double density) {
		actor = new MovingActor(radius);
		actor.getBody().getMassPerAreaUnit(density);
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
	}
	
	@Override
	public void draw(Canvas canvas) {
		Vector pos = actor.getPosition();
		canvas.drawCircle((float) pos.x, (float) pos.y, (float)actor.getBody().getRadius(), paint);
	}

	public MovingActor getActor() {
		return actor;
	}

}
