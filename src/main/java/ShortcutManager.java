import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class ShortcutManager implements NativeKeyListener {

    private final CaneBot caneBot;

    public ShortcutManager(CaneBot caneBot) {
        this.caneBot = caneBot;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if ((nativeKeyEvent.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0) { //is CTRL on
            if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_R) { //RUN
                caneBot.runHook();
            } else if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_P) { //PAUSE
                caneBot.pauseHook();
            } else if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_K) { //KILL
                caneBot.killHook();
            }
        }
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

}
