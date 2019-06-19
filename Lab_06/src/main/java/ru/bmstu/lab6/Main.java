package ru.bmstu.lab6;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import static ru.bmstu.lab6.common.Sift.sift;

public class Main {

    public static final String FILE_PATH = "src/main/resources/";

    private static final String BOOK_OBJECT = "images/bookobject.jpg";

    private static final String BOOK_SCENE = "images/bookscene.jpg";

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.out.println("Started....");
        System.out.println("Loading images...");

        Mat objectImage = Highgui.imread(FILE_PATH + BOOK_OBJECT, Highgui.CV_LOAD_IMAGE_COLOR);
        Mat sceneImage = Highgui.imread(FILE_PATH + BOOK_SCENE, Highgui.CV_LOAD_IMAGE_COLOR);
        sift(objectImage, sceneImage);
    }
}
