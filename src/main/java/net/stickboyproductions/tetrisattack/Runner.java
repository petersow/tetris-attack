package net.stickboyproductions.tetrisattack;

import net.stickboyproductions.tetrisattack.io.InputNotifier;
import net.stickboyproductions.tetrisattack.model.Game;
import net.stickboyproductions.tetrisattack.model.Grid;
import net.stickboyproductions.tetrisattack.timing.SystemClock;
import net.stickboyproductions.tetrisattack.ui.DrawableRegister;
import net.stickboyproductions.tetrisattack.ui.Screen;
import net.stickboyproductions.tetrisattack.ui.TextureStore;
import org.lwjgl.opengl.Display;

import javax.inject.Inject;


/**
 * User: Pete
 * Date: 27/10/12
 * Time: 16:48
 */
public class Runner {

  private InputNotifier inputNotifier;
  private Screen screen;
  private TextureStore textureStore;
  private SystemClock clock;
  private DrawableRegister drawableRegister;

  private Grid grid;
  private Game game;

  @Inject
  public Runner(InputNotifier inputNotifier, Screen screen,
                TextureStore textureStore,
                SystemClock clock, DrawableRegister drawableRegister,
                Grid grid, Game game) {
    this.inputNotifier = inputNotifier;
    this.screen = screen;
    this.textureStore = textureStore;
    this.clock = clock;
    this.drawableRegister = drawableRegister;
    this.grid = grid;
    this.game = game;
  }

  public void init() throws Exception {
    screen.create();
    textureStore.init();
//    grid.init();
    game.init();

    clock.start();
  }

  public void run() throws Exception {
    while (!Display.isCloseRequested()) {
      // Clear the screen before we redraw things
      screen.clear();
      // Check any user input
      inputNotifier.pollInput();

      clock.tick();
      game.update();

      // Finally redraw the screen
      drawableRegister.draw();
      screen.update();
    }
    screen.destroy();
  }


}
