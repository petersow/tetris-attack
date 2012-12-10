package net.stickboyproductions.tetrisattack.model;

import java.awt.*;

import static net.stickboyproductions.tetrisattack.config.ScreenConfig.CELL_HEIGHT;
import static net.stickboyproductions.tetrisattack.config.ScreenConfig.CELL_WIDTH;


/**
 * A single cell in the game grid
 * <p/>
 * User: Pete
 * Date: 27/10/12
 * Time: 17:46
 */
public class Cell {

  private Quad quad;

  private int x;
  private int y;

  private Block block;

  public Cell(int x, int y) {
    this.x = x;
    this.y = y;

    this.quad = new Quad(new Point(x * CELL_WIDTH, y * CELL_HEIGHT),
      new Point((x + 1) * CELL_WIDTH, y * CELL_HEIGHT),
      new Point((x + 1) * CELL_WIDTH, (y + 1) * CELL_HEIGHT),
      new Point(x * CELL_WIDTH, (y + 1) * CELL_HEIGHT));
  }

  public void offset(int xOffset, int yOffset) {
    quad.offset(xOffset, yOffset);
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public Quad getQuad() {
    return quad;
  }

  public Block getBlock() {
    return block;
  }

  public void setBlock(Block block) {
    this.block = block;
  }
}
