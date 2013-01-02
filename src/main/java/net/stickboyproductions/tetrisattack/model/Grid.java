package net.stickboyproductions.tetrisattack.model;

import com.google.common.collect.ImmutableSet;
import net.stickboyproductions.tetrisattack.enums.BlockState;
import net.stickboyproductions.tetrisattack.generators.ShapeGenerator;

import javax.inject.Inject;

import static net.stickboyproductions.tetrisattack.constants.Directions.*;
import static net.stickboyproductions.tetrisattack.constants.GameConfig.BLOCKS_IN_ROW_COUNT;
import static net.stickboyproductions.tetrisattack.constants.GameConfig.ROWS_IN_GRID;

/**
 * The holder of Blocks for a game.
 * <p/>
 * User: Pete
 * Date: 27/11/12
 * Time: 22:47
 */
public class Grid {

  private Block[][] grid =
    new Block[BLOCKS_IN_ROW_COUNT][ROWS_IN_GRID];
  private ShapeGenerator shapeGenerator;

  @Inject
  public Grid(ShapeGenerator shapeGenerator) {
    this.shapeGenerator = shapeGenerator;
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

  public void moveAllUp() {
    Block[][] newGrid =
      new Block[BLOCKS_IN_ROW_COUNT][ROWS_IN_GRID];
    for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
      Block newBlock = new Block(this, x, 0);
      newBlock.setBlockState(BlockState.IDLE);
      Block blockAbove = grid[x][0];
      if (blockAbove.getShape() != null) {
        newBlock.setShape(shapeGenerator.get(ImmutableSet.of(blockAbove.getShape())));
      } else {
        newBlock.setShape(shapeGenerator.get());
      }
      newGrid[x][0] = newBlock;
    }

    for (int y = ROWS_IN_GRID - 2; y >= 0; y--) {
      for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
        newGrid[x][y + 1] = grid[x][y];
        grid[x][y].setY(y + 1);
      }
    }

    grid = newGrid;
  }
}
