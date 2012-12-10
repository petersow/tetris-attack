package net.stickboyproductions.tetrisattack.model.nextattempt.generators;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.stickboyproductions.tetrisattack.config.GameConfig;
import net.stickboyproductions.tetrisattack.generators.ShapeGenerator;
import net.stickboyproductions.tetrisattack.model.Grid;
import net.stickboyproductions.tetrisattack.model.Shape;
import net.stickboyproductions.tetrisattack.model.nextattempt.NewBlock;
import net.stickboyproductions.tetrisattack.model.nextattempt.NewGrid;
import net.stickboyproductions.tetrisattack.model.nextattempt.enums.BlockState;
import net.stickboyproductions.tetrisattack.model.nextattempt.enums.Direction;
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

  public void generate(NewGrid grid) {
    for (int i = 0; i < GameConfig.NUMBER_OF_START_CELLS; i++) {
      int pickedCol = rand.nextInt(6);
      for (int j = 0; j < GameConfig.ROWS_IN_GRID; j++) {
        NewBlock block = grid.get(pickedCol, j);
        if (BlockState.EMPTY.equals(block.getBlockState())) {
          fillBlock(grid, block);
          break;
        }
      }

    }
  }

  void fillBlock(NewGrid grid, NewBlock block) {
    Set<Shape> shapesToAvoid = Sets.newHashSet();

    shapesToAvoid.addAll(check(grid, block, Grid.LEFT));
    shapesToAvoid.addAll(check(grid, block, Grid.RIGHT));
    shapesToAvoid.addAll(check(grid, block, Grid.DOWN));
    shapesToAvoid.addAll(check(grid, block, Grid.UP));
    shapesToAvoid.addAll(checkLeftAndRight(grid, block));
    shapesToAvoid.addAll(checkAboveAndBelow(grid, block));

    block.setBlockState(BlockState.IDLE);
    block.setShape(shapeGenerator.get(shapesToAvoid));
  }

  private ImmutableSet<Shape> checkLeftAndRight(NewGrid grid, NewBlock block) {
    NewBlock leftBlock = grid.getBlockToTheDirection(block, Direction.LEFT);
    if (leftBlock != null && leftBlock.getShape() != null) {
      NewBlock rightBlock = grid.getBlockToTheDirection(block, Direction.RIGHT);
      if (rightBlock != null && rightBlock.getShape() != null &&
        leftBlock.getShape().equals(rightBlock.getShape())) {
        return ImmutableSet.of(rightBlock.getShape());
      }
    }
    return ImmutableSet.of();
  }

  private ImmutableSet<Shape> checkAboveAndBelow(NewGrid grid, NewBlock block) {
    NewBlock belowBlock = grid.getBlockToTheDirection(block, Direction.DOWN);
    if (belowBlock != null && belowBlock.getShape() != null) {
      NewBlock aboveBlock = grid.getBlockToTheDirection(block, Direction.UP);
      if (aboveBlock != null && aboveBlock.getShape() != null &&
        belowBlock.getShape().equals(aboveBlock.getShape())) {
        return ImmutableSet.of(aboveBlock.getShape());
      }
    }
    return ImmutableSet.of();
  }

  private ImmutableSet<Shape> check(NewGrid grid, NewBlock block, int direction) {
    NewBlock nextBlock = grid.getBlockToTheDirection(block, direction);
    if (nextBlock != null && nextBlock.getShape() != null) {
      NewBlock nextNextBlock = grid.getBlockToTheDirection(block, direction);
      if (nextNextBlock != null && nextNextBlock.getShape() != null &&
        nextBlock.getShape().equals(nextNextBlock.getShape())) {
        return ImmutableSet.of(nextNextBlock.getShape());
      }
    }
    return ImmutableSet.of();
  }
}
