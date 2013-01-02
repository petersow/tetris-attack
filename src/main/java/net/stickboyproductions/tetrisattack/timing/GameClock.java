package net.stickboyproductions.tetrisattack.timing;

import javax.inject.Singleton;

/**
 * A clock to record the time of an individual game
 *
 * Will be setup on the start of a game and can be paused.
 *
 * User: Pete
 * Date: 02/01/13
 * Time: 20:59
 */
@Singleton
public class GameClock extends SystemClock {

  private int gameTimeInMs;
  private long lastFrame;
  private boolean paused = true;

  @Override
  public void start() {
    paused = false;
    lastFrame = super.getTime();
    gameTimeInMs = 0;
  }

  @Override
  protected long getTime() {
    int delta = getDelta();
    if(!paused) {
      gameTimeInMs += delta;
    }
    return gameTimeInMs;
  }

  private int getDelta() {
    long time = super.getTime();
    int delta = (int) (time - lastFrame);
    lastFrame = time;

    return delta;
  }

  public int getGameTimeInMs() {
    return gameTimeInMs;
  }

  public void pause() {
    this.paused = true;
  }

  public void resume() {
    this.paused = false;
  }
}
