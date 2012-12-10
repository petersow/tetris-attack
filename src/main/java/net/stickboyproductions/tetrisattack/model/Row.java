package net.stickboyproductions.tetrisattack.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A line of Cells
 * <p/>
 * User: Pete
 * Date: 27/10/12
 * Time: 17:46
 */
public class Row extends ArrayList<Cell> {
  private int y;
  private Grid grid;

  public Row(int y, Grid grid) {
    this.y = y;
    this.grid = grid;
  }

  public void offset(int xOffset, int yOffset) {
    Iterator<Cell> iterator = iterator();
    while (iterator.hasNext()) {
      iterator.next().offset(xOffset, yOffset);
    }
  }
}
