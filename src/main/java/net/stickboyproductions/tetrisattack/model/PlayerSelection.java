package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.Clock;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.interfaces.TimeDelayedAction;
import net.stickboyproductions.tetrisattack.io.InputNotifier;
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

  private Grid grid;
  private Cell leftCell;
  private Cell rightCell;
  private DrawableRegister drawableRegister;

  double frame = 0.0;

  public PlayerSelection(Clock clock, Grid grid,
                         Cell leftCell, Cell rightCell,
                         DrawableRegister drawableRegister, InputNotifier inputNotifier) {
    this.clock = clock;
    this.grid = grid;
    this.leftCell = leftCell;
    this.rightCell = rightCell;

    this.drawableRegister = drawableRegister;

    clock.schedule(this, 500);
    drawableRegister.register(this);
    inputNotifier.register(this);
  }

  @Override
  public void moveRightPressed() {
    Cell newRightCell = grid.getCellToTheRight(rightCell);
    if (newRightCell != null) {
      leftCell = rightCell;
      rightCell = newRightCell;
    }
  }

  @Override
  public void moveLeftPressed() {
    Cell newLeftCell = grid.getCellToTheLeft(leftCell);
    if (newLeftCell != null) {
      rightCell = leftCell;
      leftCell = newLeftCell;
    }
  }

  @Override
  public void moveUpPressed() {
    Cell newLeftCell = grid.getCellAbove(leftCell);
    Cell newRightCell = grid.getCellAbove(rightCell);
    if (newRightCell != null && newLeftCell != null) {
      leftCell = newLeftCell;
      rightCell = newRightCell;
    }
  }

  @Override
  public void moveDownPressed() {
    Cell newLeftCell = grid.getCellBelow(leftCell);
    Cell newRightCell = grid.getCellBelow(rightCell);
    if (newRightCell != null && newLeftCell != null) {
      leftCell = newLeftCell;
      rightCell = newRightCell;
    }
  }

  public int getLeftX() {
    return leftCell.getX();
  }

  public int getY() {
    return leftCell.getY();
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
    screen.drawSprite(leftCell.getQuad(), "selected", frame);
    screen.drawSprite(rightCell.getQuad(), "selected", frame);
  }
}
