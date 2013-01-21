package net.stickboyproductions.tetrisattack.actions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Chain;
import net.stickboyproductions.tetrisattack.model.Game;
import net.stickboyproductions.tetrisattack.timing.GameClock;
import net.stickboyproductions.tetrisattack.ui.ChainNotification;
import net.stickboyproductions.tetrisattack.ui.ComboNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: Pete
 * Date: 29/10/12
 * Time: 21:35
 */
public class Combo implements TimeTickingAction {

  private Set<Block> blockSet;
  List<BlockDestroy> blockDestroyGroup = Lists.newArrayList();
  private Game game;
  private GameClock gameClock;
  private Chain chain;

  private ChainNotification chainNotification;
  private ComboNotification comboNotification;

  public Combo(Set<Block> blockSet, Game game, GameClock gameClock) {
    this.blockSet = blockSet;
    this.game = game;
    this.gameClock = gameClock;
    this.chain = new Chain(game);
  }

  public Combo(Set<Block> blockSet, Game game, GameClock gameClock, Chain chain) {
    this.blockSet = blockSet;
    this.game = game;
    this.gameClock = gameClock;
    this.chain = chain;
  }

  @Override
  public void tick(long timeElapsed) {
    for (BlockDestroy blockDestroy : blockDestroyGroup) {
      blockDestroy.tick(timeElapsed);
    }
  }

  @Override
  public boolean isFinished() {
    for (BlockDestroy blockDestroy : blockDestroyGroup) {
      if (!blockDestroy.isFinished()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void start() {
    chain.increment();

    // Add bonus points to the games score and speed
    int comboOverThree = blockSet.size() - 3;
    if (comboOverThree > 0) {
      game.getScore().addToScore((10 * comboOverThree) + 10);
    }
    game.getSpeed().updateBlocksCleared(blockSet.size());

    // Create the comboNotification (if applicable)
    if (blockSet.size() > 3) {
      comboNotification =
        new ComboNotification(getTopLeftBlock(ImmutableList.copyOf(blockSet)), blockSet.size());
      game.getDrawableRegister().register(comboNotification);
      gameClock.register(comboNotification);
    }
    ArrayList<Block> blocks = Lists.newArrayList(blockSet);
    int count = 0;
    while (blocks.size() > 0) {
      // Get the TopLeftBlock and create its falling animation
      Block topLeftBlock = getTopLeftBlock(blocks);
      // Also create the chainNotification (if applicable)
      if (count == 0 && chain.getChainCount() > 1) {
        chainNotification = new ChainNotification(topLeftBlock, chain.getChainCount());
        game.getDrawableRegister().register(chainNotification);
        gameClock.register(chainNotification);
      }
      BlockDestroy newBlockDestroy
        = new BlockDestroy(topLeftBlock, count, game.getScore());
      blockDestroyGroup.add(newBlockDestroy);
      newBlockDestroy.start();

      // Setup for next loop
      blocks.remove(topLeftBlock);
      count++;
    }
    // Pause the grid moving up until all the blocks have destroyed themselves
    game.getGridMoveUp().pause();
  }

  // We define the origin as the toppest, leftest block
  private Block getTopLeftBlock(List<Block> blocks) {
    System.out.println(blocks.size());
    Block originBlock = null;
    for (Block block : blocks) {
      if (originBlock == null || block.getY() > originBlock.getY()) {
        originBlock = block;
      } else if (block.getY() == originBlock.getY() &&
        block.getX() < originBlock.getX()) {
        originBlock = block;
      }
    }
    return originBlock;
  }

  @Override
  public void end() {
    if (chainNotification != null) {
      game.getDrawableRegister().unregister(chainNotification);
    }
    if (comboNotification != null) {
      game.getDrawableRegister().unregister(comboNotification);
    }
    for (BlockDestroy blockDestroy : blockDestroyGroup) {
      blockDestroy.end();
    }
    game.getGridMoveUp().resume();
    game.setChainFinished(chain);
    System.out.println(chain + " chain done");
  }
}
