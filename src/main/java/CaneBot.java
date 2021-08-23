import org.jnativehook.GlobalScreen;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CaneBot {

    private final GuiFrame gui;
    private BotThread botThread;
    private BackgroundCheckThread backgroundCheckThread;


    public CaneBot() {
        this.gui = new GuiFrame();
    }

    public void runHook() {
        if (botThread == null) {
            gui.updateStatus("RUNNING", Color.GREEN);
            botThread = new BotThread();
            botThread.start();
            backgroundCheckThread = new BackgroundCheckThread(this);
            backgroundCheckThread.start();
        } else {
            if (botThread.isPaused()) {
                gui.updateStatus("RUNNING", Color.GREEN);
                botThread.unpause();
            }
        }
    }

    public void pauseHook() {
        if (botThread != null) {
            gui.updateStatus("PAUSED", Color.ORANGE);
            botThread.pause();
        }
    }

    public void killHook() {
        if (botThread != null) {
            gui.updateStatus("STOPPED", Color.RED);
            botThread.interrupt();
            botThread = null;
            backgroundCheckThread.interrupt();
            backgroundCheckThread = null;
        }
    }

    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        CaneBot caneBot = new CaneBot();
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new ShortcutManager(caneBot));
    }

}
