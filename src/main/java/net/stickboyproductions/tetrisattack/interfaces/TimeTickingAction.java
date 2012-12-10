package net.stickboyproductions.tetrisattack.interfaces;

/**
 * User: Pete
 * Date: 29/10/12
 * Time: 21:13
 */
public interface TimeTickingAction {

  public void tick(long timeElapsed);

  public boolean isFinished();

  public void start();

  public void end();
}
