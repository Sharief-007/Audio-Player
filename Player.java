import static javax.swing.JFileChooser.APPROVE_OPTION;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Player extends Time_Calculator  implements ActionListener {

    JFrame jf;
    JButton b1, b2, b3, folder, settings;
    JFileChooser choose;
    JSlider slider;
    JLabel l1,l2;

    ImageIcon play = new ImageIcon("play.png");
    ImageIcon pause = new ImageIcon("pause.png");
    ImageIcon forward = new ImageIcon("forward.png");
    ImageIcon backward = new ImageIcon("backward.png");
    ImageIcon folders = new ImageIcon("folders.png");
    ImageIcon set = new ImageIcon("settings.png");

    Clip clip;
    AudioInputStream audio;

    boolean status =false;
    

    public static void main(String[] args) {
        Player player = new Player();

    }

    public Player() {
        jf = new JFrame("Audio Player");
        jf.setSize(550, 300);
        jf.getContentPane().setBackground(Color.white);
        jf.setLayout(null);

        addElements();
        jf.add(b1);
        jf.add(b2);
        jf.add(b3);
        jf.add(slider);
        jf.add(folder);
        jf.add(l1);
        jf.add(l2);

        jf.setIconImage(new ImageIcon("Logo.png").getImage());
        jf.setDefaultCloseOperation(3);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);
        jf.setVisible(true);
    }

    public void addElements() {

        b1 = new JButton(play);
        b1.setBounds(200, 120, 125, 125);
        b1.setBackground(Color.white);
        b1.setBorder(null);
        b1.addActionListener(this);

        b2 = new JButton(forward);
        b2.setBounds(375, 155, 75, 75);
        b2.setBackground(Color.white);
        b2.setBorder(null);
        b2.addActionListener(this);

        b3 = new JButton(backward);
        b3.setBounds(75, 155, 75, 75);
        b3.setBackground(Color.white);
        b3.setBorder(null);
        b3.addActionListener(this);

        slider = new JSlider();
        slider.setBounds(10, 80, 520, 25);
        slider.setBackground(Color.white);
        slider.setPaintTrack(true);
        slider.setBorder(null);
        slider.setMinimum(0);
        slider.setValue(0);

        folder = new JButton(folders);
        folder.setBounds(30, 20, 30, 30);
        folder.setBackground(Color.white);
        folder.setBorder(null);
        folder.addActionListener(this);

        l1=new JLabel("00 : 00",JLabel.CENTER);
        l1.setBounds(10,105,50,20);
        l1.setOpaque(true);
        l1.setBackground(Color.white);
        l1.setForeground(Color.black);


        l2=new JLabel("00 : 00",JLabel.CENTER);
        l2.setBounds(470,105,50,20);
        l2.setOpaque(true);
        l2.setBackground(Color.white);
        l2.setForeground(Color.black);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(folder)) {
            choose = new JFileChooser("C:\\Users\\Sharief\\Music");
            //choose.setAcceptAllFileFilterUsed(false);
            choose.addChoosableFileFilter(new FileNameExtensionFilter("au","wav","wav"));
            choose.setBackground(Color.WHITE);
            choose.setForeground(Color.WHITE);
            int i = choose.showOpenDialog(jf);

            if (i == APPROVE_OPTION)
            {
                File file = choose.getSelectedFile();
                if (file.getName().endsWith(".wav") || file.getName().endsWith(".au") || file.getName().endsWith(".aiff")) {
                    try 
                    {
                        audio = AudioSystem.getAudioInputStream(file);
                        clip = AudioSystem.getClip();
                        clip.open(audio);
                        clip.start();




                        status=true;
                        b1.setIcon(pause);
                        l2.setText(super.calculate_time(clip.getMicrosecondLength()));
                        
                        slider.setMaximum((int)super.MicroSeconds_2_seconds(clip.getMicrosecondLength())+1);
                        super.StartTimer(slider,l1,(int)super.MicroSeconds_2_seconds(clip.getMicrosecondLength())+1);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(jf, ex.getMessage(), "Error", 0);
                    }
                 }
                 else
                 {
                    JOptionPane.showMessageDialog(jf, "Please select '.wav' or '.au' files.", "Alert", 1);
                 }

            }   
        }
        else if(e.getSource().equals(b1))
        {
            if(clip==null)
            {
                //
            }
            else
            {
                if(status)
                {
                    status =false;
                    clip.stop();
                    super.stopTimer();
                    b1.setIcon(play);
                }
                else
                {
                    status = true;
                    clip.start();
                    super.StartTimer(slider, l1,(int)super.MicroSeconds_2_seconds(clip.getMicrosecondLength())+1);
                    b1.setIcon(pause);
                }
            }
        }
        else if(e.getSource().equals(b2))
        {
            if(clip==null)
            {

            }
            else if(clip.isRunning())
            {
                if(clip.getMicrosecondPosition()<clip.getMicrosecondLength()-10000000)
                {
                    long position = clip.getMicrosecondPosition();
                    clip.stop();
                    super.stopTimer();
                    clip.setMicrosecondPosition(position+10000000);
                    seconds+=10;
                    super.StartTimer(slider, l1,(int)super.MicroSeconds_2_seconds(clip.getMicrosecondLength())+1);
                    clip.start();
                }
            }
            else
            {
                long position = clip.getMicrosecondPosition();
                clip.setMicrosecondPosition(position+10000000);
                seconds+=10;

            }
        }
        else if(e.getSource().equals(b3))
        {
            if(clip==null)
            {

            }
            else if(clip.isRunning())
            {
                if(clip.getMicrosecondPosition()>10000000)
                {
                    long position = clip.getMicrosecondPosition();
                    clip.stop();
                    super.stopTimer();
                    clip.setMicrosecondPosition(position-10000000);
                    
                    seconds-=10;
                    super.StartTimer(slider, l1,(int)super.MicroSeconds_2_seconds(clip.getMicrosecondLength())+1);
                    clip.start();
                }
                else
                {
                    clip.stop();
                    super.stopTimer();
                    clip.setMicrosecondPosition(0l);
                    
                    seconds=0;
                    super.StartTimer(slider, l1,(int)super.MicroSeconds_2_seconds(clip.getMicrosecondLength())+1);
                    clip.start();
                }
            }
            else 
            {
                if(clip.getMicrosecondPosition()>10000000)
                {
                    clip.setMicrosecondPosition(clip.getMicrosecondLength()-10000000);
                    seconds-=10;
                }
                else
                {
                    clip.setMicrosecondPosition(0l);
                    seconds=0;
                }
            }
        }
    }
}