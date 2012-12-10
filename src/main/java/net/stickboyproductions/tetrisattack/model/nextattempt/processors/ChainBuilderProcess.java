package net.stickboyproductions.tetrisattack.model.nextattempt.processors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.stickboyproductions.tetrisattack.model.Grid;
import net.stickboyproductions.tetrisattack.model.nextattempt.NewBlock;
import net.stickboyproductions.tetrisattack.model.nextattempt.NewGrid;
import net.stickboyproductions.tetrisattack.model.nextattempt.enums.BlockState;

import java.util.Set;

/**
 * User: Pete
 * Date: 20/11/12
 * Time: 22:13
 */
public class ChainBuilderProcess {

  public ChainBuilderProcess() {
  }

  public Set<NewBlock> buildClearableChain(NewBlock startBlock, NewGrid grid) {
    Set<NewBlock> result = Sets.newHashSet();

    Set<NewBlock> xChain = buildXChain(startBlock, grid);
    if (xChain.size() >= 3) {
      result.addAll(xChain);
    }
    Set<NewBlock> yChain = buildYChain(startBlock, grid);
    for (NewBlock block : ImmutableSet.copyOf(yChain)) {
      if (block.getY() == 0) {
        yChain.remove(block);
      }
    }
    if (yChain.size() >= 3) {
      result.addAll(yChain);
    }
    return result;
  }

  public Set<NewBlock> buildXChain(NewBlock startBlock, NewGrid grid) {
    Set<NewBlock> result = Sets.newHashSet();
    result.addAll(buildChainForDirection(startBlock, grid, Grid.LEFT));
    result.addAll(buildChainForDirection(startBlock, grid, Grid.RIGHT));
    return result;
  }

  public Set<NewBlock> buildYChain(NewBlock startBlock, NewGrid grid) {
    Set<NewBlock> result = Sets.newHashSet();
    result.addAll(buildChainForDirection(startBlock, grid, Grid.UP));
    result.addAll(buildChainForDirection(startBlock, grid, Grid.DOWN));
    return result;
  }

  private Set<NewBlock> buildChainForDirection(NewBlock startBlock,
                                               NewGrid grid, int direction) {
    if (startBlock == null || startBlock.getShape() == null) {
      return ImmutableSet.of();
    }

    Set<NewBlock> result = Sets.newHashSet();
    result.add(startBlock);
    NewBlock block = grid.getBlockToTheDirection(startBlock, direction);
    while (block != null &&
      BlockState.IDLE.equals(block.getBlockState()) &&
      startBlock.getShape().equals(block.getShape())) {
      result.add(block);
      block = grid.getBlockToTheDirection(block, direction);
    }
    return ImmutableSet.copyOf(result);
  }
}
