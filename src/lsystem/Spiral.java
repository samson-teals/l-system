package lsystem;

import java.awt.geom.Point2D;

/**
 * Draws a spiral.
 */
public class Spiral extends VectorImage implements LSystemImage {
    double baseAngle;
    double currentAngle;
    double currentLength;

    Point2D currentPoint;

    Spiral(int width, int height) {
        super(width, height);

        baseAngle = 15;
    }

    public void drawIteration(int n) {
        currentAngle = 0;
        currentLength = 1;
        currentPoint = new Point2D.Double(0, 0);

        F(n);
    }

    void F(int depth) {
        if (depth == 0) {
            currentPoint = addLine(currentPoint, currentLength, currentAngle);
        } else {
            int newDepth = depth - 1;

            F(newDepth);
            currentAngle += baseAngle;
            currentLength *= 1.03;
            currentPoint = addLine(currentPoint, currentLength, currentAngle);
        }
    }
}
