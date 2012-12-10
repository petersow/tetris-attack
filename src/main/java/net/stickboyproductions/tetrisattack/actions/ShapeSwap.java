package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Shape;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.enums.BlockState;

import static net.stickboyproductions.tetrisattack.constants.ScreenConfig.CELL_WIDTH;
import static net.stickboyproductions.tetrisattack.constants.SpeedConstants.SHAPE_SWAP_MS;
import static net.stickboyproductions.tetrisattack.constants.SpeedConstants.SHAPE_SWAP_STEP_MS;

/**
 * User: Pete
 * Date: 30/10/12
 * Time: 21:14
 */
public class ShapeSwap implements TimeTickingAction {

  private final static int OFFSET_STEP = CELL_WIDTH / 3;

  private final Block leftBlock;
  private final Block rightBlock;
  private int nextStep = SHAPE_SWAP_STEP_MS;

  public ShapeSwap(Block leftBlock, Block rightBlock) {
    this.leftBlock = leftBlock;
    this.rightBlock = rightBlock;
  }

  @Override
  public void tick(long timeElapsed) {
    if (timeElapsed >= nextStep) {
      leftBlock.incrementShapeOffsetX(OFFSET_STEP);
      rightBlock.incrementShapeOffsetX(-OFFSET_STEP);
      nextStep = nextStep + SHAPE_SWAP_STEP_MS;
    }
  }

  @Override
  public boolean isFinished() {
    return nextStep > SHAPE_SWAP_MS;
  }

  @Override
  public void start() {
    leftBlock.setBlockState(BlockState.SWAPPING);
    rightBlock.setBlockState(BlockState.SWAPPING);
  }

  @Override
  public void end() {
    Shape leftShape = leftBlock.getShape();
    Shape rightShape = rightBlock.getShape();
    rightBlock.resetShapeOffsetX();
    leftBlock.resetShapeOffsetX();
    if (leftShape != null) {
      rightBlock.setBlockState(BlockState.IDLE);
      rightBlock.setShape(leftShape);
    } else {
      rightBlock.setBlockState(BlockState.EMPTY);
      rightBlock.setShape(null);
    }
    if (rightShape != null) {
      leftBlock.setBlockState(BlockState.IDLE);
      leftBlock.setShape(rightShape);
    } else {
      leftBlock.setBlockState(BlockState.EMPTY);
      leftBlock.setShape(null);
    }
  }
}
