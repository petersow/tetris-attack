package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.constants.Directions;
import net.stickboyproductions.tetrisattack.enums.BlockState;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.ui.Screen;

/**
 * User: Pete
 * Date: 27/11/12
 * Time: 22:07
 */
public class Block implements Drawable {

  private BlockState blockState = BlockState.EMPTY;
  private Shape shape;
  private int x;
  private int y;
  private Grid grid;

  private int offsetX;
  private int shapeOffsetX = 0;
  private int offsetY;
  private double frame = 0.0;

  public Block(Grid grid, int x, int y) {
    this.grid = grid;
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public BlockState getBlockState() {
    return blockState;
  }

  public void setBlockState(BlockState blockState) {
    this.blockState = blockState;
    if (blockState.equals(BlockState.EMPTY)) {
      shape = null;
    }
  }

  public boolean canSwap() {
    return blockState == BlockState.IDLE || blockState == BlockState.EMPTY;
  }

  public boolean canFall() {
    Block blockBelow = grid.getBlockToTheDirection(this, Directions.DOWN);
    if (blockBelow == null || blockBelow.getY() == 0) {
      return false;
    }
    if ((blockBelow.getBlockState().equals(BlockState.EMPTY) ||
      blockBelow.getBlockState().equals(BlockState.FALLING) ||
      blockBelow.getBlockState().equals(BlockState.PAUSED_BEFORE_FALLING))) {
      return true;
    }
    return false;
  }

  @Override
  public void draw(Screen screen) {
    if ((!BlockState.EMPTY.equals(blockState) && !BlockState.DESTROYING_END.equals(blockState))
      && shape != null) {
      screen.drawShape(x, y, offsetX + shapeOffsetX, offsetY, shape, frame, y == 0);
    }
  }

  public int getOffsetX() {
    return offsetX;
  }

  public int getOffsetY() {
    return offsetY;
  }

  public void setOffsetX(int offsetX) {
    this.offsetX = offsetX;
  }

  public void setOffsetY(int offsetY) {
    this.offsetY = offsetY;
  }

  public int getShapeOffsetX() {
    return shapeOffsetX;
  }

  public void incrementShapeOffsetX(int incr) {
    shapeOffsetX = shapeOffsetX + incr;
  }

  public void resetShapeOffsetX() {
    shapeOffsetX = 0;
  }

  public Shape getShape() {
    return shape;
  }

  public void setShape(Shape shape) {
    this.shape = shape;
  }

  public double getFrame() {
    return frame;
  }

  public void setFrame(double frame) {
    this.frame = frame;
  }

  public int getDistance(Block block) {
    int xDistance = block.getX() - x;
    if (xDistance < 0) {
      xDistance = xDistance * -1;
    }

    int yDistance = block.getY() - y;
    if (yDistance < 0) {
      yDistance = yDistance * -1;
    }

    return xDistance + yDistance;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void init(Shape shape) {
    this.shape = shape;
    setBlockState(BlockState.IDLE);
  }
}
