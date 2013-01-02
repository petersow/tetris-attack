package net.stickboyproductions.tetrisattack.io;

import com.google.common.collect.Lists;
import net.stickboyproductions.tetrisattack.interfaces.Controllable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.List;

/**
 * User: Pete
 * Date: 27/10/12
 * Time: 17:25
 */
public class InputNotifier {

  private static final List<Controllable> registeredHandlers = Lists.newArrayList();

  public InputNotifier() {
  }

  public void pollInput() {

    if (Mouse.isButtonDown(0)) {
      int x = Mouse.getX();
      int y = Mouse.getY();

      System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
    }
    while (Keyboard.next()) {
      if (Keyboard.getEventKeyState()) {
        if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
          for(Controllable controllable : registeredHandlers ) {
            controllable.moveLeftPressed();
          }
        }
        if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
          for(Controllable controllable : registeredHandlers ) {
            controllable.moveRightPressed();
          }
        }
        if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
          for(Controllable controllable : registeredHandlers ) {
            controllable.moveUpPressed();
          }
        }
        if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
          for(Controllable controllable : registeredHandlers ) {
            controllable.moveDownPressed();
          }
        }
        if (Keyboard.getEventKey() == Keyboard.KEY_X) {
          for(Controllable controllable : registeredHandlers ) {
            controllable.actionPressed();
          }
        }
        if (Keyboard.getEventKey() == Keyboard.KEY_P) {
          for(Controllable controllable : registeredHandlers ) {
            controllable.pausePressed();
          }
        }
        if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
          for(Controllable controllable : registeredHandlers ) {
            controllable.newLinePressed();
          }
        }
      } else {
        if (Keyboard.getEventKey() == Keyboard.KEY_A) {
          System.out.println("A Key Released");
        }
        if (Keyboard.getEventKey() == Keyboard.KEY_S) {
          System.out.println("S Key Released");
        }
        if (Keyboard.getEventKey() == Keyboard.KEY_D) {
          System.out.println("D Key Released");
        }
      }
    }
  }

  public void register(Controllable controllable) {
    registeredHandlers.add(controllable);
  }

  public void unRegister(Controllable controllable) {
    registeredHandlers.remove(controllable);
  }
}
