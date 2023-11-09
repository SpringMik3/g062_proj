

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class bdImgClient extends Thread {
		
	private JLabel borderclient;
	
	public bdImgClient(JLabel borderclient) {
		this.borderclient = borderclient;
	}
	
	@Override
	public void run() {
		while (true) {
			borderclient.setBorder((Border) new LineBorder(Color.BLUE));
			try {
				Thread.sleep(500);
				borderclient.setBorder(new LineBorder(Color.BLACK));
				Thread.sleep(500);
			} catch (Exception ex) {
			}
		}
	}
}
