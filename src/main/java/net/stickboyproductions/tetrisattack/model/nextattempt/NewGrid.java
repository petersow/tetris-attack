package net.stickboyproductions.tetrisattack.model.nextattempt;

import static net.stickboyproductions.tetrisattack.config.GameConfig.BLOCKS_IN_ROW_COUNT;
import static net.stickboyproductions.tetrisattack.config.GameConfig.ROWS_IN_GRID;
import static net.stickboyproductions.tetrisattack.model.nextattempt.enums.Direction.*;

/**
 * User: Pete
 * Date: 27/11/12
 * Time: 22:47
 */
public class NewGrid {

  private NewBlock[][] grid =
    new NewBlock[BLOCKS_IN_ROW_COUNT][ROWS_IN_GRID];

  public NewGrid() {
  }

  public NewBlock get(int x, int y) {
    if (x < BLOCKS_IN_ROW_COUNT && x >= 0 && y >= 0 && y < ROWS_IN_GRID) {
      return grid[x][y];
    }
    return null;
  }

  public void init() {
    for (int y = 0; y < ROWS_IN_GRID; y++) {
      for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
        System.out.println(x + ", " + y);
        NewBlock newBlock = new NewBlock(this, x, y);
        grid[x][y] = newBlock;
      }
    }
  }

  public NewBlock getBlockToTheDirection(NewBlock block, int direction) {
    switch (direction) {
      case LEFT:
        return get(block.getX() - 1, block.getY());
      case RIGHT:
        return get(block.getX() + 1, block.getY());
      case UP:
        return get(block.getX(), block.getY() + 1);
      case DOWN:
        return get(block.getX(), block.getY() - 1);
    }
    return null;
  }
}
