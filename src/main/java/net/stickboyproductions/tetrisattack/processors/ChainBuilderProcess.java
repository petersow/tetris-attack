package net.stickboyproductions.tetrisattack.processors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.stickboyproductions.tetrisattack.constants.Directions;
import net.stickboyproductions.tetrisattack.enums.BlockState;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Grid;

import java.util.Set;

/**
 * User: Pete
 * Date: 20/11/12
 * Time: 22:13
 */
public class ChainBuilderProcess {

  public ChainBuilderProcess() {
  }

  public Set<Block> buildClearableChain(Block startBlock, Grid grid) {
    Set<Block> result = Sets.newHashSet();

    Set<Block> xChain = buildXChain(startBlock, grid);
    if (xChain.size() >= 3) {
      result.addAll(xChain);
    }
    Set<Block> yChain = buildYChain(startBlock, grid);
    for (Block block : ImmutableSet.copyOf(yChain)) {
      if (block.getY() == 0) {
        yChain.remove(block);
      }
    }
    if (yChain.size() >= 3) {
      result.addAll(yChain);
    }
    return result;
  }

  public Set<Block> buildXChain(Block startBlock, Grid grid) {
    Set<Block> result = Sets.newHashSet();
    result.addAll(buildChainForDirection(startBlock, grid, Directions.LEFT));
    result.addAll(buildChainForDirection(startBlock, grid, Directions.RIGHT));
    return result;
  }

  public Set<Block> buildYChain(Block startBlock, Grid grid) {
    Set<Block> result = Sets.newHashSet();
    result.addAll(buildChainForDirection(startBlock, grid, Directions.UP));
    result.addAll(buildChainForDirection(startBlock, grid, Directions.DOWN));
    return result;
  }

  private Set<Block> buildChainForDirection(Block startBlock,
                                            Grid grid, int direction) {
    if (startBlock == null || startBlock.getShape() == null) {
      return ImmutableSet.of();
    }

    Set<Block> result = Sets.newHashSet();
    result.add(startBlock);
    Block block = grid.getBlockToTheDirection(startBlock, direction);
    while (block != null &&
      BlockState.IDLE.equals(block.getBlockState()) &&
      startBlock.getShape().equals(block.getShape())) {
      result.add(block);
      block = grid.getBlockToTheDirection(block, direction);
    }
    return ImmutableSet.copyOf(result);
  }
}
