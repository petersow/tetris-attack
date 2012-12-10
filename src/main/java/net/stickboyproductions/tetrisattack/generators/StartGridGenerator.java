package net.stickboyproductions.tetrisattack.generators;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.stickboyproductions.tetrisattack.config.GameConfig;
import net.stickboyproductions.tetrisattack.model.*;
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
      Column column = grid.getColumns().get(pickedCol);

      // Can't be a chain if there's only 2
      Cell cell = column.get(column.getNumberFilled());
      fillCell(grid, cell);
    }
  }

  void fillCell(Grid grid, Cell cell) {
    Set<Shape> shapesToAvoid = Sets.newHashSet();

    shapesToAvoid.addAll(check(grid, cell, Grid.LEFT));
    shapesToAvoid.addAll(check(grid, cell, Grid.RIGHT));
    shapesToAvoid.addAll(check(grid, cell, Grid.DOWN));
    shapesToAvoid.addAll(check(grid, cell, Grid.UP));
    shapesToAvoid.addAll(checkLeftAndRight(grid, cell));
    shapesToAvoid.addAll(checkAboveAndBelow(grid, cell));

    new Block(shapeGenerator.get(shapesToAvoid), cell, drawableRegister);
  }

  private ImmutableSet<Shape> checkLeftAndRight(Grid grid, Cell cell) {
    Cell leftCell = grid.getCellToTheLeft(cell);
    if (leftCell != null && leftCell.getBlock() != null) {
      Cell rightCell = grid.getCellToTheRight(cell);
      if (rightCell != null && rightCell.getBlock() != null &&
        leftCell.getBlock().getShape().equals(rightCell.getBlock().getShape())) {
        return ImmutableSet.of(rightCell.getBlock().getShape());
      }
    }
    return ImmutableSet.of();
  }

  private ImmutableSet<Shape> checkAboveAndBelow(Grid grid, Cell cell) {
    Cell belowCell = grid.getCellBelow(cell);
    if (belowCell != null && belowCell.getBlock() != null) {
      Cell aboveCell = grid.getCellAbove(cell);
      if (aboveCell != null && aboveCell.getBlock() != null &&
        belowCell.getBlock().getShape().equals(aboveCell.getBlock().getShape())) {
        return ImmutableSet.of(aboveCell.getBlock().getShape());
      }
    }
    return ImmutableSet.of();
  }

  private ImmutableSet<Shape> check(Grid grid, Cell cell, int direction) {
    Cell nextCell = grid.getCellToTheDirection(cell, direction);
    if (nextCell != null && nextCell.getBlock() != null) {
      Cell nextNextCell = grid.getCellToTheDirection(nextCell, direction);
      if (nextNextCell != null && nextNextCell.getBlock() != null &&
        nextCell.getBlock().getShape().equals(nextNextCell.getBlock().getShape())) {
        return ImmutableSet.of(nextNextCell.getBlock().getShape());
      }
    }
    return ImmutableSet.of();
  }
}
