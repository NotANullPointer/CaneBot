import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundCheckThread extends Thread {

    private Robot robot;
    private final CaneBot caneBot;
    private final Rectangle screenshotZone;

    public BackgroundCheckThread(CaneBot caneBot) {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.screenshotZone = new Rectangle(10, 150, 70, 70);
        this.caneBot = caneBot;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            BufferedImage screenshot = robot.createScreenCapture(screenshotZone);
            Color color = averageColor(screenshot);

            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();

            if (red == 33 && green == 23 && (blue == 16 || blue == 17)) {
                caneBot.killHook();
            }

            try {
                sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
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
}
