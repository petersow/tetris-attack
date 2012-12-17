package net.stickboyproductions.tetrisattack.model;

import com.google.common.collect.Lists;
import net.stickboyproductions.tetrisattack.Clock;
import net.stickboyproductions.tetrisattack.actions.GridMoveUp;
import net.stickboyproductions.tetrisattack.constants.GameConfig;
import net.stickboyproductions.tetrisattack.io.InputNotifier;
import net.stickboyproductions.tetrisattack.actions.BlockDestroy;
import net.stickboyproductions.tetrisattack.actions.BlockFall;
import net.stickboyproductions.tetrisattack.actions.ShapeSwap;
import net.stickboyproductions.tetrisattack.enums.BlockState;
import net.stickboyproductions.tetrisattack.generators.StartGridGenerator;
import net.stickboyproductions.tetrisattack.processors.ChainBuilderProcess;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static net.stickboyproductions.tetrisattack.constants.GameConfig.BLOCKS_IN_ROW_COUNT;
import static net.stickboyproductions.tetrisattack.constants.GameConfig.ROWS_IN_GRID;

/**
 * User: Pete
 * Date: 27/11/12
 * Time: 22:13
 */
public class Game extends AbstractControllable {

  private DrawableRegister drawableRegister;
  private Grid grid;
  private ChainBuilderProcess chainBuilderProcess;
  private StartGridGenerator startGridGenerator;
  private InputNotifier inputNotifier;
  private Clock clock;
  private GridMoveUp gridMoveUp;

  private PlayerSelection playerSelection;

  // TODO : wrap in model?

  @Inject
  public Game(DrawableRegister drawableRegister, ChainBuilderProcess chainBuilderProcess,
              StartGridGenerator startGridGenerator, InputNotifier inputNotifier, Clock clock, Grid grid) {
    this.drawableRegister = drawableRegister;
    this.chainBuilderProcess = chainBuilderProcess;
    this.startGridGenerator = startGridGenerator;
    this.inputNotifier = inputNotifier;
    this.clock = clock;
    this.grid = grid;
  }

  public void init() {
    grid.init();
    for (int y = 0; y < ROWS_IN_GRID; y++) {
      for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
        drawableRegister.register(grid.get(x, y));
      }
    }

    startGridGenerator.generate(grid);

    playerSelection = new PlayerSelection(clock, grid, grid.get(GameConfig.PLAYER_START_X, GameConfig.PLAYER_START_Y),
      drawableRegister, inputNotifier);

    inputNotifier.register(this);

    this.gridMoveUp = new GridMoveUp(this, playerSelection);
    clock.register(gridMoveUp);

    // TODO : Move to some load state ?
    // Test block fall
//    grid.get(0, 0).setBlockState(BlockState.IDLE);
//    grid.get(0, 0).setShape(Shape.RED);
//    grid.get(0, 1).setBlockState(BlockState.IDLE);
//    grid.get(0, 1).setShape(Shape.RED);
//    grid.get(0, 8).setBlockState(BlockState.IDLE);
//    grid.get(0, 8).setShape(Shape.RED);
//    grid.get(0, 7).setBlockState(BlockState.IDLE);
//    grid.get(0, 7).setShape(Shape.CYAN);

    // Test chain clear
//    grid.get(0, 0).setBlockState(BlockState.IDLE);
//    grid.get(0, 0).setShape(Shape.RED);
//    grid.get(1, 0).setBlockState(BlockState.IDLE);
//    grid.get(1, 0).setShape(Shape.RED);
//    grid.get(2, 0).setBlockState(BlockState.IDLE);
//    grid.get(2, 0).setShape(Shape.RED);
//
//    grid.get(0, 1).setBlockState(BlockState.IDLE);
//    grid.get(0, 1).setShape(Shape.BLUE);
//    grid.get(1, 1).setBlockState(BlockState.IDLE);
//    grid.get(1, 1).setShape(Shape.BLUE);
//    grid.get(2, 1).setBlockState(BlockState.IDLE);
//    grid.get(2, 1).setShape(Shape.BLUE);
//
//    grid.get(0, 2).setBlockState(BlockState.IDLE);
//    grid.get(0, 2).setShape(Shape.GREEN);
//    grid.get(0, 3).setBlockState(BlockState.IDLE);
//    grid.get(0, 3).setShape(Shape.GREEN);
//    grid.get(0, 4).setBlockState(BlockState.IDLE);
//    grid.get(0, 4).setShape(Shape.GREEN);
  }

  private int count = 0;

  public void update() {
    count++;

    // Test block fall
//    if(count >= 10) {
//      grid.get(0, 3).setBlockState(BlockState.IDLE);
//      grid.get(0, 3).setShape(Shape.GREEN);
//      count = -100000000;
//    }

    for (int y = 0; y < ROWS_IN_GRID; y++) {
      for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
        Block currentBlock = grid.get(x, y);
        if (currentBlock.getBlockState().equals(BlockState.IDLE)) {
          if (currentBlock.canFall()) {
            System.out.println("I can fall! " + currentBlock.getX() + ", " + currentBlock.getY());
            BlockFall newBlockFall = new BlockFall(currentBlock, grid);
            clock.register(newBlockFall);
          } else {
            if (y > 0) {
              // Build chain
              Set<Block> chain = chainBuilderProcess.buildClearableChain(currentBlock, grid);
              if (chain.size() >= 3) {
                System.out.println("Found a chain - " + chain.size() + " " + chain.iterator().next().getShape());
                List<BlockDestroy> blockDestroyGroup = Lists.newArrayList();
                for (Block next : chain) {
                  BlockDestroy newBlockDestroy = new BlockDestroy(next, currentBlock.getDistance(next));
                  blockDestroyGroup.add(newBlockDestroy);
                }
                clock.register(blockDestroyGroup);
              }
            }
          }
        }
      }
    }
  }

  public DrawableRegister getDrawableRegister() {
    return drawableRegister;
  }

  @Override
  public void actionPressed() {
    Block leftBlock = grid.get(playerSelection.getLeftX(), playerSelection.getY());
    Block rightBlock = grid.get(playerSelection.getRightX(), playerSelection.getY());
    if (leftBlock != null && rightBlock != null && leftBlock.canSwap() && rightBlock.canSwap()) {
      ShapeSwap shapeSwap = new ShapeSwap(leftBlock, rightBlock);
      clock.register(shapeSwap);
    }
  }

  @Override
  public void spacePressed() {
//    moveUp();
    gridMoveUp.speedUp();
  }

  public void moveUp() {
    // Stop old cells from being drawn
    for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
      drawableRegister.unregister(grid.get(x, 11));
    }
    // Modify state
    grid.moveAllUp();
    playerSelection.moveUpPressed();

    // Remove offset caused by slow move up
    for (int y = 0; y < ROWS_IN_GRID; y++) {
      for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
        Block block = grid.get(x, y);
        block.setOffsetY(0);
      }
    }
    playerSelection.resetOffsetY();

    // Reset the counter on the move up action
    gridMoveUp.reset();

    // Make sure all new blocks are drawn
    for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
      drawableRegister.register(grid.get(x, 0));
    }
  }

  public Grid getGrid() {
    return grid;
  }
}
