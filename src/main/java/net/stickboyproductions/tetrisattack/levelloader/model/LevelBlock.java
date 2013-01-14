package net.stickboyproductions.tetrisattack.levelloader.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * User: Pete
 * Date: 13/01/13
 * Time: 20:46
 */
@Root(name = "block")
public class LevelBlock {

  @Attribute
  private Integer x;
  @Attribute
  private Integer y;
  @Attribute(name = "shape")
  private String shapeName;

  public Integer getX() {
    return x;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public Integer getY() {
    return y;
  }

  public void setY(Integer y) {
    this.y = y;
  }

  public String getShapeName() {
    return shapeName;
  }

  public void setShapeName(String shapeName) {
    this.shapeName = shapeName;
  }
}
