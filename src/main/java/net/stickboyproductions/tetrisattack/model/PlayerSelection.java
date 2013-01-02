package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.constants.Directions;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.interfaces.TimeDelayedAction;
import net.stickboyproductions.tetrisattack.io.InputNotifier;
import net.stickboyproductions.tetrisattack.timing.GameClock;
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

  GameClock clock;

  private Grid grid;
  private Block leftBlock;
  private Block rightBlock;
  private DrawableRegister drawableRegister;

  private boolean enabled = false;

  private int offsetY = 0;

  double frame = 0.0;

  public PlayerSelection(GameClock clock, Grid grid,
                         Block leftBlock,
                         DrawableRegister drawableRegister, InputNotifier inputNotifier) {
    this.clock = clock;
    this.grid = grid;
    this.leftBlock = leftBlock;
    this.rightBlock = grid.getBlockToTheDirection(leftBlock, Directions.RIGHT);

    this.drawableRegister = drawableRegister;

    clock.schedule(this, 500);
    drawableRegister.register(this);
    inputNotifier.register(this);
  }

  @Override
  public void moveRightPressed() {
    if (enabled) {
      Block newRightBlock = grid.getBlockToTheDirection(rightBlock, Directions.RIGHT);
      if (newRightBlock != null) {
        leftBlock = rightBlock;
        rightBlock = newRightBlock;
      }
    }
  }

  @Override
  public void moveLeftPressed() {
    if (enabled) {
      Block newLeftBlock = grid.getBlockToTheDirection(leftBlock, Directions.LEFT);
      if (newLeftBlock != null) {
        rightBlock = leftBlock;
        leftBlock = newLeftBlock;
      }
    }
  }

  @Override
  public void moveUpPressed() {
    if (enabled) {
      Block newLeftBlock = grid.getBlockToTheDirection(leftBlock, Directions.UP);
      Block newRightBlock = grid.getBlockToTheDirection(rightBlock, Directions.UP);
      if (newRightBlock != null && newLeftBlock != null) {
        leftBlock = newLeftBlock;
        rightBlock = newRightBlock;
      }
    }
  }

  @Override
  public void moveDownPressed() {
    if (enabled) {
      Block newLeftBlock = grid.getBlockToTheDirection(leftBlock, Directions.DOWN);
      Block newRightBlock = grid.getBlockToTheDirection(rightBlock, Directions.DOWN);
      if (newRightBlock != null && newLeftBlock != null && newRightBlock.getY() > 0) {
        leftBlock = newLeftBlock;
        rightBlock = newRightBlock;
      }
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

  public void incrementOffsetY(int incr) {
    offsetY += incr;
  }

  public void resetOffsetY() {
    offsetY = 0;
  }

  @Override
  public void draw(Screen screen) {
    screen.drawSprite(leftBlock.getX(), leftBlock.getY(), "selected", 0, offsetY, frame);
    screen.drawSprite(rightBlock.getX(), rightBlock.getY(), "selected", 0, offsetY, frame);
  }

  public void enable() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
  }
}
