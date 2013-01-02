package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.constants.ScreenConfig;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import net.stickboyproductions.tetrisattack.ui.Screen;

/**
 * User: Pete
 * Date: 26/11/12
 * Time: 22:26
 */
public class Score implements Drawable {

  private static final int SCORE_X = 400;
  private static final int SCORE_Y = 300;

  private static final int SCORE_LENGTH = 6;

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
    screen.drawText(SCORE_X, SCORE_Y, "Score");
    String s = "" + score;
    StringBuffer buffer = new StringBuffer();
    int scoreToFill = SCORE_LENGTH - s.length();
    for(int i = 0; i < scoreToFill; i++) {
      buffer.append("  ");
    }
    screen.drawText(SCORE_X, SCORE_Y + ScreenConfig.SPACER, buffer.toString() + score);
  }
}
