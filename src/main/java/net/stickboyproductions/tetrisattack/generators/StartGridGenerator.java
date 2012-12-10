package net.stickboyproductions.tetrisattack.generators;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.stickboyproductions.tetrisattack.constants.Directions;
import net.stickboyproductions.tetrisattack.constants.GameConfig;
import net.stickboyproductions.tetrisattack.model.Shape;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Grid;
import net.stickboyproductions.tetrisattack.enums.BlockState;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;

import javax.inject.Inject;
import java.util.Random;
import java.util.Set;

/**
 * User: Pete
 * Date: 28/10/12
 * Time: 22:22
 */
public class StartGridGenerator {

  private final Random rand = new Random(System.currentTimeMillis());
  private DrawableRegister drawableRegister;
  private ShapeGenerator shapeGenerator;

  @Inject
  public StartGridGenerator(DrawableRegister drawableRegister,
                            ShapeGenerator shapeGenerator) {
    this.drawableRegister = drawableRegister;
    this.shapeGenerator = shapeGenerator;
  }

  public void generate(Grid grid) {
    for (int i = 0; i < GameConfig.NUMBER_OF_START_CELLS; i++) {
      int pickedCol = rand.nextInt(6);
      for (int j = 0; j < GameConfig.ROWS_IN_GRID; j++) {
        Block block = grid.get(pickedCol, j);
        if (BlockState.EMPTY.equals(block.getBlockState())) {
          fillBlock(grid, block);
          break;
        }
      }

    }
  }

  void fillBlock(Grid grid, Block block) {
    Set<Shape> shapesToAvoid = Sets.newHashSet();

    shapesToAvoid.addAll(check(grid, block, Directions.LEFT));
    shapesToAvoid.addAll(check(grid, block, Directions.RIGHT));
    shapesToAvoid.addAll(check(grid, block, Directions.DOWN));
    shapesToAvoid.addAll(check(grid, block, Directions.UP));
    shapesToAvoid.addAll(checkLeftAndRight(grid, block));
    shapesToAvoid.addAll(checkAboveAndBelow(grid, block));

    block.setBlockState(BlockState.IDLE);
    block.setShape(shapeGenerator.get(shapesToAvoid));
  }

  private ImmutableSet<Shape> checkLeftAndRight(Grid grid, Block block) {
    Block leftBlock = grid.getBlockToTheDirection(block, Directions.LEFT);
    if (leftBlock != null && leftBlock.getShape() != null) {
      Block rightBlock = grid.getBlockToTheDirection(block, Directions.RIGHT);
      if (rightBlock != null && rightBlock.getShape() != null &&
        leftBlock.getShape().equals(rightBlock.getShape())) {
        return ImmutableSet.of(rightBlock.getShape());
      }
    }
    return ImmutableSet.of();
  }

  private ImmutableSet<Shape> checkAboveAndBelow(Grid grid, Block block) {
    Block belowBlock = grid.getBlockToTheDirection(block, Directions.DOWN);
    if (belowBlock != null && belowBlock.getShape() != null) {
      Block aboveBlock = grid.getBlockToTheDirection(block, Directions.UP);
      if (aboveBlock != null && aboveBlock.getShape() != null &&
        belowBlock.getShape().equals(aboveBlock.getShape())) {
        return ImmutableSet.of(aboveBlock.getShape());
      }
    }
    return ImmutableSet.of();
  }

  private ImmutableSet<Shape> check(Grid grid, Block block, int direction) {
    Block nextBlock = grid.getBlockToTheDirection(block, direction);
    if (nextBlock != null && nextBlock.getShape() != null) {
      Block nextNextBlock = grid.getBlockToTheDirection(block, direction);
      if (nextNextBlock != null && nextNextBlock.getShape() != null &&
        nextBlock.getShape().equals(nextNextBlock.getShape())) {
        return ImmutableSet.of(nextNextBlock.getShape());
      }
    }
    return ImmutableSet.of();
  }
}
