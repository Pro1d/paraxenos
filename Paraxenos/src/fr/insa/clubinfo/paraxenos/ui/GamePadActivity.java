package fr.insa.clubinfo.paraxenos.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.fbessou.sofa.GamePadIOClient.ConnectionStateChangedListener;
import com.fbessou.sofa.GamePadIOHelper;
import com.fbessou.sofa.GamePadInformation;
import com.fbessou.sofa.indicator.FeedbackIndicator;
import com.fbessou.sofa.sensor.Analog2DSensor;
import com.fbessou.sofa.sensor.KeySensor;
import com.fbessou.sofa.sensor.Sensor;
import com.fbessou.sofa.view.JoystickView;

import fr.insa.clubinfo.paraxenos.R;

public class GamePadActivity extends Activity implements ConnectionStateChangedListener {
	GamePadInformation infos;
	GamePadIOHelper easyIO;
	
	ToggleButton connectionStateView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamepad_layout);
		
		infos = new GamePadInformation(this);
		easyIO = new GamePadIOHelper(this, infos);
		easyIO.start(this);

		easyIO.attachSensor(new Analog2DSensor(Sensor.ANALOG_CATEGORY_VALUE+1, (JoystickView)findViewById(R.id.joystick)));
		easyIO.attachSensor(new KeySensor(Sensor.KEY_CATEGORY_VALUE+1, findViewById(R.id.action_button)));
		easyIO.attachSensor(new KeySensor(Sensor.KEY_CATEGORY_VALUE+2, findViewById(R.id.pause_button)));
		easyIO.attachIndicator(new FeedbackIndicator(this));
		
		connectionStateView = (ToggleButton) findViewById(R.id.connection_state);
		onDisconnectedFromProxy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Add an option to change the user's nickname in the menu
		menu.add(42, 42, 0, "Change nickname");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if(item.getItemId() == 42)
			changeNamePrompt();
		return super.onMenuItemSelected(featureId, item);
	}
	private void changeNamePrompt() {
		// This edit text is the content of the dialog
		final EditText edit = new EditText(this);
		edit.setText(infos.getNickname());
		
		// Build a dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Change your nickname");
		builder.setView(edit);
		builder.setPositiveButton("Change", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				infos.setNickname(edit.getText().toString());
				easyIO.updateInformation(infos);
			}
		});
		builder.setNegativeButton("Cancel", null);
		
		// Display the dialog and let the user enter his new nickname
		builder.show();
	}

	@Override
	public void onConnectedToProxy() {
		connectionStateView.setEnabled(true);
		connectionStateView.setChecked(false);
	}
	@Override
	public void onConnectedToGame() {
		connectionStateView.setEnabled(true);
		connectionStateView.setChecked(true);
	}
	@Override
	public void onDisconnectedFromGame() {
		connectionStateView.setEnabled(true);
		connectionStateView.setChecked(false);
	}
	@Override
	public void onDisconnectedFromProxy() {
		connectionStateView.setEnabled(false);
		connectionStateView.setChecked(false);
		
	}

}
