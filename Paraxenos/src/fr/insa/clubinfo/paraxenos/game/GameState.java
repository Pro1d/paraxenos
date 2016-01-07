package fr.insa.clubinfo.paraxenos.game;


import android.graphics.Canvas;

abstract public class GameState {
	protected Game game;
	
	GameState(Game g) {
		game = g;
	}
	
	abstract void enter();
	abstract void update(Canvas canvas);
	abstract void onActivityPaused();
	abstract void onActivityResumed();
	abstract void leave();
}
