package net.stickboyproductions.tetrisattack.levelloader;

import net.stickboyproductions.tetrisattack.levelloader.model.Level;

import java.io.InputStream;

/**
 * User: Pete
 * Date: 13/01/13
 * Time: 20:44
 */
public interface LevelParser {
  public Level parse(InputStream inputStream) throws Exception;
}
