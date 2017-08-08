package main;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class JStegoLSB {
	public static void main(String[] args) throws IOException{
		EventQueue.invokeLater(()->{
			try {
				JFrame frame = new FrameForJStego();
			} catch (IOException e) {
				
			}
		});
	}
}
