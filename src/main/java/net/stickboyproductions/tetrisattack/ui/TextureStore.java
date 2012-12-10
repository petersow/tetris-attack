package net.stickboyproductions.tetrisattack.ui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.stickboyproductions.tetrisattack.model.Shape;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static net.stickboyproductions.tetrisattack.constants.ScreenConfig.SPRITE_SIZE;

/**
 * User: Pete
 * Date: 27/10/12
 * Time: 22:49
 */
public class TextureStore {

  private static final Map<String, Texture> textureMap = Maps.newHashMap();

  private static final List<String> textureNames = Lists.newArrayList();

  static {
    // Could we do this by listing the files in the folder?
    textureNames.add("red");
    textureNames.add("blue");
    textureNames.add("green");
    textureNames.add("magenta");
    textureNames.add("red");
    textureNames.add("yellow");
    textureNames.add("cyan");
    textureNames.add("selected");
    textureNames.add("cell");
  }

  public TextureStore() {
  }

  public void init() {
    try {
      for (String name : textureNames) {
        textureMap.put(name, TextureLoader.getTexture("PNG",
          ResourceLoader.getResourceAsStream("sprites/" + SPRITE_SIZE + "/" + name + ".png"), true));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Texture get(String name) {
    return textureMap.get(name);
  }

  public Texture get(Shape shape) {
    return textureMap.get(shape.name().toLowerCase());
  }
}
