package fr.insa.clubinfo.paraxenos.game.state;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import android.view.MotionEvent;

import com.fbessou.sofa.GameIOHelper;
import com.fbessou.sofa.GameIOHelper.GamePadInputEvent;
import com.fbessou.sofa.sensor.Sensor;

import fr.insa.clubinfo.paraxenos.game.Game;
import fr.insa.clubinfo.paraxenos.game.GameState;
import fr.insa.clubinfo.paraxenos.graphics.BasicMenu;
import fr.insa.clubinfo.paraxenos.graphics.Renderer;
import fr.insa.clubinfo.paraxenos.navigation.BasicMenuItem.Callback;

public class StateMenu extends GameState {

	private static final String MENU_LAYOUT = "MENU_LAYOUT";
	private BasicMenu menu = null;
	private float direction = 0;
	int delayUntilNextMove = 0;

	public StateMenu(Game g) {
		super(g);
	}

	@Override
	public void enter() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if (game.getIO().isGamePadConnected(1) == false)
					game.pushState(new StateGame(game));
			}
		}, 30000);
		createMenu();
	}

	private void createMenu() {
		Callback callback = new Callback() {

			@Override
			public void onSelected() {
				Log.i("MENU", "SELECTED");
			}
		};

		menu = new BasicMenu("Paraxenos", game.getSize(), true);
		for (int i = 0; i < 3; i++) {
			menu.addItem("Pouet" + i, callback);
		}
		Renderer renderer = game.getRenderer();
		renderer.addLayoutBottom(MENU_LAYOUT);
		renderer.addDrawable(menu, MENU_LAYOUT);
	}

	@Override
	public void update(int frameDelay) {
		GameIOHelper io = game.getIO();
		GamePadInputEvent gpEvent;
		while ((gpEvent = io.pollInputEvent()) != null) {
			switch (gpEvent.event.eventType) {
				case KEYDOWN:
					if (gpEvent.event.padId == Sensor.KEY_CATEGORY_VALUE + 1) {
						game.pushState(new StateGame(game));
					}
					break;
				case FLOATMOVE:
					if (gpEvent.event.padId == Sensor.ANALOG_CATEGORY_VALUE + 1) {
						direction = gpEvent.event.getY();

					}
				default:
					break;
			}
		}

		if (delayUntilNextMove <= 0) {
			if (direction > 0.2) {
				menu.previousItem();
				delayUntilNextMove = (int) (((1 - direction) * 2 + 0.1) * 500);
			} else if (direction < -0.2) {
				menu.nextItem();
				delayUntilNextMove = (int) (((1 + direction) * 2 + 0.1) * 500);
			}
		}
		if (Math.abs(direction) <= 0.1f) {
			delayUntilNextMove = 0;
		}

		if (delayUntilNextMove > 0)
			delayUntilNextMove -= frameDelay;
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
		game.getRenderer().removeRenderLayout(MENU_LAYOUT);
	}

	@Override
	public void onTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		}
	}
}
