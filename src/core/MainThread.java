package core;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainThread extends JFrame{
	public static int screen_w = 640, screen_h = 480;
	public static JPanel panel;
	public static BufferedImage screenBuffer;
	public static void main(String[] args) {
		new MainThread();
	}
	public MainThread() {
		panel = (JPanel)getContentPane();
		panel.setPreferredSize(new Dimension(screen_w, screen_h));
		panel.setMinimumSize(new Dimension(screen_w, screen_h));
		
		setResizable(false);
		pack();
		setVisible(true);
		
		screenBuffer = new BufferedImage(screen_w, screen_h, BufferedImage.TYPE_3BYTE_BGR);
		
	}
}
