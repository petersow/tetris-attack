package net.stickboyproductions.tetrisattack;

import net.stickboyproductions.tetrisattack.io.InputController;
import net.stickboyproductions.tetrisattack.model.Game;
import net.stickboyproductions.tetrisattack.model.Grid;
import net.stickboyproductions.tetrisattack.timing.SystemClock;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import net.stickboyproductions.tetrisattack.ui.Screen;

import javax.inject.Inject;


/**
 * User: Pete
 * Date: 27/10/12
 * Time: 16:48
 */
public class Runner {

  private InputController inputController;
  private Screen screen;
  private SystemClock clock;
  private DrawableRegister drawableRegister;

  private Grid grid;
  private Game game;

  @Inject
  public Runner(InputController inputController, Screen screen,
                SystemClock clock, DrawableRegister drawableRegister,
                Grid grid, Game game) {
    this.inputController = inputController;
    this.screen = screen;
    this.clock = clock;
    this.drawableRegister = drawableRegister;
    this.grid = grid;
    this.game = game;
  }

  public void init() throws Exception {
    screen.create();
//    grid.init();
    game.init();

    clock.start();
  }

  public void run() throws Exception {
    while (!screen.isCloseRequested()) {
      // Clear the screen before we redraw things
      screen.clear();
      // Check any user input
      inputController.update();

      clock.tick();
      game.update();

      // Finally redraw the screen
      drawableRegister.draw();
      screen.update();
    }
    screen.destroy();
  }


}
