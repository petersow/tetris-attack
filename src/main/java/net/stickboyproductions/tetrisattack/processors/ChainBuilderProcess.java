package net.stickboyproductions.tetrisattack.processors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.BlockChain;
import net.stickboyproductions.tetrisattack.model.Cell;
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

  public BlockChain buildChain(Cell startCell, Grid grid) {
    Set<Block> xChain = buildXChain(startCell, grid);
    Set<Block> yChain = buildYChain(startCell, grid);
    return new BlockChain(xChain, yChain);
  }

  public Set<Block> buildXChain(Cell startCell, Grid grid) {
    Set result = Sets.newHashSet();
    result.addAll(buildChainForDirection(startCell, grid, Grid.LEFT));
    result.addAll(buildChainForDirection(startCell, grid, Grid.RIGHT));
    return result;
  }

  public Set<Block> buildYChain(Cell startCell, Grid grid) {
    Set result = Sets.newHashSet();
    result.addAll(buildChainForDirection(startCell, grid, Grid.UP));
    result.addAll(buildChainForDirection(startCell, grid, Grid.DOWN));
    return result;
  }

  private Set<Block> buildChainForDirection(Cell startCell, Grid grid, int direction) {
    if (startCell.getBlock() == null) {
      return ImmutableSet.of();
    }

    Set<Block> result = Sets.newHashSet();
    result.add(startCell.getBlock());

    Cell cell = grid.getCellToTheDirection(startCell, direction);
    while (cell != null && cell.getBlock() != null &&
      !cell.getBlock().isBusy() &&
      cell.getBlock().sameBlockType(startCell.getBlock())) {
      result.add(cell.getBlock());
      cell = grid.getCellToTheDirection(cell, direction);
    }
    return ImmutableSet.copyOf(result);
  }
}
