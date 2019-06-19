package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class Utils {

    public static final String UPLOADED_FOLDER = "src/main/resources/";

    public static final String FILE_NAME = "logo.png";

    public static final String NEW_FILE_NAME = "open_cv_file.png";

    public static final String NEW_FILE_NAME_TEST = "new_file";

    public static final String PNG = "PNG";

    public static BufferedImage readImageFromResources() {
        BufferedImage image = null;
        try {
            System.out.println("Start read file from folder");
            image = ImageIO.read(new File(UPLOADED_FOLDER + FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void writeImageToResources(BufferedImage image) {
        try {
            System.out.println("Start write file from folder");
            ImageIO.write(Objects.requireNonNull(image), PNG, generateUniqueFile());
            System.out.println("DONE!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File generateUniqueFile() {
        String extension = "." + PNG;
        File dir = Paths.get(UPLOADED_FOLDER).toFile();

        File file = new File(dir, NEW_FILE_NAME_TEST + extension);

        int num = 0;
        while (file.exists()) {
            num++;
            file = new File(dir, NEW_FILE_NAME_TEST + "_" + num + extension);
        }

        return file;
    }

    public static BufferedImage grayScaleYUV(BufferedImage bi) {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);

                bi.setRGB(j, i, newColor.getRGB());
            }
        }
        return bi;
    }

    public static int[] convertToArrayGray(BufferedImage bi) {
        int[] grayArray = new int[bi.getHeight() * bi.getWidth()];
        int index = 0;
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                grayArray[index++] = c.getRed();
            }
        }
        return grayArray;
    }

    public static void outputHistogram(int[] histogram, int threshold) {
        int maxValue = 0;

        for (int i = 0; i < histogram.length; i++) {

            for (int j = 0; j < histogram[i]; j++) {
                if (j > maxValue) {
                    maxValue = j;
                }
            }
        }
        for (int i = 0; i < histogram.length; i++) {
            for (int j = 0; j < (int) (histogram[i] / (double) maxValue * 150); j++) {
                System.out.print("*");
            }
            if (i == threshold) {
                System.out.println("{}");
            }
            System.out.println();
        }
    }
}
