package net.stickboyproductions.tetrisattack.model;

import net.stickboyproductions.tetrisattack.Clock;
import net.stickboyproductions.tetrisattack.generators.ShapeGenerator;
import net.stickboyproductions.tetrisattack.generators.StartGridGenerator;
import net.stickboyproductions.tetrisattack.io.InputNotifier;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * User: Pete
 * Date: 26/11/12
 * Time: 21:15
 */
public class ColumnTest {

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

  private Column subject;

  Grid grid = new Grid(clock, mockShapeGenerator, mockStartGridGenerator, drawableRegister, inputNotifier);

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    grid.fillGrid();
    this.subject = grid.getColumns().get(0);
    new Block(Shape.RED, subject.get(0), drawableRegister);
  }

  @Test
  public void testFilledCell() throws Exception {
    Assert.assertFalse(subject.fallIfCellBelowIsEmpty(1));
  }

  @Test
  public void testUnFilledCell() throws Exception {
    Assert.assertFalse(subject.fallIfCellBelowIsEmpty(3));
  }
}
