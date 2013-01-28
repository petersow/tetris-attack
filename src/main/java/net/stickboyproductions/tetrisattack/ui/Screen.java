package net.stickboyproductions.tetrisattack.ui;

import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Quad;
import net.stickboyproductions.tetrisattack.model.Shape;

/**
 * User: Pete
 * Date: 27/10/12
 * Time: 17:32
 */
public interface Screen {
  public void create() throws Exception;

  public void clear();

  public void update();

  public void destroy();

  public void drawText(int x, int y, String text);

  public void drawTextAtBlock(Block block, int offsetX, int offsetY, String text);

  public void drawShape(int x, int y, int offsetX, int offsetY,
                        Shape shape, double framePosition, boolean dark);

  public void drawShape(Quad quad, Shape shape, double framePosition, boolean dark);

  public void drawSprite(Quad quad, String spriteName, double framePosition);

  public void drawSprite(int x, int y, String spriteName, int offsetX, int offsetY, double framePosition);

  public void drawSprite(Quad quad, String spriteName, double framePosition, boolean dark);

  boolean isCloseRequested();
}
