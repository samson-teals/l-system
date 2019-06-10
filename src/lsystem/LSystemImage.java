package lsystem;

import java.awt.image.BufferedImage;

public interface LSystemImage {
    /**
     * Initializes image. Should be called right before drawIteration.
     */
    public void init();

    /**
     * Renders image. Should be called right after drawIteration.
     */
    public void render();

    /**
     * Return a buffered image that is ready to render.
     */
    public BufferedImage getImage();

    /**
     * Calculates image iteration n.
     */
    public void drawIteration(int n);
}
