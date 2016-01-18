package fr.insa.clubinfo.paraxenos.navigation;

import java.util.ArrayList;
import java.util.UUID;

public class Menu {
	private static final String ITEMS_LAYOUT = "MENU_ITEMS" + UUID.randomUUID();
	private static final String BACKGROUND_LAYOUT = "MENU_BACKGROUND" + UUID.randomUUID();

	private final ArrayList<MenuItem> items = new ArrayList<>();
	private int currentItemIndex = -1;
	private boolean loop;

	public Menu(boolean loop) {
	}

	public void nextItem() {
		if (currentItemIndex != items.size() - 1 || !loop) {
			items.get(currentItemIndex).onLeft();
			currentItemIndex = (currentItemIndex + 1) % items.size();
			items.get(currentItemIndex).onEntered();
		}

	}

	public void previousItem() {
		if (currentItemIndex != 0 || !loop) {
			items.get(currentItemIndex).onLeft();
			currentItemIndex = (currentItemIndex == 0 ? items.size() - 1 : currentItemIndex - 1);
			items.get(currentItemIndex).onEntered();
		}

	}

	public void selectItem() {
		items.get(currentItemIndex).onSelected();
	}

	public void switchTo(MenuItem item) {
		if (currentItemIndex != -1)
			items.get(currentItemIndex).onLeft();
		currentItemIndex = items.indexOf(item);
		items.get(currentItemIndex).onEntered();
	}

	public void addItem(MenuItem item) {
		this.items.add(item);
	}
}
