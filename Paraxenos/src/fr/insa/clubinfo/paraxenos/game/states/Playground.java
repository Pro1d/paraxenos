package fr.insa.clubinfo.paraxenos.game.states;

import java.util.ArrayList;

import android.graphics.Color;

import fr.insa.clubinfo.paraxelib.game.Game;
import fr.insa.clubinfo.paraxelib.game.State;
import fr.insa.clubinfo.paraxelib.graphics.Renderer;
import fr.insa.clubinfo.paraxelib.physics.Engine;
import fr.insa.clubinfo.paraxelib.physics.actor.MovingActor;
import fr.insa.clubinfo.paraxenos.entities.Map;
import fr.insa.clubinfo.paraxenos.entities.MapFactory;
import fr.insa.clubinfo.paraxenos.entities.Puck;

public class Playground extends State {

	private static final String MAP_LAYOUT = "MAP_LAYOUT";
	private static final String PUCKS_LAYOUT = "PUCKS_LAYOUT";
	private Map map;
	private Engine physicEngine;
	private ArrayList<Puck> pucks = new ArrayList<>();
	
	public Playground(Game g) {
		super(g);

	}

	@Override
	public void enter() {
		map = MapFactory.createMap("Default", game.getSize());
		Renderer renderer = game.getRenderer();
		renderer.addLayoutTop(MAP_LAYOUT);
		renderer.addLayoutTop(PUCKS_LAYOUT);
		renderer.addDrawable(map, MAP_LAYOUT);
		
		physicEngine = new Engine();
		for(int i = 0; i < 100; i++) {
			Puck p = new Puck(0.01*Math.random()+0.01, Color.BLACK, 1.0);
			MovingActor actor = p.getActor();
			actor.setPosition(Math.random()*0.8+0.1, (Math.random()*0.8+0.1)*game.getSize().getHeight());
			actor.setVelocity(game.getSize().getWidth()/5000, game.getSize().getHeight()/5000);
			pucks.add(p);
			physicEngine.addActor(actor);
			renderer.addDrawable(p, PUCKS_LAYOUT);
		}
		physicEngine.addActor(map.getActor());
	}

	@Override
	public void update(int frameDelay) {
		physicEngine.step(frameDelay);
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
