package net.stickboyproductions.tetrisattack.levelloader.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * User: Pete
 * Date: 13/01/13
 * Time: 20:42
 */
@Root
public class Level {
  @ElementList
  private List<LevelBlock> blocks;

  @Attribute
  private String name;

  public String getName() {
    return name;
  }

  public List<LevelBlock> getBlocks() {
    return blocks;
  }
}
