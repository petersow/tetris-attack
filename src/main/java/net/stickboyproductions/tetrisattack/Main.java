package net.stickboyproductions.tetrisattack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.stickboyproductions.tetrisattack.di.GameModule;

/**
 * User: Pete
 * Date: 27/10/12
 * Time: 17:28
 */
public class Main {

  public static void main(String[] args) throws Exception {
    Injector injector = Guice.createInjector(new GameModule());
    Runner game = injector.getInstance(Runner.class);
    game.init();
    game.run();
  }
}
