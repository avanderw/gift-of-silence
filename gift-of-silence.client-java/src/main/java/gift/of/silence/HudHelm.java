package gift.of.silence;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.avdw.vector.Vector2D;

public class HudHelm {

    public static void main(String[] args) {
        Double radius = 100D;
        Double angle = 0D;

        Integer width = 400;
        Integer height = 400;
        Frame frame = new Frame("Helm") {
            @Override
            public void paint(Graphics g) {
                Graphics2D gfx = (Graphics2D) g;
                gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gfx.setColor(Color.blue);
                gfx.drawOval(width / 2 - radius.intValue(), height / 2 - radius.intValue(), width / 2, height / 2);

                Vector2D heading = new Vector2D();
                heading.offsetPolar(radius, angle);
                heading.add(width / 2, height / 2);
                gfx.drawLine(width / 2, height / 2, heading.x.intValue(), heading.y.intValue());
            }
        };

        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
}
