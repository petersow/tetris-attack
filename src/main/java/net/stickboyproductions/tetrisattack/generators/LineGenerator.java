package net.stickboyproductions.tetrisattack.generators;

import com.google.common.collect.Sets;
import net.stickboyproductions.tetrisattack.constants.GameConfig;
import net.stickboyproductions.tetrisattack.enums.BlockState;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Grid;
import net.stickboyproductions.tetrisattack.model.Shape;

import javax.inject.Inject;
import java.util.Set;

import static net.stickboyproductions.tetrisattack.constants.GameConfig.BLOCKS_IN_ROW_COUNT;

/**
 * User: Pete
 * Date: 02/01/13
 * Time: 23:43
 */
public class LineGenerator {

  private ShapeGenerator shapeGenerator;

  @Inject
  public LineGenerator(ShapeGenerator shapeGenerator) {
    this.shapeGenerator = shapeGenerator;
  }

  public Block[] generate(Grid grid) {
    Block[] result = new Block[GameConfig.BLOCKS_IN_ROW_COUNT];
    for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
      Block newBlock = new Block(grid, x, 0);
      newBlock.setBlockState(BlockState.IDLE);
      Set<Shape> avoidShapes = Sets.newHashSet();
      if (x >= 2) {
        Block blockLeft = result[x - 1];
        Block blockTwoLeft = result[x - 2];
        if (blockLeft.getShape().equals(blockTwoLeft.getShape())) {
          avoidShapes.add(blockLeft.getShape());
        }
      }

      Block blockAbove = grid.get(x, 0);
      if (blockAbove.getShape() != null) {
        avoidShapes.add(blockAbove.getShape());
      }
      newBlock.setShape(shapeGenerator.get(avoidShapes));
      result[x] = newBlock;
    }
    return result;
  }
}
