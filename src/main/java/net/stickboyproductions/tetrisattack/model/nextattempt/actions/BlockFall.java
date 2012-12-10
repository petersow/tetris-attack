package net.stickboyproductions.tetrisattack.model.nextattempt.actions;

import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.nextattempt.NewBlock;
import net.stickboyproductions.tetrisattack.model.nextattempt.NewGrid;
import net.stickboyproductions.tetrisattack.model.nextattempt.enums.BlockState;
import net.stickboyproductions.tetrisattack.model.nextattempt.enums.Direction;

import static net.stickboyproductions.tetrisattack.config.GameConfig.DEBUG;
import static net.stickboyproductions.tetrisattack.model.nextattempt.constants.SpeedConstants.FALL_BLOCK_MS;
import static net.stickboyproductions.tetrisattack.model.nextattempt.constants.SpeedConstants.FALL_PAUSE_MS;

/**
 * User: Pete
 * Date: 30/10/12
 * Time: 23:19
 */
public class BlockFall implements TimeTickingAction {

  private NewBlock block;
  private NewGrid grid;
  private int nextFall = FALL_PAUSE_MS;
  private boolean finished;

  public BlockFall(NewBlock block, NewGrid grid) {
    if (DEBUG) {
      System.out.println("Making a Block Fall containing");

      System.out.println("x=" + block.getX() + ", y=" + block.getY());
    }
    this.block = block;
    this.grid = grid;
  }

  @Override
  public void tick(long timeElapsed) {
    if (timeElapsed >= nextFall) {
      System.out.println("falling at " + timeElapsed);
      block.setBlockState(BlockState.FALLING);
      NewBlock blockBelow = grid.getBlockToTheDirection(block, Direction.DOWN);
      System.out.println(blockBelow.getOffsetY());
      if (blockBelow.getOffsetY() == 0 && !blockBelow.getBlockState().equals(BlockState.PAUSED_BEFORE_FALLING)) {
        blockBelow.setShape(block.getShape());

        if (blockBelow.canFall()) {
          System.out.println("Moving - " + block.getX() + ", " + block.getY());
          System.out.println(blockBelow.getBlockState());
          blockBelow.setBlockState(BlockState.FALLING);
        } else {
          finished = true;
          System.out.println("Stopping - " + block.getX() + ", " + block.getY());
          blockBelow.setBlockState(BlockState.IDLE);
        }
        block.setBlockState(BlockState.EMPTY);
        block = blockBelow;
      }
      nextFall = nextFall + FALL_BLOCK_MS;
    }
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  @Override
  public void start() {
    block.setBlockState(BlockState.PAUSED_BEFORE_FALLING);
  }

  @Override
  public void end() {
  }
}
