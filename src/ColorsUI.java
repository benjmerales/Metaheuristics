import javax.swing.*;
import java.awt.*;
import java.net.URL;

public interface ColorsUI {
    Color BG1 = new Color(152,183,222);
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
            URL imageURL = ColorsUI.class.getResource("button_hover.gif");
            //Graphics2D g2d = (Graphics2D) imageURL.create();
            //g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_DITHERING,RenderingHints.VALUE_RENDER_QUALITY));
            i.setRolloverIcon(new ImageIcon(imageURL));
        }
    }
}


