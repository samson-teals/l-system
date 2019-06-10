package lsystem;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Main {
    LSystemImage iimage;
    int iteration = 0;

    /**
     * The ImageCanvas nested class is only used for drawing. No calculations should be done here.
     */
    class ImageCanvas extends Canvas {
        int windowOffset;

        public ImageCanvas(int width, int height, int windowOffset) {
            setSize(width, windowOffset + height);

            this.windowOffset = windowOffset;
        }

        /**
         * This method paints the canvas and is called by the system whenever a repaint is requested.
         */
        public void paint(Graphics g) {
            BufferedImage image = iimage.getImage();
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(image, 0, imageHeight + windowOffset, imageWidth, -imageHeight, null);
        }
    }

    public Main(LSystemImage iimage) {
        this.iimage = iimage;

        // sets up graphics
        Frame frame = new Frame("L-Systems");
        frame.setVisible(true);
        Insets insets = frame.getInsets();

        Canvas canvas = new ImageCanvas(iimage.getImage().getWidth(), iimage.getImage().getHeight(), insets.top);
        frame.add(canvas);
        frame.setLayout(null);
        frame.setSize(canvas.getWidth(), canvas.getHeight());

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        canvas.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char k = e.getKeyChar();
                if ((k >= '0') && (k <= '9')) {
                    iteration = k - '0';
                } else if ((k == '=') || (k == '+')) {
                    iteration++;
                } else if ((k == '-') || (k == '_')) {
                    iteration--;
                }

                if (iteration < 0) {
                    iteration = 0;
                }

                System.out.println("Drawing iteration: " + iteration);
                iimage.init();
                iimage.drawIteration(iteration);
                iimage.render();
                System.out.println("Rendered iteration: " + iteration);

                canvas.repaint();
            }
        });

        // initial draw
        iimage.init();
        iimage.drawIteration(iteration);
        iimage.render();

        canvas.repaint();
    }

    public static void main(String args[]) {
        int width = 1000;
        int height = 1000;

        LSystemImage iimage = new Spiral(width, height);

        new Main(iimage);
    }
}
