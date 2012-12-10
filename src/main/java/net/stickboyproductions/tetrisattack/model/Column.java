package net.stickboyproductions.tetrisattack.model;

import com.google.common.collect.Lists;
import net.stickboyproductions.tetrisattack.Clock;
import net.stickboyproductions.tetrisattack.actions.BlockFall;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Pete
 * Date: 28/10/12
 * Time: 17:30
 */
public class Column extends ArrayList<Cell> {

  private int x;
  private Grid grid;
  private Clock clock;

  public Column(int x, Grid grid, Clock clock) {
    this.x = x;
    this.grid = grid;
    this.clock = clock;
  }

  public boolean canCellFall(int y) {
    Cell startCell = get(y);
    if (startCell.getBlock() == null || startCell.getBlock().isBusy()) {
      return false;
    }
    Cell cellBelow = grid.getCellBelow(startCell);
    if (cellBelow != null && cellBelow.getBlock() == null) {
      return true;
    }
    return false;
  }

  public void fallCellsAboveIntoHole(int yOfHole) {
    Cell cell = get(yOfHole);
    List<Block> yChain = Lists.newArrayList();
    while (grid.getCellAbove(cell) != null) {
      cell = grid.getCellAbove(cell);
      if (cell.getBlock() != null) {
        yChain.add(cell.getBlock());
      }
    }
    if (yChain.size() > 0) {
      BlockFall blockFall = new BlockFall(yChain, grid);
      clock.register(blockFall);
    }
  }

  public boolean fallIfCellBelowIsEmpty(int y) {
    if (canCellFall(y)) {
      BlockFall blockFall = new BlockFall(Lists.newArrayList(get(y).getBlock()), grid);
      clock.register(blockFall);
      return true;
    }
    return false;
  }

  public void checkAndFallAll() {
    for(int i = 0; i < size(); i++) {
      fallIfCellBelowIsEmpty(i);
    }
  }

  public int getNumberFilled() {
    int numberFilled = 0;
    Iterator<Cell> iterator = iterator();
    while (iterator.hasNext()) {
      if (iterator.next().getBlock() != null) {
        numberFilled++;
      }
    }
    return numberFilled;
  }
}
