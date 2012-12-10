package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Cell;
import net.stickboyproductions.tetrisattack.model.Grid;
import net.stickboyproductions.tetrisattack.model.Shape;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * User: Pete
 * Date: 19/11/12
 * Time: 21:18
 */
public class BlockSwapTest {

  @Mock
  private DrawableRegister drawableRegister;
  @Mock
  private Grid grid;

  private Cell leftCell = new Cell(0, 0);
  private Cell rightCell = new Cell(1, 0);

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    when(grid.getCellToTheLeft(rightCell)).thenReturn(leftCell);
    when(grid.getCellToTheRight(leftCell)).thenReturn(rightCell);
  }

  @Test
  public void testBlockSwap() throws Exception {
    Block leftBlock = new Block(Shape.BLUE, leftCell, drawableRegister);
    Block rightBlock = new Block(Shape.CYAN, rightCell, drawableRegister);

    BlockSwap blockSwap = new BlockSwap(leftBlock, rightBlock, grid);
    blockSwap.start();
    blockSwap.tick(10);
    blockSwap.end();

    assertEquals(rightCell, leftBlock.getCurrentCell());
    assertEquals(leftCell, rightBlock.getCurrentCell());
    assertEquals(0, leftBlock.getOffsetX());
    assertEquals(0, leftBlock.getOffsetY());
    assertFalse(leftBlock.isBusy());
    assertEquals(0, rightBlock.getOffsetX());
    assertEquals(0, rightBlock.getOffsetY());
    assertFalse(rightBlock.isBusy());

    assertNotNull(leftCell.getBlock());
    assertNotNull(rightCell.getBlock());
  }

  @Test
  public void testLeftBlockSwap() throws Exception {
    Block leftBlock = new Block(Shape.BLUE, leftCell, drawableRegister);
    Block rightBlock = null;

    BlockSwap blockSwap = new BlockSwap(leftBlock, rightBlock, grid);
    blockSwap.start();
    blockSwap.tick(10);
    blockSwap.end();

    assertEquals(rightCell, leftBlock.getCurrentCell());
    assertEquals(leftCell.getBlock(), null);
    assertEquals(0, leftBlock.getOffsetX());
    assertEquals(0, leftBlock.getOffsetY());
    assertFalse(leftBlock.isBusy());
  }

  @Test
  public void testRightBlockSwap() throws Exception {
    Block rightBlock = new Block(Shape.CYAN, rightCell, drawableRegister);
    Block leftBlock = null;

    BlockSwap blockSwap = new BlockSwap(leftBlock, rightBlock, grid);
    blockSwap.start();
    blockSwap.tick(10);
    blockSwap.end();

    assertEquals(leftCell, rightBlock.getCurrentCell());
    assertEquals(rightCell.getBlock(), null);
    assertEquals(0, rightBlock.getOffsetX());
    assertEquals(0, rightBlock.getOffsetY());
    assertFalse(rightBlock.isBusy());
  }
}
