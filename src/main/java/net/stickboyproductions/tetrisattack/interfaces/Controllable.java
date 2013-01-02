package net.stickboyproductions.tetrisattack.interfaces;

/**
 * User: Pete
 * Date: 28/10/12
 * Time: 15:33
 */
public interface Controllable {
  void moveLeftPressed();

  void moveLeftReleased();

  void moveRightPressed();

  void moveRightReleased();

  void moveUpPressed();

  void moveUpReleased();

  void moveDownPressed();

  void moveDownReleased();

  void actionPressed();

  void actionReleased();

  void newLinePressed();

  void newLineReleased();

  void pausePressed();

  void pauseReleased();
}
