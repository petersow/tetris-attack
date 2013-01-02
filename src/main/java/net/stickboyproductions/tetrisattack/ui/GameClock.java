package net.stickboyproductions.tetrisattack.ui;

import net.stickboyproductions.tetrisattack.constants.ScreenConfig;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;

/**
 * User: Pete
 * Date: 26/11/12
 * Time: 22:26
 */
public class GameClock implements Drawable {

  private static final int CLOCK_X = 400;
  private static final int CLOCK_Y = 200;

  private int gameTimeInMs;

  public GameClock(DrawableRegister drawableRegister) {
    drawableRegister.register(this);
  }

  public int getGameTimeInMs() {
    return gameTimeInMs;
  }

  public void setGameTimeInMs(int gameTimeInMs) {
    this.gameTimeInMs = gameTimeInMs;
  }

  @Override
  public void draw(Screen screen) {
    screen.drawText(CLOCK_X, CLOCK_Y, "Time");
    System.out.println("Game time is " + gameTimeInMs / 1000 + "s");

    screen.drawText(CLOCK_X, CLOCK_Y + ScreenConfig.SPACER, "     " + gameTimeToScreenString());
  }

  private String gameTimeToScreenString() {
    int gameTimeInSeconds = gameTimeInMs / 1000;
    String result = "";
    int minutes = gameTimeInSeconds / 60;
    result += minutes + ":";
    int seconds = gameTimeInSeconds % 60;
    if(seconds < 10) {
      result += "0";
    }
    result += seconds;
    return result;
  }

  public void addToGameTime(int delta) {
    gameTimeInMs += delta;
  }
}
