package net.stickboyproductions.tetrisattack.ui;

import com.google.common.collect.Lists;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * User: Pete
 * Date: 11/11/12
 * Time: 15:11
 */
@Singleton
public class DrawableRegister {

  private final List<Drawable> drawables = Lists.newArrayList();
  private Screen screen;

  @Inject
  public DrawableRegister(Screen screen) {
    this.screen = screen;
  }

  public void draw() {
    for(Drawable drawable : drawables) {
      drawable.draw(screen);
    }
  }

  public void register(Drawable drawable) {
    drawables.add(drawable);
  }

  public void unregister(Drawable drawable) {
    drawables.remove(drawable);
  }
}
