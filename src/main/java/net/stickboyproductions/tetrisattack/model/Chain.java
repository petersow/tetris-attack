package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.constants.ChainBonusPoints;

/**
 * User: Pete
 * Date: 20/01/13
 * Time: 13:41
 */
public class Chain {

  private int chainCount = 0;
  private Game game;

  public Chain(Game game) {
    this.game = game;
  }

  public void increment() {
    chainCount++;
    game.getScore().addToScore(ChainBonusPoints.getBonusPointsValue(chainCount));
  }

  public int getChainCount() {
    return chainCount;
  }

  public void setChainCount(int chainCount) {
    this.chainCount = chainCount;
  }
}
