package net.stickboyproductions.tetrisattack.timing;

import javax.inject.Singleton;

/**
 * User: Pete
 * Date: 02/01/13
 * Time: 20:59
 */
@Singleton
public class SystemClock extends AbstractClock {

  /**
   * Get the time in milliseconds
   *
   * @return The system time in milliseconds
   */
  protected long getTime() {
    return System.nanoTime() / 1000000;
  }

}
