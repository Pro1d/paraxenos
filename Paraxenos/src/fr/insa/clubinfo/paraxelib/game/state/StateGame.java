package fr.insa.clubinfo.paraxelib.game.state;

import fr.insa.clubinfo.paraxelib.game.Game;
import fr.insa.clubinfo.paraxelib.game.State;
import fr.insa.clubinfo.paraxelib.graphics.Renderer;
import fr.insa.clubinfo.paraxelib.physics.Engine;

public class StateGame extends State {

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