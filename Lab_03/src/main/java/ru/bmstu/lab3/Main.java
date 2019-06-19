package ru.bmstu.lab3;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.bmstu.lab3.common.Morphology;

/**
 * Лабораторная работа №3
 * <p>
 * Используя эрозию и дилатацию из opencv сделать
 * <p>
 * замыкание (закрытие)
 * размыкание (открытие)
 * условная дилатация
 * морфологический скелет
 */
public class Main {

    private static final String DIRECTORY = "src/main/resources/";

    private static final String FILE_NAME = "images/pic.png";
    private static final String FILE_NAME_2 = "images/pic_closing.png";
    private static final String FILE_NAME_3 = "images/pic_opening.png";

    private static final String ERODE_NAME = "output/erode.png";
    private static final String DILATE_NAME = "output/dilate.png";
    private static final String OPENING_NAME = "output/opening.png";
    private static final String CLOSING_NAME = "output/closing.png";
    private static final String CONDITIONAL_DILATION_NAME = "output/conditionalDilation.png";
    private static final String SKELETON_NAME = "output/skeleton.png";

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public static void main(String[] args) {
        Mat source = Imgcodecs.imread(DIRECTORY + FILE_NAME);
        Mat sourceClosing = Imgcodecs.imread(DIRECTORY + FILE_NAME_2);
        Mat sourceOpening = Imgcodecs.imread(DIRECTORY + FILE_NAME_3);
        Mat sourceText = Imgcodecs.imread(DIRECTORY + FILE_NAME);

        Mat erode = Morphology.erode(source);
        Imgcodecs.imwrite(DIRECTORY + ERODE_NAME, erode);

        Mat dilate = Morphology.dilate(source);
        Imgcodecs.imwrite(DIRECTORY + DILATE_NAME, dilate);

        Mat closing = Morphology.closing(sourceClosing);
        Imgcodecs.imwrite(DIRECTORY + CLOSING_NAME, closing);

        Mat opening = Morphology.opening(sourceOpening);
        Imgcodecs.imwrite(DIRECTORY + OPENING_NAME, opening);

        Mat conditionalDilation = Morphology.conditionalDilate(sourceText);
        Imgcodecs.imwrite(DIRECTORY + CONDITIONAL_DILATION_NAME, conditionalDilation);

        Mat skeleton = Morphology.skeleton(source);
        Imgcodecs.imwrite(DIRECTORY + SKELETON_NAME, skeleton);
    }
}
