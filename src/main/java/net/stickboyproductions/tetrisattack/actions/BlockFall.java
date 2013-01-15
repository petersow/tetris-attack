package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.constants.Directions;
import net.stickboyproductions.tetrisattack.enums.BlockState;
import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Game;
import net.stickboyproductions.tetrisattack.model.Grid;

import static net.stickboyproductions.tetrisattack.constants.GameConfig.DEBUG;
import static net.stickboyproductions.tetrisattack.constants.SpeedConstants.FALL_BLOCK_MS;
import static net.stickboyproductions.tetrisattack.constants.SpeedConstants.FALL_PAUSE_MS;

/**
 * User: Pete
 * Date: 30/10/12
 * Time: 23:19
 */
public class BlockFall implements TimeTickingAction {

  private Block block;
  private Grid grid;
  private int nextFall = FALL_PAUSE_MS;
  private boolean finished;
  private Game game;
  private int chainNum;

  public BlockFall(Block block, Game game, int chainNum) {
    this.game = game;
    this.chainNum = chainNum;
    if (DEBUG) {
      System.out.println("Making a Block Fall containing");
      System.out.println("x=" + block.getX() + ", y=" + block.getY());
    }
    this.block = block;
    this.grid = game.getGrid();
  }

  @Override
  public void tick(long timeElapsed) {
    if (timeElapsed >= nextFall) {
      block.setBlockState(BlockState.FALLING);
      Block blockBelow = grid.getBlockToTheDirection(block, Directions.DOWN);
      if (!blockBelow.getBlockState().equals(BlockState.PAUSED_BEFORE_FALLING)) {
        blockBelow.setShape(block.getShape());

        block.setBlockState(BlockState.EMPTY);
        block = blockBelow;
        if (blockBelow.canFall()) {
          blockBelow.setBlockState(BlockState.FALLING);
        } else {
          finished = true;
          blockBelow.setBlockState(BlockState.RESERVED);
        }
        nextFall = nextFall + FALL_BLOCK_MS;
      } else {
        block.setBlockState(BlockState.PAUSED_BEFORE_FALLING);
      }
    }
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  @Override
  public void start() {
    block.setBlockState(BlockState.PAUSED_BEFORE_FALLING);
    // Reserve the block below
    grid.getBlockToTheDirection(block, Directions.DOWN).setBlockState(BlockState.RESERVED);
  }

  @Override
  public void end() {
    block.setBlockState(BlockState.IDLE);
    game.setChainFinished(chainNum);
  }
}
