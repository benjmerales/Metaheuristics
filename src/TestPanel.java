import javax.swing.*;
import java.awt.*;

public class TestPanel extends JPanel{
    private Image img;
    private String board_str =
            ". Q . . " +
            ". . . Q " +
            "Q . . . " +
            ". . Q . ";
    public TestPanel(String filepath){
        img = new ImageIcon(filepath).getImage();
        this.setBackground(Color.GRAY);
        this.setSize(250,250);
    }
    public TestPanel(){
        img = new ImageIcon("src/wolf1.png").getImage();
        this.setBackground(Color.GRAY);
        this.setSize(250,250);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        String mystr = ". Q . . ";
        int x_pos = 0;
        int cnt = 0;
        for(char c : mystr.toCharArray()){
            if( c == 'Q'){
                if(cnt % 2 == 1)
                    g.setColor(Color.RED);
                else
                    g.setColor(Color.GREEN);
                g.fillRect(x_pos,0, 180, 180);
                g.drawImage(img,x_pos,0,null);
                x_pos+=200; cnt++;
            }
            else if( c == '.'){
                if(cnt % 2 == 1)
                    g.setColor(Color.RED);
                else
                    g.setColor(Color.GREEN);
                cnt++;
                g.fillRect(x_pos,0, 180, 180);
                x_pos+=200;
            }
        }
    }
}

