import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;

public class BotThread extends Thread {

    private final Random randomGen;
    private Robot robot;
    private boolean paused;

    public BotThread() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.randomGen = new Random();
    }

    //Random with increments of 50
    private int randomBetween(int low, int high) {
        return 50 * (randomGen.nextInt(high/50-low/50) + low/50);
    }

    @Override
    public synchronized void run() {
        try {
            stepSleep(1000);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            while (!isInterrupted()) {
                holdKey(Key.KEY_D, 500);
                for(int i = 0; i<26; i++) {
                    holdKey(Key.KEY_S, 16300);
                    holdKey(Key.KEY_D, 16300);
                }
                holdKey(Key.KEY_S, 16300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private synchronized void holdKey(Key key, int time) throws InterruptedException {
        robot.keyPress(key.getKey());
        key.setHeld(true);
        stepSleep(time);
        robot.keyRelease(key.getKey());
        key.setHeld(false);
        stepSleep(randomBetween(100, 200));
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

    public boolean isPaused() {
        return paused;
    }

    public synchronized void unpause() {
        paused = false;
        Arrays.stream(Key.values()).filter(Key::isHeld).forEach(k -> robot.keyPress(k.getKey()));
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        this.notifyAll();
    }

    @Override
    public void interrupt() {
        super.interrupt();
        robot.keyRelease(VK_S);
        robot.keyRelease(VK_D);
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
    }


    enum Key {
        KEY_S(VK_S),
        KEY_D(VK_D);

        private final int key;
        private boolean isHeld;
        Key(int key) {
            this.key = key;
            this.isHeld = false;
        }

        void setHeld(boolean held) {
            this.isHeld = held;
        }

        int getKey() {
            return key;
        }

        boolean isHeld() {
            return isHeld;
        }
    }

}
