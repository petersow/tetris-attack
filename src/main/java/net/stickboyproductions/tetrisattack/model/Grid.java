package net.stickboyproductions.tetrisattack.model;

import static net.stickboyproductions.tetrisattack.constants.GameConfig.BLOCKS_IN_ROW_COUNT;
import static net.stickboyproductions.tetrisattack.constants.GameConfig.ROWS_IN_GRID;
import static net.stickboyproductions.tetrisattack.constants.Directions.*;

/**
 * User: Pete
 * Date: 27/11/12
 * Time: 22:47
 */
public class Grid {

  private Block[][] grid =
    new Block[BLOCKS_IN_ROW_COUNT][ROWS_IN_GRID];

  public Grid() {
  }

  public Block get(int x, int y) {
    if (x < BLOCKS_IN_ROW_COUNT && x >= 0 && y >= 0 && y < ROWS_IN_GRID) {
      return grid[x][y];
    }
    return null;
  }

  public void init() {
    for (int y = 0; y < ROWS_IN_GRID; y++) {
      for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
        System.out.println(x + ", " + y);
        Block newBlock = new Block(this, x, y);
        grid[x][y] = newBlock;
      }
    }
  }

  public Block getBlockToTheDirection(Block block, int direction) {
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
