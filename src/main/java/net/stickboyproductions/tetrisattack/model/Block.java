package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import net.stickboyproductions.tetrisattack.ui.Screen;

/**
 * The things the player moves around to match in a row
 * <p/>
 * User: Pete
 * Date: 27/10/12
 * Time: 22:28
 */
public class Block implements Drawable {

  private Shape shape;
  private DrawableRegister drawableRegister;
  private int y;
  private int x;
  private Cell currentCell;

  private int offsetX = 0;
  private int offsetY = 0;

  private double frame = 0.0;
  private boolean drawable = true;
  private boolean busy = false;

  public Block(Shape shape, Cell cell, DrawableRegister drawableRegister) {
    this.shape = shape;
    this.drawableRegister = drawableRegister;
    drawableRegister.register(this);
    setCurrentCell(cell);
  }

  public Shape getShape() {
    return shape;
  }

  public void setShape(Shape shape) {
    this.shape = shape;
  }

  @Override
  public void draw(Screen screen) {
    if (drawable) {
      screen.drawShape(currentCell.getQuad().getWithOffset(offsetX, offsetY), shape, frame, y == 0);
      screen.drawText("te");
    }
  }

  public void setCurrentCell(Cell newCell) {
    // Clear old cell
    if (this.currentCell != null && this.equals(this.currentCell.getBlock())) {
      this.currentCell.setBlock(null);
    }
    this.currentCell = newCell;
    // tell new block about this
    newCell.setBlock(this);
    this.y = newCell.getY();
    this.x = newCell.getX();
  }

  public Cell getCurrentCell() {
    return currentCell;
  }

  public void destroy() {
    busy = false;
    currentCell.setBlock(null);
    currentCell = null;
    drawableRegister.unregister(this);
  }

  public void setFrame(double frame) {
    this.frame = frame;
  }

  public void setDrawable(boolean drawable) {
    this.drawable = drawable;
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }

  public void offset(int x, int y) {
    offsetX += x;
    offsetY += y;
  }

  public boolean isBusy() {
    return busy;
  }

  public void setBusy(boolean busy) {
    this.busy = busy;
  }

  public void removeOffset() {
    offsetX = 0;
    offsetY = 0;
  }

  public boolean sameBlockType(Block block) {
    return block.getShape().equals(shape);
  }

  public int getOffsetX() {
    return offsetX;
  }

  public int getOffsetY() {
    return offsetY;
  }
}
