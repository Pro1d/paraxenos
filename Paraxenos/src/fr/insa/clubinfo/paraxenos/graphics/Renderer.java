package fr.insa.clubinfo.paraxenos.graphics;

import java.util.HashMap;
import java.util.LinkedList;

import android.graphics.Canvas;

public class Renderer {

  private Canvas canvas;
  private final LinkedList<RenderLayout> layouts = new LinkedList<>();
  private final HashMap<String, RenderLayout> layoutNames = new HashMap<>();

  public void render() {
    for (RenderLayout layout : layouts) {
      layout.render(canvas);
    }
  }

  public void addLayoutTop(String name) {
    RenderLayout layout = new RenderLayout();
    layouts.addLast(layout);
    layoutNames.put(name, layout);
  }

  public void addLayoutBottom(String name) {
    RenderLayout layout = new RenderLayout();
    layouts.addFirst(layout);
    layoutNames.put(name, layout);
  }

  public void renderLayout(String name) {
    layoutNames.get(name).render(canvas);
  }

  public boolean layoutExists(String name) {
    return layoutNames.get(name) != null;
  }

  public void removeRenderLayout(String name) {
    RenderLayout removedLayout = layoutNames.remove(name);
    layouts.remove(removedLayout);
  }

  public void removeAllRenderLayouts() {
    layouts.clear();
    layoutNames.clear();
  }

  public void clearRenderLayout(String name) {
    RenderLayout renderLayout = layoutNames.get(name);
    renderLayout.removeAll();
  }

}
