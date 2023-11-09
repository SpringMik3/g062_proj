

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class bdImgServer extends Thread {
		
	private JLabel borderserver;
	
	public bdImgServer(JLabel borderserver) {
		this.borderserver = borderserver;
	}
	
	@Override
	public void run() {
		while (true) {
			borderserver.setBorder((Border) new LineBorder(Color.pink));
			try {
				Thread.sleep(500);
				borderserver.setBorder(new LineBorder(Color.BLACK));
				Thread.sleep(500);
			} catch (Exception ex) {
			}
		}
	}
}
