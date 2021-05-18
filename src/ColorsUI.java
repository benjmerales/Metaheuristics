import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public interface ColorsUI {
//    Color BG1 = new Color(152,183,222);
    Color BG1 = Color.ORANGE;
    Color BG2 = new Color(202,209,217);
    static void button_init(JButton[] buttons, int background_id){
        for(JButton i: buttons){
            if(background_id == 1)
                i.setBackground(BG1);
            else
                i.setBackground(BG2);
            i.setBorder(null);
            i.setFocusable(false);
            i.setFocusPainted(false);
            i.setIgnoreRepaint(false);
            i.setRolloverIcon(new ImageIcon("src/resources/button_hover.gif"));
        }
    }
}
