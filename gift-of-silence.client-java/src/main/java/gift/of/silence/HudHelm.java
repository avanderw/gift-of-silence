package gift.of.silence;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import net.avdw.statemachine.StateMachine;
import net.avdw.vector.Vector2D;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

//http://www.java-gaming.org/index.php/topic,8184.
public class HudHelm {

    public static void main(String[] args) {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.DEBUG)
                .activate();

        StateMachine machine = new StateMachine();
        machine.addState(new State.AdjustingHeading());

        Double radius = 100D;
        Integer width = 400;
        Integer height = 400;

        Vector2D currentHeading = new Vector2D(1D, 0D);
        Vector2D targetHeading = new Vector2D(1D, 0D);
        Vector2D adjustHeading = new Vector2D(1D, 0D);

        currentHeading.length(radius);
        targetHeading.length(radius);
        adjustHeading.length(radius);

        currentHeading.add(width / 2, height / 2);
        targetHeading.add(width / 2, height / 2);
        adjustHeading.add(width / 2, height / 2);

        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                Logger.debug("painting");
                Graphics2D gfx = (Graphics2D) g;
                gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gfx.setColor(Color.black);
                gfx.drawOval(width / 2 - radius.intValue(), height / 2 - radius.intValue(), width / 2, height / 2);

                gfx.setColor(Color.lightGray);
                gfx.drawLine(width / 2, height / 2, adjustHeading.x.intValue(), adjustHeading.y.intValue());

                gfx.setColor(Color.red);
                gfx.drawLine(width / 2, height / 2, targetHeading.x.intValue(), targetHeading.y.intValue());

                gfx.setColor(Color.blue);
                gfx.drawLine(width / 2, height / 2, currentHeading.x.intValue(), currentHeading.y.intValue());
            }
        };

        Frame frame = new Frame("Helm");
        frame.add(canvas);
        frame.setSize(400, 400);
        frame.setVisible(true);
        canvas.createBufferStrategy(4);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Logger.trace(e);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Logger.trace(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Logger.trace(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Logger.trace(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Logger.debug(e);

                targetHeading.x = (double) e.getX() - width / 2;
                targetHeading.y = (double) e.getY() - height / 2;
                targetHeading.length(radius);
                targetHeading.add(width / 2, height / 2);

                canvas.repaint();
            }
        });

        canvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Logger.trace(e);

                adjustHeading.x = (double) e.getX() - width / 2;
                adjustHeading.y = (double) e.getY() - height / 2;
                adjustHeading.length(radius);
                adjustHeading.add(width / 2, height / 2);

                canvas.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Logger.trace(e);
            }
        });
    }

    static abstract class State implements StateMachine.AState {

        List<Class> from = new ArrayList();

        @Override
        public void from(List<Class> states) {
            from.addAll(states);
        }

        @Override
        public List<Class> from() {
            return from;
        }

        static class AdjustingHeading extends State {

            @Override
            public void enter() {
                Logger.trace("enter");
            }

            @Override
            public void exit() {
                Logger.trace("exit");
            }

            @Override
            public void process() {
                Logger.trace("process");
            }

        }
    }
}
