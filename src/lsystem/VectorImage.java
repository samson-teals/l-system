package lsystem;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class VectorImage {
    BufferedImage image;
    int color;

    int width;
    int height;

    double scaleFactor;
    int offsetX;
    int offsetY;

    Rectangle2D bounds;
    ArrayList<Line2D> lines;

    VectorImage(int width, int height) {
        color = new Color(0, 255, 0).getRGB();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        this.width = width;
        this.height = height;

        bounds = new Rectangle2D.Double();
        lines = new ArrayList<Line2D>();
    }

    public BufferedImage getImage() {
        return image;
    }

    /**
     * Call this function to initialize the line array for a new iteration.
     */
    public void init() {
        bounds.setRect(0, 0, 0, 0);

        lines.clear();
    }

    /**
     * Called to render the image at the end of the call to drawIteration.
     */
    public void render() {
        clearImage();
        calculateScaleFactor();

        Graphics2D g2 = image.createGraphics();
        for (Line2D line : lines) {
            int x1 = (int)((line.getX1() - bounds.getMinX()) * scaleFactor) + offsetX;
            int y1 = (int)((line.getY1() - bounds.getMinY()) * scaleFactor) + offsetY;
            int x2 = (int)((line.getX2() - bounds.getMinX()) * scaleFactor) + offsetX;
            int y2 = (int)((line.getY2() - bounds.getMinY()) * scaleFactor) + offsetY;

            g2.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * Calculate endpoint of line based on start position, length, and angle (in degrees).
     * - 0 degrees is straight to the right.
     *
     * Returns a point which can be used in another call to addLine().
     */
    Point2D addLine(Point2D start, double length, double angle) {
        double rads = Math.toRadians(angle);
        double cos = Math.cos(rads);
        double sin = Math.sin(rads);

        double x1 = start.getX();
        double y1 = start.getY();

        double x2 = x1 + length * cos;
        double y2 = y1 + length * sin;

        addLine(x1, y1, x2, y2);

        return new Point2D.Double(x2, y2);
    }

    /**
     * Basic version using primitive doubles.
     */
    void addLine(double x1, double y1, double x2, double y2) {
        Line2D segment = new Line2D.Double(x1, y1, x2, y2);

        Rectangle2D lineBounds = segment.getBounds2D();
        bounds.add(lineBounds);

        lines.add(segment);
    }

    /**
     * Erase the image in preparation for redrawing.
     */
    private void clearImage() {
        Graphics2D g2 = image.createGraphics();
        g2.setBackground((new Color(0, 0, 0)));
        g2.clearRect(0, 0, image.getWidth(), image.getHeight());
    }

    /**
     * Calculate scale factor from bounds.
     */
    private void calculateScaleFactor() {
        double minX = bounds.getMinX();
        double maxX = bounds.getMaxX();
        double minY = bounds.getMinY();
        double maxY = bounds.getMaxY();

        double plotWidth = maxX - minX;
        double plotHeight = maxY - minY;

        // Determine minimum scale factor.
        double width = this.width;
        double height = this.height;

        scaleFactor = Math.min(width / plotWidth, height / plotHeight);

        offsetX = (int)((width - (scaleFactor * plotWidth)) / 2);
        offsetY = (int)((height - (scaleFactor * plotHeight)) / 2);
    }
}
