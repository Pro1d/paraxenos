package fr.insa.clubinfo.paraxenos.game;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;

import com.fbessou.sofa.GameIOHelper;

import fr.insa.clubinfo.paraxenos.game.state.StateMenu;
import fr.insa.clubinfo.paraxenos.ui.GameActivity;

public class Game implements Runnable, OnTouchListener {
	
	private static final int FRAME_DELAY = 30; 
	
	private SurfaceHolder surfaceHolder;
	private GameIOHelper easyIO;
	
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
	
	public Game(SurfaceHolder holder, GameIOHelper easyIO, Handler activityHandler) {
		surfaceHolder = holder;
		this.easyIO = easyIO;
		this.activityHandler = activityHandler;
		// load resource
		// texture, sound, ...
	}
	
	@Override
	public void run() {
		pushState(new StateMenu(this));
		
		long t = SystemClock.elapsedRealtime();
		while(isRunning) {
			if(onPause) {
				currentState.onActivityPaused();
				isPaused = true;
				onPause = false;
			}
			if(onResume) {
				if(isPaused) {
					currentState.onActivityResumed();
					isPaused = false;
				}
				onResume = false;
			}
			
			// Activity paused
			if(isPaused) {
				// TODO flush
				easyIO.flushAll();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				continue;
			}

			update();
			t = SystemClock.elapsedRealtime() - t;
			if(t < FRAME_DELAY)
				try {
					Thread.sleep(FRAME_DELAY-t);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			t = SystemClock.elapsedRealtime();

		}
		Log.i("###", "End thread --------------");
	}
	
	private void update() {
		// sound.update();
		Canvas canvas = surfaceHolder.lockCanvas();
		
		currentState.update(canvas, FRAME_DELAY);
		
		if(canvas != null)
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
	public void start() {
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void pushState(GameState newState) {
		if(currentState != null)
			currentState.leave();
		states.push(newState);
		currentState = newState;
		currentState.enter();
	}
	public void popState(int count) {
		currentState.leave();
		while(/*states.size() > 1 && */--count >= 0)
			states.pop();
		currentState = states.peek();
		currentState.enter();
	}

	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
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
		//int[] l = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		ArrayList<Integer> l = easyIO.getGamePadIds();
		lockedGamePadId.clear();
		for(int i : l)
			lockedGamePadId.add(i);
		return lockedGamePadId;
	}
}
