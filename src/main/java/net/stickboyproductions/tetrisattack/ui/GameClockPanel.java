package net.stickboyproductions.tetrisattack.ui;

import net.stickboyproductions.tetrisattack.constants.ScreenConfig;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.timing.GameClock;

/**
 * User: Pete
 * Date: 26/11/12
 * Time: 22:26
 */
public class GameClockPanel implements Drawable {

  private static final int CLOCK_X = 400;
  private static final int CLOCK_Y = 200;
  private GameClock gameClock;

  public GameClockPanel(DrawableRegister drawableRegister, GameClock gameClock) {
    this.gameClock = gameClock;
    drawableRegister.register(this);
  }

  @Override
  public void draw(Screen screen) {
    screen.drawText(CLOCK_X, CLOCK_Y, "Time");

    screen.drawText(CLOCK_X, CLOCK_Y + ScreenConfig.SPACER, "     " + gameTimeToScreenString());
  }

  private String gameTimeToScreenString() {
    int gameTimeInSeconds = gameClock.getGameTimeInMs() / 1000;
    String result = "";
    int minutes = gameTimeInSeconds / 60;
    result += minutes + ":";
    int seconds = gameTimeInSeconds % 60;
    if (seconds < 10) {
      result += "0";
    }
    result += seconds;
    return result;
  }
}
