package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import net.stickboyproductions.tetrisattack.ui.Screen;

/**
 * User: Pete
 * Date: 26/11/12
 * Time: 22:26
 */
public class Score implements Drawable {

  private int score;

  public Score(DrawableRegister drawableRegister) {
    drawableRegister.register(this);
  }

  public void addToScore(int i) {
    score += i;
  }

  public int getScore() {
    return score;
  }

  @Override
  public void draw(Screen screen) {
    screen.drawText("Score: " + score);
  }
}
