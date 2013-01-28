package net.stickboyproductions.tetrisattack.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Wrapper object to represent the score of the game round
 * <p/>
 * User: Pete
 * Date: 26/11/12
 * Time: 22:26
 */
public class Speed {
  private int speed;
  private int blockCleared;
  private int nextSpeedUp;

  private static final int MAX_SPEED = 50;

  private static Map<Integer, Integer> blocksTillNextLevel = Maps.newHashMap();
  private static Map<Integer, Integer> timeTillNextStep = Maps.newHashMap();

  static {
    blocksTillNextLevel.put(2, 10);
    blocksTillNextLevel.put(3, 10);
    blocksTillNextLevel.put(4, 10);
    blocksTillNextLevel.put(5, 10);
    blocksTillNextLevel.put(6, 15);
    blocksTillNextLevel.put(7, 15);
    blocksTillNextLevel.put(8, 15);
    blocksTillNextLevel.put(9, 15);
    blocksTillNextLevel.put(10, 15);
    blocksTillNextLevel.put(11, 17);
    blocksTillNextLevel.put(12, 17);
    blocksTillNextLevel.put(13, 17);
    blocksTillNextLevel.put(14, 17);
    blocksTillNextLevel.put(15, 17);
    blocksTillNextLevel.put(16, 20);
    blocksTillNextLevel.put(17, 20);
    blocksTillNextLevel.put(18, 20);
    blocksTillNextLevel.put(19, 20);
    blocksTillNextLevel.put(20, 20);
    blocksTillNextLevel.put(21, 22);
    blocksTillNextLevel.put(22, 22);
    blocksTillNextLevel.put(23, 22);
    blocksTillNextLevel.put(24, 22);
    blocksTillNextLevel.put(25, 22);
    blocksTillNextLevel.put(26, 25);
    blocksTillNextLevel.put(27, 25);
    blocksTillNextLevel.put(28, 25);
    blocksTillNextLevel.put(29, 25);
    blocksTillNextLevel.put(30, 25);
    blocksTillNextLevel.put(31, 27);
    blocksTillNextLevel.put(32, 27);
    blocksTillNextLevel.put(33, 27);
    blocksTillNextLevel.put(34, 27);
    blocksTillNextLevel.put(35, 27);
    blocksTillNextLevel.put(36, 30);
    blocksTillNextLevel.put(37, 30);
    blocksTillNextLevel.put(38, 30);
    blocksTillNextLevel.put(39, 30);
    blocksTillNextLevel.put(40, 30);
    blocksTillNextLevel.put(41, 32);
    blocksTillNextLevel.put(42, 32);
    blocksTillNextLevel.put(43, 32);
    blocksTillNextLevel.put(44, 32);
    blocksTillNextLevel.put(45, 32);
    blocksTillNextLevel.put(46, 35);
    blocksTillNextLevel.put(47, 35);
    blocksTillNextLevel.put(48, 35);
    blocksTillNextLevel.put(49, 35);
    blocksTillNextLevel.put(50, 35);

    timeTillNextStep.put(1, 1000);
    timeTillNextStep.put(2, 950);
    timeTillNextStep.put(3, 900);
    timeTillNextStep.put(4, 850);
    timeTillNextStep.put(5, 800);
    timeTillNextStep.put(6, 750);
    timeTillNextStep.put(7, 700);
    timeTillNextStep.put(8, 650);
    timeTillNextStep.put(9, 600);
    timeTillNextStep.put(10, 550);
    timeTillNextStep.put(11, 500);
    timeTillNextStep.put(12, 480);
    timeTillNextStep.put(13, 460);
    timeTillNextStep.put(14, 440);
    timeTillNextStep.put(15, 420);
    timeTillNextStep.put(16, 400);
    timeTillNextStep.put(17, 380);
    timeTillNextStep.put(18, 360);
    timeTillNextStep.put(19, 340);
    timeTillNextStep.put(20, 320);
    timeTillNextStep.put(21, 300);
    timeTillNextStep.put(22, 285);
    timeTillNextStep.put(23, 270);
    timeTillNextStep.put(24, 255);
    timeTillNextStep.put(25, 240);
    timeTillNextStep.put(26, 225);
    timeTillNextStep.put(27, 210);
    timeTillNextStep.put(28, 195);
    timeTillNextStep.put(29, 185);
    timeTillNextStep.put(30, 175);
    timeTillNextStep.put(31, 165);
    timeTillNextStep.put(32, 155);
    timeTillNextStep.put(33, 145);
    timeTillNextStep.put(34, 135);
    timeTillNextStep.put(35, 125);
    timeTillNextStep.put(36, 115);
    timeTillNextStep.put(37, 105);
    timeTillNextStep.put(38, 100);
    timeTillNextStep.put(39, 95);
    timeTillNextStep.put(40, 90);
    timeTillNextStep.put(41, 85);
    timeTillNextStep.put(42, 80);
    timeTillNextStep.put(43, 75);
    timeTillNextStep.put(44, 70);
    timeTillNextStep.put(45, 65);
    timeTillNextStep.put(46, 60);
    timeTillNextStep.put(47, 55);
    timeTillNextStep.put(48, 50);
    timeTillNextStep.put(49, 45);
    timeTillNextStep.put(50, 40);
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
    if (blockCleared >= nextSpeedUp) {
      speed++;
      if (speed + 1 < MAX_SPEED) {
        nextSpeedUp += blocksTillNextLevel.get(speed + 1);
      } else {
        // Never level up again
        nextSpeedUp = Integer.MAX_VALUE;
      }
    }
  }
}
