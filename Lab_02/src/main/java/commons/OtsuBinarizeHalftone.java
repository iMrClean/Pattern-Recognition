package commons;

import java.awt.image.BufferedImage;

import static utils.Utils.convertToArrayGray;
import static utils.Utils.grayScaleYUV;
import static utils.Utils.outputHistogram;

public class OtsuBinarizeHalftone {

    private int[] histogram;

    public BufferedImage otsuBinarize(BufferedImage image) {
        image = grayScaleYUV(image);
        int[] grayScaleArray = convertToArrayGray(image);
        int threshold = getOtsuThreshold(grayScaleArray);
        System.out.println("Порог " + threshold);
        outputHistogram(histogram, threshold);
        image = new BinarizeHalftone().binarize(image, threshold);
        return image;
    }

    private int getOtsuThreshold(int[] grayScaleValues) {
        int n = grayScaleValues.length;
        this.histogram = getHistogram(grayScaleValues);

        float sum = 0;
        for (int i = 0; i < 256; i++)
            sum += i * histogram[i];

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 127;

        for (int t = 0; t < 256; t++) {
            wB += histogram[t];               // Weight Background
            if (wB == 0) continue;

            wF = n - wB;                 // Weight Foreground
            if (wF == 0) break;

            sumB += (float) (t * histogram[t]);

            float mB = sumB / wB;            // Background
            float mF = (sum - sumB) / wF;    // Foreground

            // Calculate Between Class Variance
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            // Check if new maximum found
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = t;
            }
        }

        return threshold;
    }

    private int[] getHistogram(int[] grayScaleValues) {
        int[] histogram = new int[256];

        for (int grayScaleValue : grayScaleValues) {
            histogram[grayScaleValue]++;
        }

        return histogram;
    }
}
