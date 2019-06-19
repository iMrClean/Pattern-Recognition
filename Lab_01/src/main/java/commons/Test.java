package commons;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import static utils.Utils.*;

public class Test {

    private final double[] filter = new double[]{
            0.5, 0.75, 0.5,
            0.75, 1, 0.75,
            0.5, 0.75, 0.5
    };

    public void start() throws IOException {
        System.out.println("Start filter...");
        BufferedImage srcImage = ImageIO.read(new File(UPLOADED_FOLDER + FILE_NAME));

        WritableRaster raster = srcImage.getRaster();

        //to avoid multiple indexing in loop
        final int srcWidth = srcImage.getWidth();
        final int srcHeight = srcImage.getHeight();

        //here will be stored pixels after calculating
        double[] rPixels = new double[srcWidth * srcHeight];
        double[] gPixels = new double[srcWidth * srcHeight];
        double[] bPixels = new double[srcWidth * srcHeight];

        int counter = 0;
        for (int i = 0; i < srcWidth; i++) {
            for (int j = 0; j < srcHeight; j++) {
                double[] pixel_4_center = raster.getPixel(i, j, (double[]) null);

                double[] pixel_0 = new double[]{0, 0, 0};
                if (((i - 1) >= 0) && ((j - 1) >= 0))
                    pixel_0 = raster.getPixel(i - 1, j - 1, (double[]) null);

                double[] pixel_1 = new double[]{0, 0, 0};
                if ((j - 1) >= 0)
                    pixel_1 = raster.getPixel(i, j - 1, (double[]) null);

                double[] pixel_2 = new double[]{0, 0, 0};
                if (((i + 1) < srcWidth) && ((j - 1) >= 0))
                    pixel_2 = raster.getPixel(i + 1, j - 1, (double[]) null);

                double[] pixel_3 = new double[]{0, 0, 0};
                if ((i - 1) >= 0)
                    pixel_3 = raster.getPixel(i - 1, j, (double[]) null);

                double[] pixel_5 = new double[]{0, 0, 0};
                if ((i + 1) < srcWidth)
                    pixel_5 = raster.getPixel(i + 1, j, (double[]) null);

                double[] pixel_6 = new double[]{0, 0, 0};
                if (((i - 1) >= 0) && ((j + 1) < srcHeight))
                    pixel_6 = raster.getPixel(i - 1, j + 1, (double[]) null);

                double[] pixel_7 = new double[]{0, 0, 0};
                if ((j + 1) < srcHeight)
                    pixel_7 = raster.getPixel(i, j + 1, (double[]) null);

                double[] pixel_8 = new double[]{0, 0, 0};
                if (((i + 1) < srcWidth) && ((j + 1) < srcHeight))
                    pixel_8 = raster.getPixel(i + 1, j + 1, (double[]) null);


                rPixels[counter] = (pixel_0[0] * filter[0] + pixel_1[0] * filter[1] + pixel_2[0] * filter[2] +
                        pixel_3[0] * filter[3] + pixel_4_center[0] * filter[4] + pixel_5[0] * filter[5] +
                        pixel_6[0] * filter[6] + pixel_7[0] * filter[7] + pixel_8[0] * filter[8]) / 6;

                gPixels[counter] = (pixel_0[1] * filter[0] + pixel_1[1] * filter[1] + pixel_2[1] * filter[2] +
                        pixel_3[1] * filter[3] + pixel_4_center[1] * filter[4] + pixel_5[1] * filter[5] +
                        pixel_6[1] * filter[6] + pixel_7[1] * filter[7] + pixel_8[1] * filter[8]) / 6;

                bPixels[counter] = (pixel_0[2] * filter[0] + pixel_1[2] * filter[1] + pixel_2[2] * filter[2] +
                        pixel_3[2] * filter[3] + pixel_4_center[2] * filter[4] + pixel_5[2] * filter[5] +
                        pixel_6[2] * filter[6] + pixel_7[2] * filter[7] + pixel_8[2] * filter[8]) / 6;

                counter++;
            }
        }

        BufferedImage destImage = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_RGB);

        int[] rgb = new int[rPixels.length];
        for (int i = 0; i < rPixels.length; i++) {
            rgb[i] = (int) (rPixels[i] + gPixels[i] + bPixels[i]) / 3;
            if (rgb[i] > 255) rgb[i] = 255;
        }

        counter = 0;
        for (int i = 0; i < destImage.getWidth(); i++) {
            for (int j = 0; j < destImage.getHeight(); j++) {
                Color newColor = new Color(rgb[counter], rgb[counter], rgb[counter]);
                destImage.setRGB(i, j, newColor.getRGB());
                counter++;
            }
        }

        ImageIO.write(destImage, JPG, new File(UPLOADED_FOLDER + NEW_FILE_NAME));

        System.out.println("<<<SUCCESS>>>");
    }
}

