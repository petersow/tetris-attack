package net.stickboyproductions.tetrisattack.ui;

import net.stickboyproductions.tetrisattack.constants.ScreenConfig;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.model.Speed;

/**
 * User: Pete
 * Date: 26/11/12
 * Time: 22:26
 */
public class GameSpeedPanel implements Drawable {

  private static final int SPEED_X = 400;
  private static final int SPEED_Y = 400;
  private static final int SPEED_LENGTH = 6;
  private Speed speed;

  public GameSpeedPanel(DrawableRegister drawableRegister, Speed speed) {
    this.speed = speed;
    drawableRegister.register(this);
  }

  @Override
  public void draw(Screen screen) {
    screen.drawText(SPEED_X, SPEED_Y, "Speed");

    String s = "" + speed.getSpeed();
    StringBuffer buffer = new StringBuffer();
    int scoreToFill = SPEED_LENGTH - s.length();
    for (int i = 0; i < scoreToFill; i++) {
      buffer.append("  ");
    }
    screen.drawText(SPEED_X, SPEED_Y + ScreenConfig.SPACER, buffer.toString() + speed.getSpeed());
  }
}
