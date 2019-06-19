package commons;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static utils.Utils.*;

public class OtsuBinarizeHalftoneOpenCV {

    public void thresholdOpenCV() {
        System.out.println("Start OpenCV");
        try {
            //Загрузка базовой библиотеки OpenCV
            nu.pattern.OpenCV.loadLocally();
            // Чтение изображения из файла и сохранение его в объекте Matrix
            Mat source = Imgcodecs.imread(UPLOADED_FOLDER + FILE_NAME);
            if (source.empty()) {
                System.err.println("Не удалось загрузить изображение");
            }
            // Создание пустой матрицы для хранения результата
            Mat destination = new Mat(source.rows(), source.cols(), CvType.CV_8UC3);
            // Красим в серый destination
            Imgproc.cvtColor(source, destination, Imgproc.COLOR_RGB2GRAY);
            // Применение Threshold на изображении
            Imgproc.threshold(destination, destination, 0, 255, Imgproc.THRESH_OTSU | Imgproc.THRESH_BINARY);
            // Запись изображения
            Imgcodecs.imwrite(UPLOADED_FOLDER + NEW_FILE_NAME, destination);
            System.out.println("End OpenCV");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
