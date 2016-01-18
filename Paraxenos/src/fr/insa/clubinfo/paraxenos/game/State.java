package fr.insa.clubinfo.paraxenos.game;

import android.view.MotionEvent;
import fr.insa.clubinfo.paraxenos.graphics.Renderer;

abstract public class State {
	protected Game game;

	public State(Game g) {
		game = g;
	}

	abstract public void enter();

	abstract public void update(int frameDelay);

	public void render(Renderer renderer) {
		renderer.render();
	}

	abstract public void onActivityPaused();

	abstract public void onActivityResumed();

	abstract public void leave();

	public void onTouch(MotionEvent event) {

	}
}
