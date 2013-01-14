package net.stickboyproductions.tetrisattack.levelloader.xml;

import net.stickboyproductions.tetrisattack.levelloader.LevelParser;
import net.stickboyproductions.tetrisattack.levelloader.model.Level;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;

/**
 * User: Pete
 * Date: 13/01/13
 * Time: 20:43
 */
public class XmlParser implements LevelParser {

  private static final Serializer serializer = new Persister();

  public Level parse(InputStream inputStream) throws Exception {
    return serializer.read(Level.class, inputStream);
  }

}
