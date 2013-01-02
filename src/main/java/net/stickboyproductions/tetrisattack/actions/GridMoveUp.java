package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Game;
import net.stickboyproductions.tetrisattack.model.PlayerSelection;

import static net.stickboyproductions.tetrisattack.constants.GameConfig.BLOCKS_IN_ROW_COUNT;
import static net.stickboyproductions.tetrisattack.constants.GameConfig.ROWS_IN_GRID;
import static net.stickboyproductions.tetrisattack.constants.ScreenConfig.CELL_HEIGHT;
import static net.stickboyproductions.tetrisattack.constants.SpeedConstants.GRID_MOVE_UP_OFFSET_MS;

/**
 * User: Pete
 * Date: 30/10/12
 * Time: 21:14
 */
public class GridMoveUp implements TimeTickingAction {
  //
  private final static int OFFSET_STEP = CELL_HEIGHT / 15;
  private int totalOffset;
  private PlayerSelection playerSelection;
  private long timeElapsed;
  private long timeToNextFire;

  private long nextFire = GRID_MOVE_UP_OFFSET_MS;
  private Game game;

  private boolean spedUp = false;

  public GridMoveUp(Game game, PlayerSelection playerSelection) {
    this.game = game;
    this.playerSelection = playerSelection;
  }

  @Override
  public void tick(long timeElapsed) {
    this.timeElapsed = timeElapsed;
    if (timeElapsed >= nextFire) {
      for (int y = 0; y < ROWS_IN_GRID; y++) {
        for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
          Block block = game.getGrid().get(x, y);
          block.setOffsetY(block.getOffsetY() + OFFSET_STEP);
        }
      }
      playerSelection.incrementOffsetY(OFFSET_STEP);
      if(!spedUp) {
        nextFire += GRID_MOVE_UP_OFFSET_MS;
      } else {
        nextFire += 1;
      }

      totalOffset += OFFSET_STEP;
      if (totalOffset >= CELL_HEIGHT) {
        game.moveUp();
        totalOffset = 0;
        spedUp = false;
      }
    }
  }

  @Override
  public boolean isFinished() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void start() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void end() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void reset() {
    totalOffset = 0;
  }

  public void speedUp() {
    spedUp = true;
    nextFire = timeElapsed + 1;
  }
}
