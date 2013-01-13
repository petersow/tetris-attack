package net.stickboyproductions.tetrisattack.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Wrapper object to represent the score of the game round
 *
 * User: Pete
 * Date: 26/11/12
 * Time: 22:26
 */
public class Speed {
  private int speed;
  private int blockCleared;
  private int nextSpeedUp;

  private static Map<Integer, Integer> blocksTillNextLevel = Maps.newHashMap();
  private static Map<Integer, Integer> timeTillNextStep = Maps.newHashMap();

  static {
    blocksTillNextLevel.put(2, 9);
    blocksTillNextLevel.put(3, 12);
    blocksTillNextLevel.put(4, 12);
    blocksTillNextLevel.put(5, 12);
    blocksTillNextLevel.put(6, 12);
    blocksTillNextLevel.put(7, 12);
    blocksTillNextLevel.put(8, 15);
    blocksTillNextLevel.put(9, 15);
    blocksTillNextLevel.put(10, 16);

    timeTillNextStep.put(1, 1000);
    timeTillNextStep.put(2, 900);
    timeTillNextStep.put(3, 800);
    timeTillNextStep.put(4, 700);
    timeTillNextStep.put(5, 600);
    timeTillNextStep.put(6, 500);
    timeTillNextStep.put(7, 400);
    timeTillNextStep.put(8, 300);
    timeTillNextStep.put(9, 200);
    timeTillNextStep.put(10, 66);
  }

  public Speed(int initialSpeed) {
    speed = initialSpeed;
    nextSpeedUp = blocksTillNextLevel.get(initialSpeed + 1);
  }

  public int getSpeed() {
    return speed;
  }

  public int getNextStepTime() {
    return timeTillNextStep.get(speed);
  }

  public void updateBlocksCleared(int size) {
    blockCleared += size;
    if(blockCleared >= nextSpeedUp) {
      speed++;
      if(blocksTillNextLevel.containsKey(speed + 1)) {
        nextSpeedUp += blocksTillNextLevel.get(speed + 1);
      } else {
        // Never level up again
        nextSpeedUp = Integer.MAX_VALUE;
      }
    }
  }
}
