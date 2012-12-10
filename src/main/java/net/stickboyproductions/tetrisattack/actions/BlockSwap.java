package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Cell;
import net.stickboyproductions.tetrisattack.model.Grid;

import static net.stickboyproductions.tetrisattack.config.ScreenConfig.CELL_WIDTH;

/**
 * User: Pete
 * Date: 30/10/12
 * Time: 21:14
 */
public class BlockSwap implements TimeTickingAction {

  private final static int OFFSET_STEP = CELL_WIDTH / 5;

  private final Block leftBlock;
  private final Block rightBlock;
  private Grid grid;
  private int offset = CELL_WIDTH;

  public BlockSwap(Block leftBlock, Block rightBlock, Grid grid) {
    this.leftBlock = leftBlock;
    this.rightBlock = rightBlock;
    this.grid = grid;
  }

  @Override
  public void tick(long timeElapsed) {
    if (offset > OFFSET_STEP) {
      if (leftBlock != null) {
        leftBlock.offset(OFFSET_STEP, 0);
      }
      if (rightBlock != null) {
        rightBlock.offset(-OFFSET_STEP, 0);
      }
    } else {
      if (offset > 0) {
        if (leftBlock != null) {
          leftBlock.offset(offset, 0);
        }
        if (rightBlock != null) {
          rightBlock.offset(-offset, 0);
        }
      }
    }
    offset -= OFFSET_STEP;
  }

  @Override
  public boolean isFinished() {
    return offset <= 0;
  }

  @Override
  public void start() {
    if (leftBlock != null) {
      leftBlock.setBusy(true);
    }

    if (rightBlock != null) {
      rightBlock.setBusy(true);
    }
  }

  @Override
  public void end() {
    if (leftBlock != null && rightBlock != null) {
      Cell leftCell = leftBlock.getCurrentCell();
      finaliseCell(leftBlock, rightBlock.getCurrentCell());
      finaliseCell(rightBlock, leftCell);
    } else if (leftBlock != null) {
      grid.getColumns().get(leftBlock.getX()).fallCellsAboveIntoHole(leftBlock.getY());
      Cell cellToTheRight = grid.getCellToTheRight(leftBlock.getCurrentCell());
      finaliseCell(leftBlock, cellToTheRight);
    } else if (rightBlock != null) {
      grid.getColumns().get(rightBlock.getX()).fallCellsAboveIntoHole(rightBlock.getY());
      Cell cellToTheLeft = grid.getCellToTheLeft(rightBlock.getCurrentCell());
      finaliseCell(rightBlock, cellToTheLeft);
    }
  }

  private void finaliseCell(Block block, Cell cell) {
    block.setCurrentCell(cell);
    block.setBusy(false);
    block.removeOffset();
    grid.getColumns().get(cell.getX()).fallIfCellBelowIsEmpty(cell.getY());
    grid.checkAndPerformChainClear(cell.getX(), cell.getY());
  }
}
