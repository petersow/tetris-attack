package net.stickboyproductions.tetrisattack.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * User: Pete
 * Date: 29/10/12
 * Time: 23:22
 */
public class BlockChain extends HashSet<Block> {

  private final Set<Block> xChain;
  private final Set<Block> yChain;

  private List<Block> clearableChain;

  public BlockChain(Set<Block> xChain, Set<Block> yChain) {
    this.xChain = xChain;
    this.yChain = yChain;

    addAll(xChain);
    for (Block block : yChain) {
      if (!contains(block)) {
        add(block);
      }
    }
  }

  public Set<Block> getXChain() {
    return xChain;
  }

  public Set<Block> getYChain() {
    return yChain;
  }

  public List<Block> getClearableChain() {
    if (clearableChain == null) {
      if (xChain.size() >= 3 && yChain.size() >= 3) {
        clearableChain = getChainInOrder(Lists.newArrayList(this));
      } else if (xChain.size() >= 3) {
        clearableChain = getChainInOrder(Lists.newArrayList(xChain));
      } else if (yChain.size() >= 3) {
        clearableChain = getChainInOrder(Lists.newArrayList(yChain));
      } else {
        clearableChain = ImmutableList.of();
      }
    }
    return clearableChain;
  }

  private List<Block> getChainInOrder(List<Block> input) {
    Collections.sort(input, new Comparator<Block>() {
      @Override
      public int compare(Block b1, Block b2) {
        return new Integer(b1.getCurrentCell().getY()).compareTo(b2.getCurrentCell().getY());
      }
    });
    return input;
  }

  public int getScoreValue() {
    return getClearableChain().size() * 10;
  }
}
