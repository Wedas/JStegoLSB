/**
 * @author <a href="mailto:vapenn@ya.ru">Vape in NN</a> 
 */
package src;

import java.awt.EventQueue;
import java.util.ArrayList;
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
