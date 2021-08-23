import javax.swing.*;
import java.awt.*;

public class GuiFrame extends JFrame {

    JLabel status;
    GridBagLayout gridBagLayout;

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
        status.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        add(status);

        updateStatus(Status.WAITING_FOR_START);

        setVisible(true);
    }

    public void updateStatus(Status status) {
        this.status.setText(status.getText());
        this.status.setForeground(status.getColor());
    }

    public enum Status {
        WAITING_FOR_START("WAITING TO BE STARTED", new Color(170, 0, 0)),
        RUNNING("RUNNING", new Color(0, 128, 0)),
        PAUSED("PAUSED", new Color(170, 130, 0)),
        STOPPED("STOPPED", new Color(170, 0, 0));

        private String text;
        private Color color;

        Status(String text, Color color) {
            this.text = text;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public Color getColor() {
            return color;
        }
    }

}
