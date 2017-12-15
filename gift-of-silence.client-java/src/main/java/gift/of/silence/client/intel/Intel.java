package gift.of.silence.client.intel;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class Intel {

    public static void main(String[] args) {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();

        Frame frame = new Frame("Intel");
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        SonarCanvas sonarCanvas = new SonarCanvas();
        frame.add(sonarCanvas);
        sonarCanvas.createBufferStrategy(4);
    }
}
