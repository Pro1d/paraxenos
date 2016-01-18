package fr.insa.clubinfo.paraxenos.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;

import com.fbessou.sofa.GameIOHelper;

import fr.insa.clubinfo.paraxenos.graphics.Renderer;
import fr.insa.clubinfo.paraxenos.ui.GameActivity;
import fr.insa.clubinfo.paraxenos.utils.Size;

public class Game implements Runnable, OnTouchListener {

	private static final int FRAME_DELAY = 30;

	private SurfaceHolder surfaceHolder;
	private final Renderer renderer = new Renderer();
	private GameIOHelper easyIO;

	private GameState firstState;
	private GameState currentState = null;
	private Stack<GameState> states = new Stack<>();
	private HashSet<Integer> lockedGamePadId = new HashSet<>();

	// related to activity
	private boolean isPaused = true;
	private boolean onPause = false;
	private boolean onResume = false;

	private Thread thread;
	private boolean isRunning = true;
	private Handler activityHandler;

	private final Size size;
	private final DisplayMetrics metrics;
	private final float scale;

	public Game(SurfaceHolder holder, GameIOHelper easyIO, Handler activityHandler, DisplayMetrics metrics) {
		surfaceHolder = holder;
		this.easyIO = easyIO;
		this.activityHandler = activityHandler;

		this.metrics = metrics;
		this.scale = (float) metrics.widthPixels;
		this.size = new Size(1f, (float) metrics.heightPixels / this.scale);
		// load resource
		// texture, sound, ...
	}

	@Override
	public void run() {
		pushState(firstState);

		long t = SystemClock.elapsedRealtime();
		while (isRunning) {
			if (onPause) {
				currentState.onActivityPaused();
				isPaused = true;
				onPause = false;
			}
			if (onResume) {
				if (isPaused) {
					currentState.onActivityResumed();
					isPaused = false;
				}
				onResume = false;
			}

			// Activity paused
			if (isPaused) {
				// TODO flush
				easyIO.flushAll();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				continue;
			}

			updateAndRender();
			t = SystemClock.elapsedRealtime() - t;
			if (t < FRAME_DELAY)
				try {
					Thread.sleep(FRAME_DELAY - t);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			t = SystemClock.elapsedRealtime();

		}
		Log.i("###", "End thread --------------");
	}

	private void updateAndRender() {
		// sound.update();
		Canvas canvas = surfaceHolder.lockCanvas();

		update(FRAME_DELAY);
		if (canvas != null) {
			renderOnCanvas(canvas);
		}
	}

	private void update(int frameDelay) {
		currentState.update(FRAME_DELAY);
	}

	private void renderOnCanvas(Canvas canvas) {
		renderer.setCanvas(canvas);
		int count = canvas.save();
		canvas.scale(scale, scale);
		currentState.render(renderer);
		canvas.restoreToCount(count);

		surfaceHolder.unlockCanvasAndPost(canvas);
	}

	public void onActivityPaused() {
		onPause = true;
	}

	public void onActivityResumed() {
		onResume = true;
	}

	public void stop() {
		isRunning = false;
		thread.interrupt();
	}

	public void start(GameState firstState) {
		if (thread == null) {
			this.firstState = firstState;
			thread = new Thread(this);
			thread.start();
		}
	}

	public void pushState(GameState newState) {
		if (currentState != null) {
			currentState.leave();
		}
		states.push(newState);
		currentState = newState;
		currentState.enter();
	}

	public void popState(int count) {
		currentState.leave();
		while (--count >= 0)
			states.pop();
		currentState = states.peek();
		currentState.enter();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		currentState.onTouch(event);
		return false;
	}

	public void finishActivity() {
		activityHandler.sendEmptyMessage(GameActivity.FINISH);
	}

	public GameIOHelper getIO() {
		return easyIO;
	}

	public HashSet<Integer> getActiveGamePadId() {
		return lockedGamePadId;
	}

	public HashSet<Integer> lockActiveGamePadId() {
		// int[] l = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		ArrayList<Integer> l = easyIO.getGamePadIds();
		lockedGamePadId.clear();
		for (int i : l)
			lockedGamePadId.add(i);
		return lockedGamePadId;
	}

	public boolean isStarted() {
		return thread != null;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Size getSize() {
		return size;
	}
}
