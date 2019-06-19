import commons.BinarizeHalftone;
import commons.BradleyBinarizeHalftone;
import commons.OtsuBinarizeHalftone;
import commons.OtsuBinarizeHalftoneOpenCV;
import utils.Utils;

import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * Лабораторная работа №2.
 * <p>
 * Реализовать бинаризацию полутонового изображения:
 * <p>
 * 1) с ручным выбором порога(threshold).
 * 2) с выбором порога методом Оцу.
 * а) с помощью OpenCV.
 * б) вручную.
 * 3) методом Брэдли - вручную.
 * При демонстрации работы - использовать фотографию текста с сильно неравномерным
 * освещением.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        BufferedImage destination = null;
        BufferedImage image = Utils.readImageFromResources();
        switch (value) {
            case 1:
                destination = new BinarizeHalftone().binarize(image, 90);
                break;
            case 2:
                destination = new BinarizeHalftone().binarize2(image, 40);
                break;
            case 3:
                destination = new OtsuBinarizeHalftone().otsuBinarize(image);
                break;
            case 4:
                new OtsuBinarizeHalftoneOpenCV().thresholdOpenCV();
                break;
            case 5:
                destination = new BradleyBinarizeHalftone().bradleyBinarize(image, image.getWidth() / 8, 1);
                break;
        }
        if (destination != null) {
            Utils.writeImageToResources(destination);
        }
    }

}
