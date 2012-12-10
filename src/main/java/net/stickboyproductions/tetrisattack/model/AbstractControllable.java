package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.interfaces.Controllable;

/**
 * An implementation of Controllable that does nothing
 * <p/>
 * Saves implementing classes having to implement the methods they don't care about
 * <p/>
 * User: Pete
 * Date: 28/10/12
 * Time: 15:36
 */
public abstract class AbstractControllable implements Controllable {
  @Override
  public void moveLeftPressed() {
    // Do Nothing
  }

  @Override
  public void moveLeftReleased() {
    // Do Nothing
  }

  @Override
  public void moveRightPressed() {
    // Do Nothing
  }

  @Override
  public void moveRightReleased() {
    // Do Nothing
  }

  @Override
  public void moveUpPressed() {
    // Do Nothing
  }

  @Override
  public void moveUpReleased() {
    // Do Nothing
  }

  @Override
  public void moveDownPressed() {
    // Do Nothing
  }

  @Override
  public void moveDownReleased() {
    // Do Nothing
  }

  @Override
  public void actionPressed() {
    // Do Nothing
  }

  @Override
  public void actionReleased() {
    // Do Nothing
  }

  @Override
  public void spacePressed() {
    // Do Nothing
  }

  @Override
  public void spaceReleased() {
    // Do Nothing
  }
}
