package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Cell;
import net.stickboyproductions.tetrisattack.model.Grid;

import static net.stickboyproductions.tetrisattack.config.ScreenConfig.CELL_HEIGHT;

/**
 * User: Pete
 * Date: 30/10/12
 * Time: 21:14
 */
public class GridMoveUp implements TimeTickingAction {

  private final static int OFFSET_STEP = CELL_HEIGHT / 15;
  private int totalOffset;
  private Grid grid;
  private long timeElapsed;

  private long nextFire = NEXT_FIRE_OFFSET;
  private static final long NEXT_FIRE_OFFSET = 1000;

  public GridMoveUp(Grid grid) {
    this.grid = grid;
  }

  @Override
  public void tick(long timeElapsed) {
    this.timeElapsed = timeElapsed;
    if (timeElapsed >= nextFire) {
      for (Cell cell : grid.getCells()) {
        cell.offset(0, OFFSET_STEP);
      }

      totalOffset += OFFSET_STEP;
      if (totalOffset >= CELL_HEIGHT) {
        System.out.println("FIRING!!!!!!!");
        grid.moveAllUp();
        grid.generateNewRow();
        for (Cell cell : grid.getCells()) {
          // Reset offset
          cell.offset(0, -CELL_HEIGHT);
        }
        totalOffset = 0;
      }
      nextFire += NEXT_FIRE_OFFSET;
    }
  }

  public void reset() {
    nextFire = timeElapsed + NEXT_FIRE_OFFSET;
    for (Cell cell : grid.getCells()) {
      // Reset offset
      cell.offset(0, -totalOffset);
    }
    totalOffset = 0;
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void start() {
  }

  @Override
  public void end() {
  }
}
