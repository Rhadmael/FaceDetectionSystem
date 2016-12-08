/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faceDetectionV1;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author kiddabove
 */
class Detector {
    private CascadeClassifier cascadeClassifier;
    private MatOfRect detectedFaces;
    private Mat coloredImage;
    private Mat grayImage;
    
    public Detector(){
        this.cascadeClassifier = new CascadeClassifier(Constants.CASCADE_CLASSIFIER);
        this.detectedFaces = new MatOfRect();
        this.coloredImage = new Mat();
        this.grayImage = new Mat();
    }
   
    public Mat detect(Mat InputwebCamImage) {
           InputwebCamImage.copyTo(grayImage);//grayImage is a deepCopy of InputwebCamImage(rows ,cools and pixelData)
           InputwebCamImage.copyTo(coloredImage);
           
           Imgproc.cvtColor(coloredImage, grayImage, Imgproc.COLOR_BGR2GRAY);
           //Equalize Histogram of gray image
           Imgproc.equalizeHist(grayImage, grayImage);
           
           cascadeClassifier.detectMultiScale(grayImage, detectedFaces);
           
           ShowFacesOnScreen();
           
           return coloredImage;
    }

    private void ShowFacesOnScreen() {
        for(Rect rect:detectedFaces.toArray()){
            Imgproc.rectangle(coloredImage, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height), new Scalar(0,999,0),2);
        }
    }
    
}
