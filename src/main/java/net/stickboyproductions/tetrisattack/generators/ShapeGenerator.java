package net.stickboyproductions.tetrisattack.generators;

import net.stickboyproductions.tetrisattack.model.Shape;

import java.util.Random;
import java.util.Set;

/**
 * User: Pete
 * Date: 27/10/12
 * Time: 22:43
 */
public class ShapeGenerator {

  private static final Random random = new Random(System.currentTimeMillis() * 100);

  public Shape get() {
    int i = random.nextInt(Shape.values().length);
    return Shape.values()[i];
  }

  public Shape get(Set<Shape> avoid) {
    Shape shape = Shape.values()[random.nextInt(Shape.values().length)];
    while(avoid.contains(shape)) {
      shape = Shape.values()[random.nextInt(Shape.values().length)];
    }
    return shape;
  }
}
