import javax.swing.*;
import java.awt.*;

public class GuiFrame extends JFrame {

    JLabel status;
    FlowLayout flowLayout;

    GuiFrame() {
        super("Sugarcane bot");
        setSize(300, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.CENTER);
        setLayout(flowLayout);

        status = new JLabel();
        status.setBackground(Color.BLACK);
        status.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        add(status);

        updateStatus("WAITING TO BE STARTED", Color.RED);

        setVisible(true);
    }

    public void updateStatus(String text, Color color) {
        status.setText(text);
        status.setForeground(color);
    }

}
