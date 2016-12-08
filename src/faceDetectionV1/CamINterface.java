/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faceDetectionV1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author kiddabove
 */
public class CamINterface extends JFrame{
    private static final long serialVersionUID = 1L;
    private Detector processor;
    private CameraPanel cameraPanel; 
    
    public CamINterface(){
        
        super(Constants.APPLICATION_NAME);
        setJMenuBar(createMenuBar());
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.cameraPanel = new CameraPanel();
        this.processor = new Detector();
        
        setContentPane(cameraPanel);
        
        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        
    }
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu FileMenu = new JMenu("File");
            JMenuItem StratRecord = new JMenuItem("Start Recording");
            JMenuItem pause = new JMenuItem("Pause");
            JMenuItem Resume = new JMenuItem("Resume");
            JMenuItem Finish = new JMenuItem("Finish");

        
            FileMenu.add(StratRecord);
            FileMenu.add(pause);
            FileMenu.add(Resume);
            FileMenu.add(Finish);
            
            menuBar.add(FileMenu);
       
       JMenu ControlsMenu = new JMenu("Controls");
       JMenuItem Exit = new JMenuItem("Exit");
            
            ControlsMenu.add(Exit);
            
            menuBar.add(ControlsMenu);
            
            Exit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               int action = JOptionPane.showConfirmDialog(CamINterface.this, "Do You Really Want to Quit ?");
               
               if (action == JOptionPane.OK_OPTION){
                   System.gc();
                    System.exit(0);
               }
            }
            
            });
        return menuBar;
    }
    public void DisplayScreen(){
        Mat webCamImage = new Mat();
        VideoCapture WebCamVideo = new VideoCapture(0);
        
        
        if(WebCamVideo.isOpened()){
            while(true){
                WebCamVideo.read(webCamImage);
                
                if(!webCamImage.empty()){
                    setSize(webCamImage.width()+50 , webCamImage.height()+70);
                    webCamImage = processor.detect(webCamImage);
                    cameraPanel.convertMatToImage(webCamImage);
                    cameraPanel.repaint();
                }
                else{
                    System.out.println("Error in detecting Camera");
                    break;
                }
            }
        }
    }
}
