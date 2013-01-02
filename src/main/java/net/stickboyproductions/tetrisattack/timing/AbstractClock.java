package net.stickboyproductions.tetrisattack.timing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.stickboyproductions.tetrisattack.enums.GameState;
import net.stickboyproductions.tetrisattack.interfaces.TimeDelayedAction;
import net.stickboyproductions.tetrisattack.interfaces.TimeTickingAction;
import net.stickboyproductions.tetrisattack.model.Game;

import javax.inject.Singleton;
import java.util.List;

/**
 * User: Pete
 * Date: 29/10/12
 * Time: 21:10
 */
public abstract class AbstractClock {

  private final List<TimeDelayedActionEntry> delayedActions = Lists.newArrayList();
  private final List<TimeTickingActionEntry> tickingActions = Lists.newArrayList();
  private final List<TimeTickingActionGroupEntry> tickingActionGroups = Lists.newArrayList();

  public void start() {
  }

  /**
   * Get the time in milliseconds
   *
   * @return The system time in milliseconds
   */
  protected abstract long getTime();

  public void tick() {
    // Fire any delayed actions
    for (TimeDelayedActionEntry entry : Lists.newArrayList(delayedActions)) {
      if (getTime() >= entry.getTimeToFire()) {
        entry.getAction().timedExecution();
        delayedActions.remove(entry);
      }
    }

    // Tick all ticking actions
    for (TimeTickingActionEntry entry : Lists.newArrayList(tickingActions)) {
      TimeTickingAction action = entry.getAction();
      if (action.isFinished()) {
        action.end();
        tickingActions.remove(entry);
      } else {
        entry.getAction().tick(getTime() - entry.getTimeStarted());
      }
    }

    for (TimeTickingActionGroupEntry entry : ImmutableList.copyOf(tickingActionGroups)) {
      if (entry.isFinished()) {
        entry.end();
        tickingActionGroups.remove(entry);
      } else {
        entry.tick(getTime() - entry.getTimeStarted());
      }
    }
  }

  public void schedule(TimeDelayedAction action, int timeInMilliseconds) {
    delayedActions.add(new TimeDelayedActionEntry(action, timeInMilliseconds + getTime()));
  }

  public void register(TimeTickingAction action) {
    tickingActions.add(new TimeTickingActionEntry(action, getTime()));
    action.start();
  }

  public void register(List<? extends TimeTickingAction> actions) {
    TimeTickingActionGroupEntry newGroup = new TimeTickingActionGroupEntry(actions, getTime());
    tickingActionGroups.add(newGroup);
    newGroup.start();
  }

  class TimeDelayedActionEntry {
    private TimeDelayedAction action;
    private long timeToFire;

    TimeDelayedActionEntry(TimeDelayedAction action, long timeToFire) {
      this.action = action;
      this.timeToFire = timeToFire;
    }

    public TimeDelayedAction getAction() {
      return action;
    }

    public void setAction(TimeDelayedAction action) {
      this.action = action;
    }

    public long getTimeToFire() {
      return timeToFire;
    }

    public void setTimeToFire(long timeToFire) {
      this.timeToFire = timeToFire;
    }
  }

  class TimeTickingActionEntry {
    private TimeTickingAction action;
    private long timeStarted;

    TimeTickingActionEntry(TimeTickingAction action, long timeStarted) {
      this.action = action;
      this.timeStarted = timeStarted;
    }

    public TimeTickingAction getAction() {
      return action;
    }

    public void setAction(TimeTickingAction action) {
      this.action = action;
    }

    public long getTimeStarted() {
      return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
      this.timeStarted = timeStarted;
    }
  }

  class TimeTickingActionGroupEntry {
    private List<? extends TimeTickingAction> actions;
    private long timeStarted;

    TimeTickingActionGroupEntry(List<? extends TimeTickingAction> actions, long timeStarted) {
      this.actions = actions;
      this.timeStarted = timeStarted;
    }

    public List<? extends TimeTickingAction> getActions() {
      return actions;
    }

    public void setActions(List<? extends TimeTickingAction> actions) {
      this.actions = actions;
    }

    public long getTimeStarted() {
      return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
      this.timeStarted = timeStarted;
    }

    public void start() {
      for(TimeTickingAction action : actions) {
        action.start();
      }
    }

    public boolean isFinished() {
      for(TimeTickingAction action : actions) {
        if(!action.isFinished()) {
          return false;
        }
      }
      return true;
    }

    public void end() {
      for(TimeTickingAction action : actions) {
        action.end();
      }
    }

    public void tick(long timeElapsed) {
      for(TimeTickingAction action : actions) {
        action.tick(timeElapsed);
      }
    }
  }
}
