package net.stickboyproductions.tetrisattack.processors;

import net.stickboyproductions.tetrisattack.Clock;
import net.stickboyproductions.tetrisattack.generators.ShapeGenerator;
import net.stickboyproductions.tetrisattack.generators.StartGridGenerator;
import net.stickboyproductions.tetrisattack.io.InputNotifier;
import net.stickboyproductions.tetrisattack.model.*;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * User: Pete
 * Date: 20/11/12
 * Time: 22:18
 */
public class ChainBuilderProcessTest {

  @Mock
  private Clock clock;
  @Mock
  private ShapeGenerator mockShapeGenerator;
  @Mock
  private StartGridGenerator mockStartGridGenerator;
  @Mock
  private DrawableRegister drawableRegister;
  @Mock
  private InputNotifier inputNotifier;

  private ChainBuilderProcess subject;

  Grid grid = new Grid(clock, mockShapeGenerator, mockStartGridGenerator, drawableRegister, inputNotifier);
  Cell startCell;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    grid.fillGrid();
    this.subject = new ChainBuilderProcess();
    this.startCell = grid.getRows().get(0).get(0);
  }

  @Test
  public void testEmptyGrid() throws Exception {
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(0, blockChain.size());
  }

  @Test
  public void testOneCell() throws Exception {
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(1, blockChain.size());
  }

  @Test
  public void testTwoCellLeft() throws Exception {
    startCell = grid.getRows().get(0).get(1);
    new Block(Shape.GREEN, grid.getRows().get(0).get(0), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(2, blockChain.size());
  }

  @Test
  public void testTwoCellLeftDifferent() throws Exception {
    startCell = grid.getRows().get(0).get(1);
    new Block(Shape.GREEN, grid.getRows().get(0).get(0), drawableRegister);
    new Block(Shape.YELLOW, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(1, blockChain.size());
  }

  @Test
  public void testThreeCellLeft() throws Exception {
    startCell = grid.getRows().get(0).get(2);
    new Block(Shape.GREEN, grid.getRows().get(0).get(0), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(0).get(1), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(3, blockChain.size());
  }

  @Test
  public void testThreeCellLeftDifferent() throws Exception {
    startCell = grid.getRows().get(0).get(2);
    new Block(Shape.GREEN, grid.getRows().get(0).get(0), drawableRegister);
    new Block(Shape.RED, grid.getRows().get(0).get(1), drawableRegister);
    new Block(Shape.YELLOW, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(1, blockChain.size());
  }

  @Test
  public void testOneCellRight() throws Exception {
    startCell = grid.getRows().get(0).get(5);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(1, blockChain.size());
  }

  @Test
  public void testTwoCellRight() throws Exception {
    startCell = grid.getRows().get(0).get(4);
    new Block(Shape.GREEN, grid.getRows().get(0).get(5), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(2, blockChain.size());
  }

  @Test
  public void testThreeCellRight() throws Exception {
    startCell = grid.getRows().get(0).get(3);
    new Block(Shape.GREEN, grid.getRows().get(0).get(5), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(0).get(4), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(3, blockChain.size());
  }

  @Test
  public void testTwoCellRightAndTwoCellLeft() throws Exception {
    startCell = grid.getRows().get(0).get(2);
    new Block(Shape.GREEN, grid.getRows().get(0).get(0), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(0).get(1), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(0).get(3), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(0).get(4), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(5, blockChain.size());
  }

  @Test
  public void testOneCellTop() throws Exception {
    startCell = grid.getRows().get(11).get(0);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(1, blockChain.size());
  }

  @Test
  public void testTwoCellTop() throws Exception {
    startCell = grid.getRows().get(10).get(0);
    new Block(Shape.GREEN, grid.getRows().get(11).get(0), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(2, blockChain.size());
  }

  @Test
  public void testThreeCellTop() throws Exception {
    startCell = grid.getRows().get(9).get(0);
    new Block(Shape.GREEN, grid.getRows().get(11).get(0), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(10).get(0), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(3, blockChain.size());
  }

  @Test
  public void testThreeCellDifferentColourTop() throws Exception {
    startCell = grid.getRows().get(9).get(0);
    new Block(Shape.GREEN, grid.getRows().get(11).get(0), drawableRegister);
    new Block(Shape.RED, grid.getRows().get(10).get(0), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(1, blockChain.size());
  }

  @Test
  public void testTwoCellAboveAndTwoCellBelow() throws Exception {
    startCell = grid.getRows().get(9).get(0);
    new Block(Shape.GREEN, grid.getRows().get(10).get(0), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(11).get(0), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(8).get(0), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(7).get(0), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(5, blockChain.size());
  }

  @Test
  public void testTwoCellLeftAndTwoCellBelow() throws Exception {
    startCell = grid.getRows().get(9).get(0);
    new Block(Shape.GREEN, grid.getRows().get(9).get(1), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(9).get(2), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(8).get(0), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(7).get(0), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(5, blockChain.size());
  }

  @Test
  public void testCross() throws Exception {
    startCell = grid.getRows().get(9).get(2);
    new Block(Shape.GREEN, grid.getRows().get(9).get(1), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(9).get(3), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(8).get(2), drawableRegister);
    new Block(Shape.GREEN, grid.getRows().get(10).get(2), drawableRegister);
    new Block(Shape.GREEN, startCell, drawableRegister);
    BlockChain blockChain = subject.buildChain(startCell, grid);
    assertEquals(5, blockChain.size());
  }
}
