package net.stickboyproductions.tetrisattack.io;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.stickboyproductions.tetrisattack.actions.KeyPressed;
import net.stickboyproductions.tetrisattack.interfaces.Controllable;
import net.stickboyproductions.tetrisattack.timing.GameClock;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;

/**
 * User: Pete
 * Date: 02/01/13
 * Time: 22:14
 */
@Singleton
public class InputController {

  private Map<Integer, KeyPressed> actions = Maps.newHashMap();
  private static final List<Controllable> registeredHandlers = Lists.newArrayList();

  private GameClock clock;
  private InputNotifier inputNotifier;

  @Inject
  public InputController(GameClock gameClock, InputNotifier inputNotifier) {
    this.clock = gameClock;
    this.inputNotifier = inputNotifier;
  }

  public void createAction(Integer keyCode, boolean spammable) {
    KeyPressed keyPressed = new KeyPressed(keyCode, this, spammable);
    clock.register(keyPressed);
    actions.put(keyCode, keyPressed);
  }

  public void removeAction(Integer keyCode) {
    actions.get(keyCode).setFinished(true);
    actions.remove(keyCode);
  }

  public void registerHandler(Controllable controllable) {
    registeredHandlers.add(controllable);
  }

  public void unRegisterHandler(Controllable controllable) {
    registeredHandlers.remove(controllable);
  }

  public static List<Controllable> getRegisteredHandlers() {
    return registeredHandlers;
  }

  public void update() {
    inputNotifier.pollInput(this);
  }
}
