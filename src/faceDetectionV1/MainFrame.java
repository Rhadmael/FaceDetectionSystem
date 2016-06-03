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
/**
 *
 * @author kiddabove
 */
public class MainFrame extends JFrame{

    private static final long serialVersionUID = 1L;
    private ImagePanel imagepanel;
    private JFileChooser fileChooser;
    private FaceDetection faceDetection;
    private File file;
    public MainFrame() {
        super(Constants.APPLICATION_NAME);
        
        setJMenuBar(createMenuBar());
        
        this.imagepanel = new ImagePanel();
        this.fileChooser = new JFileChooser();
        this.faceDetection = new FaceDetection();
        
        add(imagepanel, BorderLayout.CENTER);
        
        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load Image");
        JMenuItem DetectMenuItem = new JMenuItem("Detect Faces");
        JMenuItem DetectPersonMenuItem = new JMenuItem("Detect People");
        JMenuItem ExitMenuItem = new JMenuItem("Exit");
        
        fileMenu.add(loadMenuItem);
        fileMenu.add(DetectMenuItem);
        fileMenu.add(DetectPersonMenuItem);
        fileMenu.add(ExitMenuItem);
        
        //Add actionListeners to menu items
        
        loadMenuItem.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){  
                    MainFrame.this.file = fileChooser.getSelectedFile();
                    System.out.println(MainFrame.this.file);
                    MainFrame.this.imagepanel.loadImage(MainFrame.this.file);
                }
            }
    });
    
        DetectMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               MainFrame.this.faceDetection.detectFaces(MainFrame.this.file, MainFrame.this.imagepanel);
            }
    
        
    });
        JMenu AboutMenu = new JMenu("About");
        JMenu HelpMenu = new JMenu("Help");
        
        menuBar.add(fileMenu);
        menuBar.add(AboutMenu);
        menuBar.add(HelpMenu);
        
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
    
}
