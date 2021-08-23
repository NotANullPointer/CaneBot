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
            gui.updateStatus(GuiFrame.Status.RUNNING);
            botThread = new BotThread();
            botThread.start();
            backgroundCheckThread = new BackgroundCheckThread(this);
            backgroundCheckThread.start();
        } else {
            if (botThread.isPaused()) {
                gui.updateStatus(GuiFrame.Status.RUNNING);
                botThread.unpause();
                backgroundCheckThread.unpause();
            }
        }
    }

    public void pauseHook() {
        if (botThread != null) {
            gui.updateStatus(GuiFrame.Status.PAUSED);
            botThread.pause();
            backgroundCheckThread.pause();
        }
    }

    public void killHook() {
        if (botThread != null) {
            gui.updateStatus(GuiFrame.Status.STOPPED);
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
