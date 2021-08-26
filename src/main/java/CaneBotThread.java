import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class CaneBotThread extends BotThread {

    private boolean paused = false;

    public CaneBotThread() {
        super();
    }

    @Override
    public void run() {
        try {
            stepSleep(1000);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            while (!isInterrupted()) {
                holdKey(Key.KEY_D, 400);
                for(int i = 0; i<26; i++) {;
                    holdKey(Key.KEY_S, 14000);
                    holdKey(Key.KEY_D, 14000);
                }
                holdKey(Key.KEY_S, 14000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void interrupt() {
        Arrays.stream(Key.values()).filter(Key::isHeld).forEach(k -> {
            robot.keyRelease(k.getKey());
            k.setHeld(false);
        });
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
        super.interrupt();
    }

    private void holdKey(Key key, int time) throws InterruptedException {
        robot.keyPress(key.getKey());
        key.setHeld(true);
        stepSleep(time);
        robot.keyRelease(key.getKey());
        key.setHeld(false);
    }

    private synchronized void stepSleep(int time) throws InterruptedException {
        for(int i = 0; i < time; i += 50) {
            sleep(50);
            if (paused) {
                this.wait();
            }
        }
    }

    public void pause() {
        paused = true;
        Arrays.stream(Key.values()).filter(Key::isHeld).forEach(k -> robot.keyRelease(k.getKey()));
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
    }

    public synchronized void unpause() {
        paused = false;
        Arrays.stream(Key.values()).filter(Key::isHeld).forEach(k -> robot.keyPress(k.getKey()));
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        this.notifyAll();
    }

    public boolean isPaused() {
        return paused;
    }

}
