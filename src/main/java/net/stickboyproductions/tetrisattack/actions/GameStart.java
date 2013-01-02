package net.stickboyproductions.tetrisattack.actions;

import net.stickboyproductions.tetrisattack.constants.ScreenConfig;
import net.stickboyproductions.tetrisattack.interfaces.Drawable;
import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Game;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import net.stickboyproductions.tetrisattack.ui.Screen;

/**
 * User: Pete
 * Date: 17/12/12
 * Time: 22:37
 */
public class GameStart implements TimeTickingAction, Drawable {

  private static final int COUNTDOWN_X = 180;
  private static final int COUNTDOWN_Y = 300;

  private long timeElapsed;
  private Game game;
  private DrawableRegister drawableRegister;

  public GameStart(Game game, DrawableRegister drawableRegister) {
    this.game = game;
    this.drawableRegister = drawableRegister;
    drawableRegister.register(this);
  }

  @Override
  public void tick(long timeElapsed) {
    this.timeElapsed = timeElapsed;
  }

  @Override
  public boolean isFinished() {
    return timeElapsed >= 3000;
  }

  @Override
  public void start() {
  }

  @Override
  public void end() {
    game.startGame();
    drawableRegister.unregister(this);
  }

  @Override
  public void draw(Screen screen) {
    String s = "";
    if (timeElapsed > 2000) {
      s = "1";
    } else if (timeElapsed > 1000) {
      s = "2";
    } else {
      screen.drawText(COUNTDOWN_X - 40, COUNTDOWN_Y - ScreenConfig.SPACER, "Ready");
      s = "3";
    }
    screen.drawText(COUNTDOWN_X, COUNTDOWN_Y, s);
  }
}
