package commons;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BinarizeHalftone {

    public BufferedImage binarize(BufferedImage image, int threshold) {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                Color newColor;
                if ((red + green + blue) / 3 < threshold) {
                    newColor = new Color(0, 0, 0);
                } else {
                    newColor = new Color(255, 255, 255);
                }
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }

    public BufferedImage binarize2(BufferedImage image, int threshold) {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                Color newColor;
                if (red < threshold || green < threshold || blue < threshold) {
                    newColor = new Color(0, 0, 0);
                } else {
                    newColor = new Color(255, 255, 255);
                }
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }
}
