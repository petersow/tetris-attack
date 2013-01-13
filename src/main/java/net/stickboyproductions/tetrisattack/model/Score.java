package net.stickboyproductions.tetrisattack.model;

/**
 * Wrapper object to represent the score of the game round
 *
 * User: Pete
 * Date: 26/11/12
 * Time: 22:26
 */
public class Score {
  private int score;

  public Score() {
  }

  public void addToScore(int i) {
    score += i;
  }

  public int getScore() {
    return score;
  }
}
