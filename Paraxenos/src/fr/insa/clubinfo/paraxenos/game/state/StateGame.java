package fr.insa.clubinfo.paraxenos.game.state;

import fr.insa.clubinfo.paraxenos.game.Game;
import fr.insa.clubinfo.paraxenos.game.GameState;
import fr.insa.clubinfo.paraxenos.graphics.Renderer;
import fr.insa.clubinfo.paraxenos.physics.Engine;

public class StateGame extends GameState {

	private static final String MAP_LAYOUT = "MAP_LAYOUT";
	private Map map;
	private Engine physicEngine;
	
	public StateGame(Game g) {
		super(g);

	}

	@Override
	public void enter() {
		map = new Map(game.getSize());
		Renderer renderer = game.getRenderer();
		renderer.addLayoutTop(MAP_LAYOUT);
		renderer.addDrawable(map, MAP_LAYOUT);
		physicEngine = new Engine();
	}

	@Override
	public void update(int frameDelay) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityPaused() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResumed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void leave() {
		// TODO Auto-generated method stub

	}

}
