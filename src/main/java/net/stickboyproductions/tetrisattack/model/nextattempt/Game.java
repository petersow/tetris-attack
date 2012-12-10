package net.stickboyproductions.tetrisattack.model.nextattempt;

import com.google.common.collect.Lists;
import net.stickboyproductions.tetrisattack.Clock;
import net.stickboyproductions.tetrisattack.config.GameConfig;
import net.stickboyproductions.tetrisattack.io.InputNotifier;
import net.stickboyproductions.tetrisattack.model.AbstractControllable;
import net.stickboyproductions.tetrisattack.model.nextattempt.actions.BlockDestroy;
import net.stickboyproductions.tetrisattack.model.nextattempt.actions.BlockFall;
import net.stickboyproductions.tetrisattack.model.nextattempt.actions.ShapeSwap;
import net.stickboyproductions.tetrisattack.model.nextattempt.enums.BlockState;
import net.stickboyproductions.tetrisattack.model.nextattempt.generators.StartGridGenerator;
import net.stickboyproductions.tetrisattack.model.nextattempt.processors.ChainBuilderProcess;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static net.stickboyproductions.tetrisattack.config.GameConfig.BLOCKS_IN_ROW_COUNT;
import static net.stickboyproductions.tetrisattack.config.GameConfig.ROWS_IN_GRID;

/**
 * User: Pete
 * Date: 27/11/12
 * Time: 22:13
 */
public class Game extends AbstractControllable {

  private DrawableRegister drawableRegister;
  private NewGrid grid;
  private ChainBuilderProcess chainBuilderProcess;
  private StartGridGenerator startGridGenerator;
  private InputNotifier inputNotifier;
  private Clock clock;

  private PlayerSelection playerSelection;

  // TODO : wrap in model?

  @Inject
  public Game(DrawableRegister drawableRegister, ChainBuilderProcess chainBuilderProcess,
              StartGridGenerator startGridGenerator, InputNotifier inputNotifier, Clock clock, NewGrid grid) {
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
        NewBlock currentBlock = grid.get(x, y);
        if (currentBlock.getBlockState().equals(BlockState.IDLE)) {
          if (currentBlock.canFall()) {
            System.out.println("I can fall! " + currentBlock.getX() + ", " + currentBlock.getY());
            BlockFall newBlockFall = new BlockFall(currentBlock, grid);
            clock.register(newBlockFall);
          } else {
            if (y > 0) {
              // Build chain
              Set<NewBlock> chain = chainBuilderProcess.buildClearableChain(currentBlock, grid);
              if (chain.size() >= 3) {
                System.out.println("Found a chain - " + chain.size() + " " + chain.iterator().next().getShape());
                List<BlockDestroy> blockDestroyGroup = Lists.newArrayList();
                for (NewBlock next : chain) {
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
    NewBlock leftBlock = grid.get(playerSelection.getLeftX(), playerSelection.getY());
    NewBlock rightBlock = grid.get(playerSelection.getRightX(), playerSelection.getY());
    if (leftBlock != null && rightBlock != null && leftBlock.canSwap() && rightBlock.canSwap()) {
      ShapeSwap shapeSwap = new ShapeSwap(leftBlock, rightBlock);
      clock.register(shapeSwap);
    }
  }
}
