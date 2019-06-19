package commons;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static utils.Utils.*;

public class GaussianBlurOpenCV {

    public void gaussianBlurOpenCV() {
        System.out.println("Start Gaussian BlurOpenCV");
        try {
            //Загрузка базовой библиотеки OpenCV
            nu.pattern.OpenCV.loadLocally();
            // Чтение изображения из файла и сохранение его в объекте Matrix
            Mat source = Imgcodecs.imread(UPLOADED_FOLDER + FILE_NAME);
            if (source.empty()) {
                System.err.println("Не удалось загрузить изображение");
            }
            // Создание пустой матрицы для хранения результата
            Mat destination = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
            // Красим в серый destination
            Imgproc.cvtColor(source, destination, Imgproc.COLOR_RGB2GRAY);
            // Применение GaussianBlur на изображении
            Imgproc.GaussianBlur(destination, destination, new Size(11, 11), 0, 0);
            // Запись изображения
            Imgcodecs.imwrite(UPLOADED_FOLDER + NEW_FILE_NAME, destination);
            System.out.println("End Gaussian BlurOpenCV");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
