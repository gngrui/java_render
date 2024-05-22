package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainThread extends JFrame {
	public static int screen_h = 480, screen_w = 640;
	public static JPanel panel;
	public static BufferedImage screen_buffer;
	public static int[] screen;

	public static int frameIndex;
	public static int frameInterval = 33;
	public static int sleepTime;
	
	public static int framePerSecond;
	public static long lastDraw;
	public static double thisTime, lastTime;
	public static void main(String[] args) {
		new MainThread();
	}

	public MainThread() {
		panel = (JPanel) this.getContentPane();
		panel.setPreferredSize(new Dimension(screen_w, screen_h));
		pack();
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		screen_buffer = new BufferedImage(screen_w, screen_h, BufferedImage.TYPE_INT_RGB);
		DataBuffer dest = screen_buffer.getRaster().getDataBuffer();
		screen = ((DataBufferInt) dest).getData();

		while (true) {
			int r_skyblue = 0, g_skyblue = 255, b_skyblue = 0;
			int r_red = 255, g_red = 0, b_red = 0;
			for (int i = 0; i < screen_h; ++i) {
				for (int j = 0; j < screen_w; ++j) {
					int r_blue = 163, g_blue = 216, b_blue = 239;
					int r_orange = 255, g_orange = 128, b_orange = 0;
					int skyblue = (r_skyblue << 16) | (g_skyblue << 8) | b_skyblue;
					int red = (r_red << 16) | (g_red << 8) | b_red;
					int half_screen_w = screen_w / 2;
					int half_screen_h = screen_h / 2;
					int v = i-half_screen_h;
					int h = j-half_screen_w;
					float r1 = (float)(v*v+h*h)/(half_screen_w*half_screen_w+half_screen_h*half_screen_h);
					float r2 = 1f - r1;
					int r = (int)(r_blue*r1+r_orange*r2);
					int g = (int)(g_blue*r1+g_orange*r2);
					int b = (int)(b_blue*r1+b_orange*r2);

					screen[i * screen_w + j] = (r<<16)|(g<<8)|b;
				}
			}
			frameIndex++;
			if (frameIndex % 30 == 0) {
				double thisTime=System.currentTimeMillis();
				framePerSecond=(int)(1000/((thisTime-lastTime)/30));
				lastTime=thisTime;
			}
			sleepTime=0;
			while (System.currentTimeMillis()-lastDraw<frameInterval) {
				try {
					Thread.sleep(1);
					sleepTime++;
				} catch(InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			Graphics2D g2=(Graphics2D)screen_buffer.getGraphics();
			g2.setColor(Color.BLACK);
			g2.drawString("FPS: " + framePerSecond + " ms " + "Thread sleep: " + sleepTime + " ms ", 5, 15);
			lastDraw=System.currentTimeMillis();
			panel.getGraphics().drawImage(screen_buffer, 0, 0, this);
		}
	}
}
