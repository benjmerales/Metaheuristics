import javax.swing.*;

public class Tester extends JFrame{
    private JButton changePanelButton;
    private JPanel changePanel;
    private JPanel otherPanel;
    private JPanel mainPanel;

    public Tester() {
        this.setSize(800,800);
        this.setContentPane(mainPanel); // A created panel using Intellij IDE
        otherPanel = new TestPanel(); // A custom created panel without the use of
        changePanelButton.addActionListener(e -> {
            changePanel = otherPanel;
        });

    }

    public static void main(String[] args) {
        Tester test = new Tester();
        test.setVisible(true);
    }
}
