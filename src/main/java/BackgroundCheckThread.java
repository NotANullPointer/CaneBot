import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class BackgroundCheckThread extends BotThread {

    private Robot robot;
    private final CaneBot caneBot;
    private volatile boolean paused = false;

    public BackgroundCheckThread(CaneBot caneBot) {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.caneBot = caneBot;
    }

    @Override
    public synchronized void run() {
        while(!isInterrupted()) {
            BufferedImage screenshot = getMinecraftScreenshot();

            if(screenshot == null) {
                new Thread(() ->
                        JOptionPane.showMessageDialog(new JFrame(),
                                "No Minecraft instance found", "Error", JOptionPane.ERROR_MESSAGE))
                        .start();
                caneBot.killHook();
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return;
            }

            Color color = averageColor(screenshot);

            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();

            if (red == 33 && green == 23 && (blue == 16 || blue == 17)) {
                caneBot.killHook();
            }

            try {
                sleep(70);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void pause() {
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public void unpause() {
        paused = false;
    }

    private Color averageColor(BufferedImage bi) {
        int[] colorSum = new int[3];
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                Color pixel = new Color(bi.getRGB(x, y));
                colorSum[0] += pixel.getRed();
                colorSum[1] += pixel.getGreen();
                colorSum[2] += pixel.getBlue();
            }
        }
        int num = bi.getWidth() * bi.getHeight();
        colorSum[0] /= num;
        colorSum[1] /= num;
        colorSum[2] /= num;
        return new Color(colorSum[0], colorSum[1], colorSum[2]);
    }

    private BufferedImage getMinecraftScreenshot() {
        DesktopWindow minecraft = WindowUtils.getAllWindows(true).stream().filter(w -> w.getTitle().startsWith("Minecraft")).findAny().orElse(null);
        if (minecraft == null) {
            return null;
        }
        Rectangle minecraftWindow = minecraft.getLocAndSize();
        minecraftWindow.setSize(70, 70);
        return robot.createScreenCapture(minecraftWindow);
    }

}
