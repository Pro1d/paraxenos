package fr.insa.clubinfo.paraxelib.game.state;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import fr.insa.clubinfo.paraxelib.graphics.ColorScheme;
import fr.insa.clubinfo.paraxelib.graphics.ColorSchemeProvider;
import fr.insa.clubinfo.paraxelib.graphics.Drawable;
import fr.insa.clubinfo.paraxelib.utils.Size;

public class Map implements Drawable {
	private Paint backgroundPaint;
	private Paint borderPaint;
	private Size size;

	Map(Size size) {
		this.size = size;
		backgroundPaint = new Paint();
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		ColorScheme colors = ColorSchemeProvider.getColorScheme();

		backgroundPaint.setColor(colors.get("game_bg"));
		borderPaint.setColor(colors.get("game_border"));
		borderPaint.setStrokeWidth(0.1f);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(0, 0, size.getWidth(), size.getHeight(), backgroundPaint);
		canvas.drawRect(0, 0, size.getWidth(), size.getHeight(), borderPaint);
	}

}
