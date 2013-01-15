package net.stickboyproductions.tetrisattack.actions;

import com.google.common.collect.Lists;
import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Game;

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
  private int chainNum;

  public Combo(Set<Block> blockSet, Game game, int chainNum) {
    this.blockSet = blockSet;
    this.game = game;
    this.chainNum = chainNum;
  }

  @Override
  public void tick(long timeElapsed) {
    for(BlockDestroy blockDestroy : blockDestroyGroup) {
      blockDestroy.tick(timeElapsed);
    }
  }

  @Override
  public boolean isFinished() {
    for(BlockDestroy blockDestroy : blockDestroyGroup) {
      if(!blockDestroy.isFinished()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void start() {
    for (Block next : blockSet) {
      BlockDestroy newBlockDestroy = new BlockDestroy(next, 0, game.getScore());
      blockDestroyGroup.add(newBlockDestroy);
      newBlockDestroy.start();
    }
    game.getGridMoveUp().pause();
  }

  @Override
  public void end() {
    for(BlockDestroy blockDestroy : blockDestroyGroup) {
      blockDestroy.end();
    }
    game.getGridMoveUp().resume();
    game.setChainFinished(chainNum);
    System.out.println(chainNum +  " chain done");
  }
}
