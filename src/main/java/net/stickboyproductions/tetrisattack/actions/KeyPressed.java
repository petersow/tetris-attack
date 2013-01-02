package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.constants.Controls;
import net.stickboyproductions.tetrisattack.constants.SpeedConstants;
import net.stickboyproductions.tetrisattack.interfaces.Controllable;
import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.io.InputController;

import java.util.List;

/**
 * User: Pete
 * Date: 30/10/12
 * Time: 23:19
 */
public class KeyPressed implements TimeTickingAction {

  private Integer keyCode;
  private InputController inputController;
  private boolean spammable;
  private boolean finished = false;

  public KeyPressed(Integer keyCode, InputController inputController, boolean spammable) {
    this.keyCode = keyCode;
    this.inputController = inputController;
    this.spammable = spammable;
  }

  @Override
  public void tick(long timeElapsed) {
    if (timeElapsed >= SpeedConstants.KEY_PRESS_PAUSE_MS && !finished && spammable) {
      fireAction();
    }
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  @Override
  public void start() {
    fireAction();
  }

  private void fireAction() {
    List<Controllable> registeredHandlers = inputController.getRegisteredHandlers();
    for (Controllable controllable : registeredHandlers) {
      if (keyCode.equals(Controls.LEFT)) {
        controllable.moveLeftPressed();
      } else if (keyCode.equals(Controls.RIGHT)) {
        controllable.moveRightPressed();
      } else if (keyCode.equals(Controls.UP)) {
        controllable.moveUpPressed();
      } else if (keyCode.equals(Controls.DOWN)) {
        controllable.moveDownPressed();
      } else if (keyCode.equals(Controls.NEW_LINE)) {
        controllable.newLinePressed();
      } else if (keyCode.equals(Controls.BLOCK_SWAP)) {
        controllable.actionPressed();
      } else if (keyCode.equals(Controls.PAUSE)) {
        controllable.pausePressed();
      }
    }
  }

  @Override
  public void end() {
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }
}
