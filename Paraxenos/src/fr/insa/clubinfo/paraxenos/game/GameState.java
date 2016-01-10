package fr.insa.clubinfo.paraxenos.game;


import android.graphics.Canvas;

abstract public class GameState {
	protected Game game;
	
	public GameState(Game g) {
		game = g;
	}
	
	abstract public void enter();
	abstract public void update(Canvas canvas);
	abstract public void onActivityPaused();
	abstract public void onActivityResumed();
	abstract public void leave();
}
