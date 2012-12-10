package net.stickboyproductions.tetrisattack.generators;

import com.google.common.collect.ImmutableSet;
import net.stickboyproductions.tetrisattack.Clock;
import net.stickboyproductions.tetrisattack.io.InputNotifier;
import net.stickboyproductions.tetrisattack.model.Block;
import net.stickboyproductions.tetrisattack.model.Cell;
import net.stickboyproductions.tetrisattack.model.Grid;
import net.stickboyproductions.tetrisattack.model.Shape;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * User: Pete
 * Date: 19/11/12
 * Time: 22:32
 */
public class StartGridGeneratorTest {

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

  private StartGridGenerator subject;

  Grid grid = new Grid(clock, mockShapeGenerator, mockStartGridGenerator, drawableRegister, inputNotifier);

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    grid.fillGrid();
    this.subject = new StartGridGenerator(drawableRegister, mockShapeGenerator);
  }

  @Test
  public void testFirstCell() throws Exception {
    subject.fillCell(grid, grid.getRows().get(0).get(0));
  }

  @Test
  public void testNextCell() throws Exception {
    Cell bottomLeftCell = grid.getRows().get(0).get(0);
    new Block(Shape.BLUE, bottomLeftCell, drawableRegister);
    subject.fillCell(grid, grid.getRows().get(0).get(1));
  }

  @Test
  public void testLastCell() throws Exception {
    subject.fillCell(grid, grid.getRows().get(0).get(5));
  }

  @Test
  public void testThirdCellIsntAMatch() throws Exception {
    Cell bottomLeftCell = grid.getRows().get(0).get(0);
    new Block(Shape.BLUE, bottomLeftCell, drawableRegister);
    Cell nextLeftCell = grid.getRows().get(0).get(1);
    new Block(Shape.BLUE, nextLeftCell, drawableRegister);

    subject.fillCell(grid, grid.getRows().get(0).get(2));

    verify(mockShapeGenerator, times(1)).get(ImmutableSet.of(Shape.BLUE));
  }

  @Test
  public void testThirdCellIsntAMatchTwice() throws Exception {
    Cell bottomLeftCell = grid.getRows().get(0).get(0);
    new Block(Shape.BLUE, bottomLeftCell, drawableRegister);
    Cell nextLeftCell = grid.getRows().get(0).get(1);
    new Block(Shape.BLUE, nextLeftCell, drawableRegister);

    Cell rightCell = grid.getRows().get(0).get(3);
    new Block(Shape.RED, rightCell, drawableRegister);
    Cell nextRightCell = grid.getRows().get(0).get(4);
    new Block(Shape.RED, nextRightCell, drawableRegister);

    subject.fillCell(grid, grid.getRows().get(0).get(2));
    verify(mockShapeGenerator, times(1)).get(ImmutableSet.of(Shape.BLUE, Shape.RED));
  }

  @Test
  public void testThirdCellInAColumn() throws Exception {
    Cell bottomCell = grid.getRows().get(1).get(0);
    new Block(Shape.BLUE, bottomCell, drawableRegister);
    Cell nextUpCell = grid.getRows().get(2).get(0);
    new Block(Shape.BLUE, nextUpCell, drawableRegister);

    subject.fillCell(grid, grid.getRows().get(3).get(0));

    verify(mockShapeGenerator, times(1)).get(ImmutableSet.of(Shape.BLUE));
  }

  @Test
  public void testThirdCellInAColumnTwice() throws Exception {
    Cell bottomCell = grid.getRows().get(1).get(0);
    new Block(Shape.BLUE, bottomCell, drawableRegister);
    Cell nextUpCell = grid.getRows().get(2).get(0);
    new Block(Shape.BLUE, nextUpCell, drawableRegister);
    Cell topCell = grid.getRows().get(4).get(0);
    new Block(Shape.RED, topCell, drawableRegister);
    Cell nextTopCell = grid.getRows().get(5).get(0);
    new Block(Shape.RED, nextTopCell, drawableRegister);

    subject.fillCell(grid, grid.getRows().get(3).get(0));

    verify(mockShapeGenerator, times(1)).get(ImmutableSet.of(Shape.BLUE, Shape.RED));
  }

  @Test
  public void testMiddleCell() throws Exception {
    Cell leftCell = grid.getRows().get(0).get(0);
    new Block(Shape.BLUE, leftCell, drawableRegister);
    Cell rightCell = grid.getRows().get(0).get(2);
    new Block(Shape.BLUE, rightCell, drawableRegister);

    subject.fillCell(grid, grid.getRows().get(0).get(1));

    verify(mockShapeGenerator, times(1)).get(ImmutableSet.of(Shape.BLUE));
  }
}
