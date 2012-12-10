package net.stickboyproductions.tetrisattack.model;

import com.google.common.collect.Lists;
import net.stickboyproductions.tetrisattack.Clock;
import net.stickboyproductions.tetrisattack.actions.BlockChainDestroy;
import net.stickboyproductions.tetrisattack.actions.BlockSwap;
import net.stickboyproductions.tetrisattack.actions.GridMoveUp;
import net.stickboyproductions.tetrisattack.generators.ShapeGenerator;
import net.stickboyproductions.tetrisattack.generators.StartGridGenerator;
import net.stickboyproductions.tetrisattack.io.InputNotifier;
import net.stickboyproductions.tetrisattack.processors.ChainBuilderProcess;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static net.stickboyproductions.tetrisattack.config.GameConfig.BLOCKS_IN_ROW_COUNT;
import static net.stickboyproductions.tetrisattack.config.GameConfig.ROWS_IN_GRID;

/**
 * The container of Rows of Cells
 * <p/>
 * User: Pete
 * Date: 27/10/12
 * Time: 17:51
 */
@Singleton
public class Grid extends AbstractControllable {

  public static final int LEFT = 1;
  public static final int RIGHT = 2;
  public static final int UP = 3;
  public static final int DOWN = 4;

  private final List<Row> rows = Lists.newArrayListWithExpectedSize(ROWS_IN_GRID);
  private final List<Column> columns = Lists.newArrayListWithExpectedSize(BLOCKS_IN_ROW_COUNT);
  private final List<Cell> cells = Lists.newArrayList();
  private PlayerSelection playerSelection;
  private Clock clock;
  private ShapeGenerator shapeGenerator;
  private StartGridGenerator startGridGenerator;
  private DrawableRegister drawableRegister;
  private InputNotifier inputNotifier;

  private ChainBuilderProcess chainBuilderProcess = new ChainBuilderProcess();

  private GridMoveUp gridMoveUp;

  private Score score;

  @Inject
  public Grid(Clock clock, ShapeGenerator shapeGenerator,
              StartGridGenerator startGridGenerator,
              DrawableRegister drawableRegister, InputNotifier inputNotifier) {
    this.clock = clock;
    this.shapeGenerator = shapeGenerator;
    this.startGridGenerator = startGridGenerator;
    this.drawableRegister = drawableRegister;
    this.inputNotifier = inputNotifier;
    score = new Score(drawableRegister);
  }

  public List<Row> getRows() {
    return rows;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public void offset(int xOffset, int yOffset) {
    for (Row row : rows) {
      row.offset(xOffset, yOffset);
    }
  }

  public PlayerSelection getPlayerSelection() {
    return playerSelection;
  }

  @Override
  public void actionPressed() {
    Row row = rows.get(playerSelection.getY());
    Block leftBlock = row.get(playerSelection.getLeftX()).getBlock();
    if (leftBlock != null && leftBlock.isBusy()) {
      return;
    }
    Block rightBlock = row.get(playerSelection.getLeftX() + 1).getBlock();
    if (rightBlock != null && rightBlock.isBusy()) {
      return;
    }
    if ((rightBlock != null || leftBlock != null)) {
      BlockSwap blockSwap = new BlockSwap(leftBlock, rightBlock, this);
      clock.register(blockSwap);
    }
  }

  public void checkAndPerformChainClear(int x, int y) {
    BlockChain chain = chainBuilderProcess.buildChain(columns.get(x).get(y), this);
    if (chain.getClearableChain().size() >= 3) {
      BlockChainDestroy cellChainDestroy = new BlockChainDestroy(chain, this);
      clock.register(cellChainDestroy);
      cellChainDestroy.start();
    }
  }

  public void fillEmptyCells(BlockChain chain) {
    if (chain.getXChain().size() >= 3) {
      for (Block block : chain.getXChain()) {
        columns.get(block.getX()).fallCellsAboveIntoHole(block.getY());
      }
    }

    if (chain.getYChain().size() >= 3) {
      int highest = -1;
      for (Block block : chain.getYChain()) {
        if (block.getY() > highest) {
          highest = block.getY();
        }
      }
      columns.get(chain.getYChain().iterator().next().getX()).fallCellsAboveIntoHole(highest);
    }
  }

  public List<Cell> getCells() {
    return cells;
  }

  public void moveAllUp() {
    for (int i = rows.size() - 1; i >= 0; i--) {
      for (Cell cell : rows.get(i)) {
        if (!(cell.getBlock() == null)) {
          Cell cellAbove = getCellAbove(cell);
          if (cellAbove != null) {
            cell.getBlock().setCurrentCell(cellAbove);
          } else {
            // todo : GAME OVER!
          }
        }
      }
    }

    for (Cell cell : rows.get(1)) {
      checkAndPerformChainClear(cell.getX(), cell.getY());
    }
    playerSelection.moveUpPressed();
  }

  public void generateNewRow() {
    Row row = rows.get(0);
    for (int i = 0; i < row.size(); i++) {
      Block block = new Block(shapeGenerator.get(), row.get(i), drawableRegister);
      block.setCurrentCell(row.get(i));
    }
  }

  @Override
  public void spacePressed() {
    moveAllUp();
    generateNewRow();
  }

  public Cell getCellToTheDirection(Cell cell, int direction) {
    switch (direction) {
      case LEFT:
        return getCellToTheLeft(cell);
      case RIGHT:
        return getCellToTheRight(cell);
      case UP:
        return getCellAbove(cell);
      case DOWN:
        return getCellBelow(cell);
    }
    return null;
  }

  public Cell getCellToTheRight(Cell cell) {
    Row row = rows.get(cell.getY());
    if (cell.getX() + 1 < row.size()) {
      return row.get(cell.getX() + 1);
    }
    return null;
  }

  public Cell getCellToTheLeft(Cell cell) {
    Row row = rows.get(cell.getY());
    if (cell.getX() - 1 >= 0) {
      return row.get(cell.getX() - 1);
    }
    return null;
  }

  public Cell getCellAbove(Cell cell) {
    Column col = columns.get(cell.getX());
    if (cell.getY() + 1 < col.size()) {
      return col.get(cell.getY() + 1);
    }
    return null;
  }

  public Cell getCellBelow(Cell cell) {
    Column col = columns.get(cell.getX());
    if (cell.getY() - 1 > 0) {
      return col.get(cell.getY() - 1);
    }
    return null;
  }

  public void init() {
    inputNotifier.register(this);
    fillGrid();

    this.playerSelection = new PlayerSelection(clock, this,
      rows.get(2).get(2), rows.get(2).get(3), drawableRegister, inputNotifier);

    // TODO : Replace the magic numbers
    offset(64, 10);

    startGridGenerator.generate(this);

    this.gridMoveUp = new GridMoveUp(this);
    clock.register(gridMoveUp);
  }

  public void fillGrid() {
    // Init the data structures
    for (int y = 0; y < ROWS_IN_GRID; y++) {
      rows.add(new Row(y, this));
    }
    for (int x = 0; x < BLOCKS_IN_ROW_COUNT; x++) {
      columns.add(new Column(x, this, clock));
      for (int y = 0; y < ROWS_IN_GRID; y++) {
        Cell cell = new Cell(x, y);
        cells.add(cell);
        columns.get(x).add(cell);
        rows.get(y).add(cell);
      }
    }
  }

  public Score getScore() {
    return score;
  }
}
