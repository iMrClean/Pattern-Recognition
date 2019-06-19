package ru.bmstu.lab3.common;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Morphology {

    private static Size KERNEL_SIZE = new Size(3, 3);

    public static Mat erode(Mat source) {
        Mat dest = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
        Imgproc.cvtColor(source, dest, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(dest, dest, 0, 255, Imgproc.THRESH_OTSU);
        Mat mat = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, KERNEL_SIZE);
        Imgproc.erode(dest, dest, mat);
        return dest;
    }

    public static Mat dilate(Mat source) {
        Mat dest = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
        Imgproc.cvtColor(source, dest, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(dest, dest, 0, 255, Imgproc.THRESH_OTSU);
        Mat mat = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, KERNEL_SIZE);
        Imgproc.dilate(dest, dest, mat);
        return dest;
    }

    public static Mat closing(Mat source) {
        Mat dest = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
        Imgproc.cvtColor(source, dest, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(dest, dest, 0, 255, Imgproc.THRESH_OTSU);
        Mat mat = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, KERNEL_SIZE);
        Imgproc.dilate(dest, dest, mat);
        Imgproc.erode(dest, dest, mat);
        return dest;
    }

    public static Mat opening(Mat source) {
        Mat dest = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
        Imgproc.cvtColor(source, dest, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(dest, dest, 0, 255, Imgproc.THRESH_OTSU);
        Mat mat = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, KERNEL_SIZE);
        Imgproc.erode(dest, dest, mat);
        Imgproc.dilate(dest, dest, mat);
        return dest;
    }

    public static Mat conditionalDilate(Mat source) {
        Mat img = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
        Mat dest = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
        Imgproc.cvtColor(source, dest, Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(source, img, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(dest, dest, 0, 255, Imgproc.THRESH_OTSU);
        Imgproc.threshold(img, img, 0, 255, Imgproc.THRESH_OTSU);

        Mat kernelErode = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, KERNEL_SIZE);
        Mat kernelDilate = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, KERNEL_SIZE);
        Imgproc.erode(dest, dest, kernelErode);

        int tempZeros = -1;
        int size = source.cols() * source.rows();
        boolean done = false;

        while (!done) {
            Imgproc.dilate(dest, dest, kernelDilate);
            Core.bitwise_and(dest, img, dest);
            int zeros = size - Core.countNonZero(dest);
            if (zeros == tempZeros) {
                done = true;
            } else {
                tempZeros = zeros;
            }
        }

        return dest;
    }

    public static Mat skeleton(Mat source) {
        Mat img = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
        Mat dest = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
        Imgproc.cvtColor(source, img, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(img, img, 0, 255, Imgproc.THRESH_BINARY);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, KERNEL_SIZE);

        int size = source.cols() * source.rows();
        boolean done = false;
        while (!done) {
            Mat eroded = new Mat();
            Mat temp = new Mat();
            Imgproc.erode(img, eroded, kernel);
            Imgproc.dilate(eroded, temp, kernel);
            Core.subtract(img, temp, temp);
            Core.bitwise_or(dest, temp, dest);
            img = eroded.clone();

            int zeros = size - Core.countNonZero(img);
            if (zeros == size) {
                done = true;
            }
        }

        return dest;
    }
}
