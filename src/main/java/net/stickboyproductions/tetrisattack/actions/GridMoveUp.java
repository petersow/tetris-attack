package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Game;
import net.stickboyproductions.tetrisattack.model.PlayerSelection;
import net.stickboyproductions.tetrisattack.model.Speed;

import static net.stickboyproductions.tetrisattack.constants.GameConfig.BLOCKS_IN_ROW_COUNT;
import static net.stickboyproductions.tetrisattack.constants.GameConfig.ROWS_IN_GRID;
import static net.stickboyproductions.tetrisattack.constants.ScreenConfig.CELL_HEIGHT;

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
  private Speed speed;
  private long timeElapsed;

  private long nextFire;
  private Game game;

  private boolean spedUp = false;
  private int cellsPausing = 0;
  private long timeToNextFire;

  public GridMoveUp(Game game, PlayerSelection playerSelection, Speed speed) {
    this.game = game;
    this.playerSelection = playerSelection;
    this.speed = speed;
    nextFire = speed.getNextStepTime();
  }

  @Override
  public void tick(long timeElapsed) {
    this.timeElapsed = timeElapsed;
    while (timeElapsed >= nextFire) {
      for (int y = 0; y < ROWS_IN_GRID; y++) {
        for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
          Block block = game.getGrid().get(x, y);
          block.setOffsetY(block.getOffsetY() + OFFSET_STEP);
        }
      }
      playerSelection.incrementOffsetY(OFFSET_STEP);
      if (!spedUp) {
        nextFire += speed.getNextStepTime();
      } else {
        nextFire = timeElapsed + 1;
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
    return false;
  }

  @Override
  public void start() {
  }

  @Override
  public void end() {
  }

  public void reset() {
    totalOffset = 0;
  }

  public boolean isPaused() {
    return cellsPausing > 0;
  }

  public void speedUp() {
    if(!isPaused()) {
      spedUp = true;
      nextFire = timeElapsed + 1;
    }
  }

  public void pause() {
    if(!isPaused()) {
      timeToNextFire = nextFire - timeElapsed;
      nextFire = Integer.MAX_VALUE;
    }
    cellsPausing++;
  }

  public void resume() {
    if(isPaused()) {
      nextFire = timeElapsed + timeToNextFire;
    }
    cellsPausing--;
  }
}
