package fr.insa.clubinfo.paraxenos.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.fbessou.sofa.GameIOHelper;
import com.fbessou.sofa.GameInformation;

import fr.insa.clubinfo.paraxelib.game.Game;
import fr.insa.clubinfo.paraxelib.game.state.StateMenu;
import fr.insa.clubinfo.paraxenos.ContextSetup;
import fr.insa.clubinfo.paraxenos.R;

public class GameActivity extends Activity {
	public static final int FINISH = 0;

	Game game;
	GameIOHelper easyIO;
	GameInformation info;
	Handler handler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case FINISH:
					finish();
					return true;
				default:
					return false;
			}
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		SurfaceView surface = (SurfaceView) findViewById(R.id.surfaceView);
		// surface.getHolder().addCallback(this);

		info = new GameInformation(this);
		info.setName("Bouncy Clash - YoLo SwaG");
		easyIO = new GameIOHelper(this, info);
		easyIO.start(null);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		game = new Game(surface.getHolder(), easyIO, handler, metrics);
		surface.setOnTouchListener(game);
		new ContextSetup().setup();
		game.start(new StateMenu(game));

	}

	@Override
	protected void onPause() {
		super.onPause();
		game.onActivityPaused();
	}

	@Override
	protected void onResume() {
		super.onResume();
		game.onActivityResumed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		game.stop();
	}

}
