package fr.insa.clubinfo.paraxelib.graphics;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.text.TextPaint;
import fr.insa.clubinfo.paraxelib.navigation.BasicMenuItem;
import fr.insa.clubinfo.paraxelib.navigation.BasicMenuItem.Callback;
import fr.insa.clubinfo.paraxelib.navigation.Menu;
import fr.insa.clubinfo.paraxelib.utils.Size;

public class BasicMenu extends Menu implements Drawable {
	private String title;
	private TextPaint titlePaint;
	private Size size;
	private float textSize;
	private float titleRatio = 1.5f;

	private float marginY;
	private float heightTitle;
	private float heightItems;
	private List<BasicMenuItem> basicItems;

	private int colorBase;
	private int colorHighlight;
	private Paint bgPaint;
	private float textX;

	public BasicMenu(String name, Size size, boolean loop) {
		super(loop);
		basicItems = new ArrayList<BasicMenuItem>();
		this.size = size;
		this.title = name;
		this.colorBase = Color.RED;
		this.colorHighlight = Color.CYAN;

		titlePaint = TextPaintFactory.createTextPaint(1);
		titlePaint.setColor(Color.BLUE);
		titlePaint.setTextAlign(Align.CENTER);

		bgPaint = new Paint();
		bgPaint.setColor(Color.BLACK);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(0, 0, 1, 1, bgPaint);
		int count = canvas.save();
		canvas.translate(textX, marginY);
		canvas.drawText(title, 0, textSize * titleRatio, titlePaint);
		canvas.translate(0, heightTitle - textSize / 2);
		for (BasicMenuItem item : basicItems) {
			canvas.translate(0, textSize);
			item.draw(canvas);
		}
		canvas.restoreToCount(count);
	}

	public void addItem(String name, Callback callback) {
		BasicMenuItem item = new BasicMenuItem(name, colorBase, colorHighlight);
		item.setCallback(callback);
		basicItems.add(item);

		super.addItem(item);
		if (basicItems.size() == 1) {
			item.onEntered();
			switchTo(item);
		}

		recalculateMetrics();
	}

	private void recalculateMetrics() {
		textSize = Math.min(size.getHeight() / 10, size.getHeight() / (basicItems.size() + 2));
		for (BasicMenuItem it : basicItems) {
			it.setTextSize(textSize);
		}

		heightTitle = (titleRatio + 0.2f) * textSize;
		heightItems = textSize * basicItems.size();
		marginY = (size.getHeight() - (heightItems + heightTitle)) / 2;
		textX = size.getWidth() / 2;
		titlePaint.setTextSize(textSize * titleRatio);
	}

	public void setTitleRatio(float ratio) {
		titleRatio = ratio;

		recalculateMetrics();
	}

	public void setTitleFont(Typeface typeface) {
		titlePaint.setTypeface(typeface);
	}

	public void setItemsFont(Typeface typeface) {
		for (BasicMenuItem item : basicItems) {
			item.setFont(typeface);
		}
	}
}
