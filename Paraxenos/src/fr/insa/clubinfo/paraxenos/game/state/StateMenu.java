package fr.insa.clubinfo.paraxenos.game.state;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Canvas;

import com.fbessou.sofa.GameIOHelper;
import com.fbessou.sofa.GameIOHelper.GamePadInputEvent;
import com.fbessou.sofa.sensor.Sensor;

import fr.insa.clubinfo.paraxenos.game.Game;
import fr.insa.clubinfo.paraxenos.game.GameState;

public class StateMenu extends GameState {
	
	public StateMenu(Game g) {
		super(g);
		
	}

	@Override
	public void enter() {
		Timer t= new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if(game.getIO().isConnected() == false)
					game.pushState(new StateGame(game));
			}
		}, 3000);
	}

	@Override
	public void update(Canvas canvas, int frameDelay) {
		GameIOHelper io = game.getIO();
		GamePadInputEvent gpEvent;
		while((gpEvent = io.pollInputEvent()) != null) {
			switch(gpEvent.event.eventType) {
			case KEYDOWN:
				if(gpEvent.event.inputId == Sensor.KEY_CATEGORY_VALUE+1) {
					game.pushState(new StateGame(game));
				}
				break;
			default:
				break;
			}
		}
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
