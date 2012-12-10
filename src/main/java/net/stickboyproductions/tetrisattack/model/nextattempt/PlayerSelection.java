package net.stickboyproductions.tetrisattack.model.nextattempt;

import net.stickboyproductions.tetrisattack.Clock;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.interfaces.TimeDelayedAction;
import net.stickboyproductions.tetrisattack.io.InputNotifier;
import net.stickboyproductions.tetrisattack.model.AbstractControllable;
import net.stickboyproductions.tetrisattack.model.nextattempt.enums.Direction;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import net.stickboyproductions.tetrisattack.ui.Screen;

/**
 * The selection block the user moves around
 * Covers 2 Cells on the x plane
 * <p/>
 * User: Pete
 * Date: 28/10/12
 * Time: 15:17
 */
public class PlayerSelection extends AbstractControllable implements TimeDelayedAction, Drawable {

  Clock clock;

  private NewGrid grid;
  private NewBlock leftBlock;
  private NewBlock rightBlock;
  private DrawableRegister drawableRegister;

  double frame = 0.0;

  public PlayerSelection(Clock clock, NewGrid grid,
                         NewBlock leftBlock,
                         DrawableRegister drawableRegister, InputNotifier inputNotifier) {
    this.clock = clock;
    this.grid = grid;
    this.leftBlock = leftBlock;
    this.rightBlock = grid.getBlockToTheDirection(leftBlock, Direction.RIGHT);

    this.drawableRegister = drawableRegister;

    clock.schedule(this, 500);
    drawableRegister.register(this);
    inputNotifier.register(this);
  }

  @Override
  public void moveRightPressed() {
    NewBlock newRightBlock = grid.getBlockToTheDirection(rightBlock, Direction.RIGHT);
    if (newRightBlock != null) {
      leftBlock = rightBlock;
      rightBlock = newRightBlock;
    }
  }

  @Override
  public void moveLeftPressed() {
    NewBlock newLeftBlock = grid.getBlockToTheDirection(leftBlock, Direction.LEFT);
    if (newLeftBlock != null) {
      rightBlock = leftBlock;
      leftBlock = newLeftBlock;
    }
  }

  @Override
  public void moveUpPressed() {
    NewBlock newLeftBlock = grid.getBlockToTheDirection(leftBlock, Direction.UP);
    NewBlock newRightBlock = grid.getBlockToTheDirection(rightBlock, Direction.UP);
    if (newRightBlock != null && newLeftBlock != null) {
      leftBlock = newLeftBlock;
      rightBlock = newRightBlock;
    }
  }

  @Override
  public void moveDownPressed() {
    NewBlock newLeftBlock = grid.getBlockToTheDirection(leftBlock, Direction.DOWN);
    NewBlock newRightBlock = grid.getBlockToTheDirection(rightBlock, Direction.DOWN);
    if (newRightBlock != null && newLeftBlock != null && newRightBlock.getY() > 0) {
      leftBlock = newLeftBlock;
      rightBlock = newRightBlock;
    }
  }

  public int getLeftX() {
    return leftBlock.getX();
  }

  public int getRightX() {
    return rightBlock.getX();
  }

  public int getY() {
    return leftBlock.getY();
  }

  @Override
  public void timedExecution() {
    if (frame == 0.0) {
      frame = 0.25;
    } else {
      frame = 0.0;
    }
    clock.schedule(this, 500);
  }

  @Override
  public void draw(Screen screen) {
    screen.drawSprite(leftBlock.getX(), leftBlock.getY(), "selected", frame);
    screen.drawSprite(rightBlock.getX(), rightBlock.getY(), "selected", frame);
  }
}
