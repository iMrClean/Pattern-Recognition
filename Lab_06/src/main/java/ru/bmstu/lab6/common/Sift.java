package ru.bmstu.lab6.common;

import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.highgui.Highgui;

import java.util.LinkedList;
import java.util.List;

import static ru.bmstu.lab6.Main.FILE_PATH;

public class Sift {

    private static final String OUTPUT_IMAGE = "output/outputImage.jpg";

    private static final String MATCH_OUTPUT = "output/matchOutput.jpg";

    public static void sift(Mat objectImage, Mat sceneImage) {
        System.out.println("Detecting key points...");
        MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
        FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SIFT);
        featureDetector.detect(objectImage, objectKeyPoints);

        System.out.println("Computing descriptors...");
        MatOfKeyPoint objectDescriptors = new MatOfKeyPoint();
        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
        descriptorExtractor.compute(objectImage, objectKeyPoints, objectDescriptors);

        Mat outputImage = new Mat(objectImage.rows(), objectImage.cols(), Highgui.CV_LOAD_IMAGE_COLOR);
        Scalar newKeyPointColor = new Scalar(255, 0, 0);
        Features2d.drawKeypoints(objectImage, objectKeyPoints, outputImage, newKeyPointColor, 0);

        System.out.println("Detecting key points in background image...");
        MatOfKeyPoint sceneKeyPoints = new MatOfKeyPoint();
        featureDetector.detect(sceneImage, sceneKeyPoints);

        System.out.println("Computing descriptors in background image...");
        MatOfKeyPoint sceneDescriptors = new MatOfKeyPoint();
        descriptorExtractor.compute(sceneImage, sceneKeyPoints, sceneDescriptors);

        Mat matchOutput = new Mat(sceneImage.rows() * 2, sceneImage.cols() * 2, Highgui.CV_LOAD_IMAGE_COLOR);
        Scalar matchColor = new Scalar(0, 255, 0);

        System.out.println("Matching object and scene images...");
        List<MatOfDMatch> matches = new LinkedList<>();
        DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        descriptorMatcher.knnMatch(objectDescriptors, sceneDescriptors, matches, 2);

        System.out.println("Calculating good match list...");
        LinkedList<DMatch> goodMatchesList = new LinkedList<>();
        float nndrRatio = 0.7f;

        for (int i = 0; i < matches.size(); i++) {
            MatOfDMatch matofDMatch = matches.get(i);
            DMatch[] dmatcharray = matofDMatch.toArray();
            DMatch m1 = dmatcharray[0];
            DMatch m2 = dmatcharray[1];

            if (m1.distance <= m2.distance * nndrRatio) {
                goodMatchesList.addLast(m1);
            }
        }

        if (goodMatchesList.size() >= 7) {
            MatOfDMatch goodMatches = new MatOfDMatch();
            goodMatches.fromList(goodMatchesList);

            Features2d.drawMatches(objectImage, objectKeyPoints, sceneImage, sceneKeyPoints, goodMatches, matchOutput, matchColor, newKeyPointColor, new MatOfByte(), 2);

            Highgui.imwrite(FILE_PATH + OUTPUT_IMAGE, outputImage);
            Highgui.imwrite(FILE_PATH + MATCH_OUTPUT, matchOutput);
        } else {
            System.out.println("Object Not Found");
        }
    }
}
