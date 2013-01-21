package net.stickboyproductions.tetrisattack.constants;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * User: Pete
 * Date: 21/01/13
 * Time: 20:38
 */
public final class ChainBonusPoints {

  private final static Integer MAX_CHAIN_SIZE = 13;

  private final static Map<Integer, Integer> POINTS_MAP = Maps.newHashMap();

  static {
    POINTS_MAP.put(1, 0);
    POINTS_MAP.put(2, 50);
    POINTS_MAP.put(3, 80);
    POINTS_MAP.put(4, 150);
    POINTS_MAP.put(5, 300);
    POINTS_MAP.put(6, 400);
    POINTS_MAP.put(7, 500);
    POINTS_MAP.put(8, 700);
    POINTS_MAP.put(9, 900);
    POINTS_MAP.put(10, 1100);
    POINTS_MAP.put(11, 1300);
    POINTS_MAP.put(12, 1500);
    POINTS_MAP.put(13, 1800);
  }

  public static Integer getBonusPointsValue(int chainNum) {
    if(chainNum > MAX_CHAIN_SIZE) {
      return POINTS_MAP.get(MAX_CHAIN_SIZE);
    }
    return POINTS_MAP.get(chainNum);
  }

}
