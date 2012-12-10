package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Cell;
import net.stickboyproductions.tetrisattack.model.Grid;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static net.stickboyproductions.tetrisattack.config.GameConfig.DEBUG;
import static net.stickboyproductions.tetrisattack.config.ScreenConfig.CELL_HEIGHT;

/**
 * User: Pete
 * Date: 30/10/12
 * Time: 23:19
 */
public class BlockFall implements TimeTickingAction {

  private static final int FALL_STEP = CELL_HEIGHT * 2;

  private static final int FALL_WAIT_TIME = 200;

  private List<Block> chain;
  private Grid grid;
  private int fallen;

  private boolean landed = false;

  public BlockFall(List<Block> chain, Grid grid) {
    if (DEBUG) {
      System.out.println("Making a Block Fall containing");
      for (Block block : chain) {
        System.out.println("x=" + block.getX() + ", y=" + block.getY());
      }
    }
    this.chain = chain;
    this.grid = grid;
  }

  @Override
  public void tick(long timeElapsed) {
    if (timeElapsed > FALL_WAIT_TIME) {
      for (Block block : chain) {
        block.offset(0, -FALL_STEP);
      }
      fallen += FALL_STEP;
    }
    if (fallen >= CELL_HEIGHT) {
      for (Block block : getChainInOrder()) {
        block.removeOffset();
        Cell cellBelow = grid.getCellBelow(block.getCurrentCell());
        if (cellBelow != null && cellBelow.getBlock() == null) {
          if (DEBUG) {
            System.out.println("Moving x=" + block.getX() + ", y=" + block.getY());
            System.out.println("to x=" + cellBelow.getX() + ", y=" + cellBelow.getY());
          }
          block.setCurrentCell(cellBelow);
        }

      }
      // check if we should carry on
      Block currentBlock = getChainInOrder().get(0);
      Cell cellBelow = grid.getCellBelow(currentBlock.getCurrentCell());
      if (DEBUG) {
        System.out.println("Checking  x=" + currentBlock.getX() + ", y=" +
          (currentBlock.getY() - 1) + " to see if we should carry on");
      }
      if (cellBelow == null || !(cellBelow.getBlock() == null)) {
        if (DEBUG) {
          System.out.println("Stopping");
        }
        landed = true;
      } else {
        if (DEBUG) {
          System.out.println("Carrying on");
        }
      }
      fallen = 0;
    }
  }

  private List<Block> getChainInOrder() {
    Collections.sort(chain, new Comparator<Block>() {
      @Override
      public int compare(Block b1, Block b2) {
        return new Integer(b1.getCurrentCell().getY()).compareTo(b2.getCurrentCell().getY());
      }
    });
    return chain;
  }

  @Override
  public boolean isFinished() {
    return landed;
  }

  @Override
  public void start() {
    for (Block block : chain) {
      block.setBusy(true);
    }
  }

  @Override
  public void end() {
    for (Block block : chain) {
      block.removeOffset();
      block.setBusy(false);
      grid.checkAndPerformChainClear(block.getX(), block.getY());
    }
  }
}
