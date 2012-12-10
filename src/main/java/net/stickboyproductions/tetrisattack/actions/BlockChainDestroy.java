package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.BlockChain;
import net.stickboyproductions.tetrisattack.model.Grid;

/**
 * User: Pete
 * Date: 29/10/12
 * Time: 21:35
 */
public class BlockChainDestroy implements TimeTickingAction {

  private BlockChain chain;
  private Grid grid;

  private long startMilli = 100;
  private long endMilli = 200;
  private int cellToSkip = 0;
  private long finalPhaseStep = 1100;

  private boolean finished = false;

  public BlockChainDestroy(BlockChain chain, Grid grid) {
    this.chain = chain;
    this.grid = grid;
  }

  @Override
  public void tick(long timeElapsed) {
    for (int i = 0; i < chain.getClearableChain().size(); i++) {
      Block block = chain.getClearableChain().get(i);
      // Flash for the first second
      if (timeElapsed <= 1000) {
        if (timeElapsed > startMilli && timeElapsed < endMilli) {
          block.setFrame(0.25);
        } else {
          block.setFrame(0.0);
        }

        if (timeElapsed >= endMilli) {
          startMilli = timeElapsed + 100;
          endMilli = startMilli + 100;
        }
      } else {
        // Then slowly delete the cells every 0.1 of a second
        if (i >= cellToSkip) {
          block.setFrame(0.5);
        } else {
          block.setDrawable(false);
        }

        if (timeElapsed >= finalPhaseStep) {
          cellToSkip++;
          finalPhaseStep = timeElapsed + 100;
          if (cellToSkip >= chain.size()) {
            finished = true;
          }
        }
      }
    }
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  @Override
  public void start() {
    for (Block block : chain.getClearableChain()) {
      block.setBusy(true);
    }
  }

  @Override
  public void end() {
    grid.getScore().addToScore(chain.getScoreValue());
    grid.fillEmptyCells(chain);
    for (Block block : chain.getClearableChain()) {
      System.out.println(this + " - " + block);
      block.destroy();
    }
  }
}
