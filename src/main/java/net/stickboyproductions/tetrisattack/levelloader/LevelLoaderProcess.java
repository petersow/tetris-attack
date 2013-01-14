package net.stickboyproductions.tetrisattack.levelloader;

import net.stickboyproductions.tetrisattack.levelloader.model.Level;
import net.stickboyproductions.tetrisattack.levelloader.model.LevelBlock;
import net.stickboyproductions.tetrisattack.levelloader.xml.XmlParser;
import net.stickboyproductions.tetrisattack.model.Grid;
import net.stickboyproductions.tetrisattack.model.Shape;

/**
 * User: Pete
 * Date: 13/01/13
 * Time: 20:38
 */
public class LevelLoaderProcess {

  // Probably should be injected?
  LevelParser levelParser = new XmlParser();

  public void load(String levelName, Grid outputGrid) throws Exception {
    Level level =
      levelParser.parse(LevelLoaderProcess.class.getResourceAsStream("/levels/" + levelName));

    for(LevelBlock levelBlock : level.getBlocks()) {
      outputGrid.get(levelBlock.getX(), levelBlock.getY()).init(Shape.create(levelBlock.getShapeName()));
    }
  }

}
