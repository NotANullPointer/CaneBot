import org.jnativehook.GlobalScreen;

import java.util.logging.Level;
import java.util.logging.Logger;


public class CaneBot {

    private final GuiFrame gui;
    private BotThread botThread;
    private Class<? extends BotThread> botThreadClass;
    private BackgroundCheckThread backgroundCheckThread;


    public CaneBot() {
        this.gui = new GuiFrame(this);
    }

    public void setBotType(Class<? extends BotThread> botClass) {
        this.botThreadClass = botClass;
    }

    private void newBotThread() {
        if (botThreadClass == CaneBotThread.class) {
            botThread = new CaneBotThread();
        } else if (botThreadClass == NetherwartBotThread.class) {
            botThread = new NetherwartBotThread();
        }
    }

    public void runHook() {
        if (botThread == null) {
            gui.updateStatus(GuiFrame.Status.RUNNING);
            newBotThread();
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
            try {
                botThread.pause();
                backgroundCheckThread.pause();
                System.out.println("BBBBBB");
            } catch (Exception e) {
                System.out.println("AAAA");
                e.printStackTrace();
            }
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
