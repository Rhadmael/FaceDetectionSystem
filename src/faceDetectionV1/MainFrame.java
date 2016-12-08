/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faceDetectionV1;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
/**
 *
 * @author kiddabove
 */
public class MainFrame extends JFrame{
    FaceDetection FD = new FaceDetection();
    private static final long serialVersionUID = 1L;
/********************Static Content Declarations********************/
    private ImagePanel imagepanel;
    private JFileChooser fileChooser;
    private FaceDetection faceDetection;
    private File file;
/********************Dynamic Content Declarations********************/
    
    
    
    public MainFrame() {
        super(Constants.APPLICATION_NAME);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        setJMenuBar(createMenuBar());
//Static Content Initializers
        this.imagepanel = new ImagePanel();
        this.fileChooser = new JFileChooser();
        this.faceDetection = new FaceDetection();


        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(this);

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu StaticFeedMenu = new JMenu("Static Feed");
            JMenuItem loadMenuItem = new JMenuItem("Load Image");
            JMenuItem DetectMenuItem = new JMenuItem("Detect Faces");
            JMenuItem DetectPersonMenuItem = new JMenuItem("Detect People");

        
            StaticFeedMenu.add(loadMenuItem);
            StaticFeedMenu.add(DetectMenuItem);
            StaticFeedMenu.add(DetectPersonMenuItem);
        
        
        //Add actionListeners to menu items
        
        loadMenuItem.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                add(imagepanel, BorderLayout.CENTER);
                if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){  
                    MainFrame.this.file = fileChooser.getSelectedFile();
                    System.out.println("Accessing File "+MainFrame.this.file);
                    MainFrame.this.imagepanel.loadImage(MainFrame.this.file);
                }
            }
    });
    
        DetectMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               MainFrame.this.faceDetection.detectFaces(MainFrame.this.file, MainFrame.this.imagepanel);
               System.out.println(FD.getNumberOfFaces()+" Faces Detected");
                System.out.println("Writing File Out "+MainFrame.this.file);
            }
    
        
    });
        
        JMenu DynamicFeedMenu = new JMenu("Dynamic Feed");
            JMenuItem StartCamera = new JMenuItem ("Start Video Capture");
            JMenuItem CensorEyes = new JMenuItem("Censor Eyes");
            
            StartCamera.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                
            }
    
    });
            
            DynamicFeedMenu.add(StartCamera);
            DynamicFeedMenu.add(CensorEyes);
            
        JMenu AboutMenu = new JMenu("About");
        JMenu HelpMenu = new JMenu("Help");
        
        JMenu ControlsMenu = new JMenu("Controls");
            JMenuItem ExitMenuItem = new JMenuItem("Exit");
            ControlsMenu.add(ExitMenuItem);
        
        menuBar.add(StaticFeedMenu);
        menuBar.add(DynamicFeedMenu);
        menuBar.add(AboutMenu);
        menuBar.add(ControlsMenu);
        
        ExitMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainFrame.this,Constants.EXIT_REQUEST);
                
                if(action == JOptionPane.OK_OPTION){
                    System.gc();
                    System.exit(0);
                }
            }
        });
        return menuBar;
    }
    

    /********************Dynamic Content********************/
    
}
