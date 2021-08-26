import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GuiFrame extends JFrame {

    private static final int FONT_SIZE = 18;

    private final CaneBot instance;
    private JLabel status;
    private JComboBox<String> botType;
    private FlowLayout flowLayout;

    GuiFrame(CaneBot instance) {
        super("MC bot");

        this.instance = instance;
        updateBotType("Sugar Cane");

        setSize(300, 150);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("bot.png")));

        flowLayout = new FlowLayout();
        flowLayout.setVgap(15);
        flowLayout.setHgap(50);
        setLayout(flowLayout);

        botType = new JComboBox<>(new String[]{"Sugar Cane", "Nether Wart"});
        botType.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        botType.setBackground(Color.WHITE);
        botType.setPreferredSize(new Dimension(200,25));
        botType.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateBotType((String)e.getItem());
            }
        });
        add(botType);

        status = new JLabel();
        status.setBackground(Color.BLACK);
        status.setFont(new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE));
        add(status);

        updateStatus(Status.WAITING_FOR_START);

        setVisible(true);
    }

    private void updateBotType(String botType) {
        if (botType.equalsIgnoreCase("Sugar Cane")) {
            instance.setBotType(CaneBotThread.class);
        } else if (botType.equalsIgnoreCase("Nether Wart")) {
            instance.setBotType(NetherwartBotThread.class);
        }
    }

    public void updateStatus(Status status) {
        this.status.setText(status.getText());
        this.status.setForeground(status.getColor());
        this.status.setIcon(status.getIcon());

        if (status == Status.RUNNING || status == Status.PAUSED) {
            this.botType.setEnabled(false);
        } else {
            this.botType.setEnabled(true);
        }
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
