package commons;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static utils.Utils.*;

public class GaussianBlur {

    private static GaussianBlur blur;

    private GaussianBlur() {
    }

    public static GaussianBlur getInstance() {
        if (blur == null) {
            blur = new GaussianBlur();
        }
        return blur;
    }

    // G(x,y) = 1 / 2 * PI sigma^2 * e^-(x^2 + y^2)/2sigma^2
    public double gaussianModel(double x, double y, double variance) {
        return (1 / (2 * Math.PI * Math.pow(variance, 2))
                * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(variance, 2))));
    }

    public double[][] generateWeightMatrix(int radius, double variance) {
        double[][] weights = new double[radius][radius];
        double summation = 0;

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = gaussianModel(i - radius / 2, j - radius / 2, variance);
                summation += weights[i][j];
            }
        }

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] /= summation;

            }
        }

        return weights;
    }

    public void printWeightedMatrixToFile(double[][] weightMatrix) {
        BufferedImage img = new BufferedImage(weightMatrix.length, weightMatrix.length, BufferedImage.TYPE_INT_RGB);

        double max = 0;
        for (int i = 0; i < weightMatrix.length; i++) {
            for (int j = 0; j < weightMatrix.length; j++) {
                max = Math.max(max, weightMatrix[i][j]);
            }
        }

        for (int i = 0; i < weightMatrix.length; i++) {
            for (int j = 0; j < weightMatrix.length; j++) {
                int grayScaleValue = (int) (weightMatrix[i][j] / max * 255d);
                img.setRGB(i, j, new Color(grayScaleValue, grayScaleValue, grayScaleValue).getRGB());
            }
        }

        try {
            ImageIO.write(img, PNG, new File(UPLOADED_FOLDER + TEST_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage createGaussianImage(BufferedImage source, double[][] weights, int radius) {
        BufferedImage destination = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < source.getWidth() - radius; x++) {
            for (int y = 0; y < source.getHeight() - radius; y++) {

                double[][] distributedColorRed = new double[radius][radius];
                double[][] distributedColorGreen = new double[radius][radius];
                double[][] distributedColorBlue = new double[radius][radius];

                for (int weightX = 0; weightX < weights.length; weightX++) {
                    for (int weightY = 0; weightY < weights[weightX].length; weightY++) {

                        try {
                            int sampleX = x + weightX - (weights.length / 2);
                            int sampleY = y + weightY - (weights.length / 2);

                            double currentWeight = weights[weightX][weightY];

                            Color sampleColored = new Color(source.getRGB(sampleX, sampleY));
                            distributedColorRed[weightX][weightY] = currentWeight * sampleColored.getRed();
                            distributedColorGreen[weightX][weightY] = currentWeight * sampleColored.getGreen();
                            distributedColorBlue[weightX][weightY] = currentWeight * sampleColored.getBlue();
                        } catch (Exception e) {
                            System.out.print("out of bounds.");
                        }
                    }
                }
                destination.setRGB(x, y, new Color(getWightedColorValue(distributedColorRed), getWightedColorValue(distributedColorGreen), getWightedColorValue(distributedColorBlue)).getRGB());
            }
        }
        return destination;
    }

    private int getWightedColorValue(double[][] weightedColor) {
        double summation = 0;

        for (int i = 0; i < weightedColor.length; i++) {
            for (int j = 0; j < weightedColor[i].length; j++) {
                summation += weightedColor[i][j];
            }
        }

        return (int) summation;
    }
}
