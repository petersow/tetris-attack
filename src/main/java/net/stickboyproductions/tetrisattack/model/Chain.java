package net.stickboyproductions.tetrisattack.model;

/**
 * User: Pete
 * Date: 20/01/13
 * Time: 13:41
 */
public class Chain {

  private int chainCount = 1;

  public Chain() {
  }

  public void increment() {
    chainCount++;
  }

  public int getChainCount() {
    return chainCount;
  }

  public void setChainCount(int chainCount) {
    this.chainCount = chainCount;
  }
}
