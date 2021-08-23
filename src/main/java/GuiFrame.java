import javax.swing.*;
import java.awt.*;

public class GuiFrame extends JFrame {

    private static final int FONT_SIZE = 18;

    private JLabel status;
    private GridBagLayout gridBagLayout;

    GuiFrame() {
        super("Sugarcane bot");
        setSize(300, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sugarcane.png")));

        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        status = new JLabel();
        status.setBackground(Color.BLACK);
        status.setFont(new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE));
        add(status);

        updateStatus(Status.WAITING_FOR_START);

        setVisible(true);
    }

    public void updateStatus(Status status) {
        this.status.setText(status.getText());
        this.status.setForeground(status.getColor());
        this.status.setIcon(status.getIcon());
    }

    public enum Status {
        WAITING_FOR_START("WAITING TO BE STARTED", new Color(170, 0, 0), "waiting.png"),
        RUNNING("RUNNING", new Color(0, 128, 0), "running.png"),
        PAUSED("PAUSED", new Color(170, 130, 0), "paused.png"),
        STOPPED("STOPPED", new Color(170, 0, 0), "stopped.png");

        private String text;
        private Color color;
        private Icon icon;

        Status(String text, Color color, String iconPath) {
            this.text = text;
            this.color = color;
            this.icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(iconPath))
                    .getScaledInstance(FONT_SIZE, FONT_SIZE,  java.awt.Image.SCALE_SMOOTH));
        }

        public String getText() {
            return text;
        }

        public Color getColor() {
            return color;
        }

        public Icon getIcon() {
            return icon;
        }
    }

}
