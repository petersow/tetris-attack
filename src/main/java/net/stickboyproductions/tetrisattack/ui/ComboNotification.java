package net.stickboyproductions.tetrisattack.ui;

import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Block;

/**
 * User: Pete
 * Date: 19/01/13
 * Time: 17:30
 */
// todo : Make a generic Notification superclass
public class ComboNotification implements Drawable, TimeTickingAction {

  private Block source;
  private int comboNum;
  private int offsetX = -14;
  private int offsetY = 22;

  private static final int Y_OFFSET_STEP = 2;
  private static final int TIME_TO_END = 600;
  private boolean finished = false;

  public ComboNotification(Block source, int comboNum) {
    this.source = source;
    this.comboNum = comboNum;
  }

  @Override
  public void draw(Screen screen) {
    if(!finished) {
      screen.drawTextAtBlock(source,
        source.getOffsetX() + offsetX, source.getOffsetY() + offsetY, "" + comboNum);
    }
  }

  @Override
  public void tick(long timeElapsed) {
    offsetY += Y_OFFSET_STEP;
    if(timeElapsed >= TIME_TO_END) {
      finished = true;
    }
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  @Override
  public void start() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void end() {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
