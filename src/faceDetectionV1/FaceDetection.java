/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faceDetectionV1;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


/**
 *
 * @author kiddabove
 */
public class FaceDetection {
    
    private CascadeClassifier cascadeClassifier;
    
    public FaceDetection(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.cascadeClassifier = new CascadeClassifier(Constants.CASCADE_CLASSIFIER);
    }
    public void detectFaces(File file,ImagePanel imagePanel){
        Mat image = Imgcodecs.imread(file.getAbsolutePath(),Imgcodecs.CV_LOAD_IMAGE_COLOR);
        
        MatOfRect facedetections = new MatOfRect();
        cascadeClassifier.detectMultiScale(image, facedetections);
        
        for(Rect rect: facedetections.toArray()){
            Imgproc.rectangle(image,new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height), new Scalar(100,100,250),10);
        }
        
        BufferedImage bufferedImage = convertMatToImage(image);
        imagePanel.updateImage(bufferedImage);
        
        
    }

    private BufferedImage convertMatToImage(Mat matrix) {
        int type= BufferedImage.TYPE_BYTE_GRAY;
        
        if(matrix.channels() >1){
            type=BufferedImage.TYPE_3BYTE_BGR;
        }
        
        int bufferSize = matrix.channels()*matrix.cols()*matrix.rows();
        byte bytes[] = new byte[bufferSize];
        matrix.get(0, 0, bytes);
        BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(),type);
        final byte targetsize[] = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        System.arraycopy(bytes, 0, targetsize, 0, bytes.length);
        return image;
    }
    
}
