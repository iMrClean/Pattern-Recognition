import commons.GaussianBlur;
import commons.GaussianBlurOpenCV;
import commons.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static utils.Utils.*;

/**
 * Лабораторная работа №1.
 * <p>
 * Реализовать свертку методом Гаусса.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Input '1' To openCV gaussianBlur or '2' own realization");
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        switch (value) {
            case 1:
                new GaussianBlurOpenCV().gaussianBlurOpenCV();
                break;
            case 2:
                gaussianBlur();
                break;
            case 3:
                new Test().start();
                break;
            default:
                System.err.println("Incorrect input");
        }
    }

    private static void gaussianBlur() {
        double[][] weights = GaussianBlur.getInstance().generateWeightMatrix(50, Math.sqrt(150));

        BufferedImage destination = null;
        try {
            BufferedImage source = ImageIO.read(new File(UPLOADED_FOLDER + FILE_NAME));
            System.out.println("Start create...");
            destination = GaussianBlur.getInstance().createGaussianImage(source, weights, 150);
            System.out.println("End create...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Start write...");
            ImageIO.write(Objects.requireNonNull(destination), PNG, new File(UPLOADED_FOLDER + MY_FILE_NAME));
            System.out.println("End write...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DONE!");
    }
}