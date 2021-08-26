import java.awt.*;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

public abstract class BotThread extends Thread {

    protected final Random randomGen;
    protected Robot robot;

    protected BotThread() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.randomGen = new Random();
    }

    public abstract void pause();

    public abstract boolean isPaused();

    public abstract void unpause();

    enum Key {
        KEY_W(VK_W),
        KEY_A(VK_A),
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
