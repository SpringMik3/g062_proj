

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Base64Image {
	public static File ImageSelect() {
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch(Exception ex) {
	        ex.printStackTrace();
	    }
		JFileChooser j = new JFileChooser(); 
		j.setAcceptAllFileFilterUsed(false); 
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images (*.png, *.jpg)", "jpg", "png");
		j.addChoosableFileFilter(filter);
		int result = j.showDialog(null, "Select Image");
		if (result == JFileChooser.APPROVE_OPTION) { 
			return new File(j.getSelectedFile().getAbsolutePath());
		} else return null;
	}
	public static ImageIcon decoder(String base64Image) {
	    byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
	    ImageIcon a = new ImageIcon(imageByteArray);
	    return a;
	}
	public static String encoder(ImageIcon imgicon) {
		String base64Image = "";
		BufferedImage bi = getBufferedImage(imgicon.getImage());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "png", baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] byteArray= baos.toByteArray();
	    base64Image = Base64.getEncoder().encodeToString(byteArray);

		return base64Image;
	}
	
	public static String ConvertImageIconToBase64String(ImageIcon ii) {
		 BufferedImage image = new BufferedImage(ii.getIconWidth(),
		 ii.getIconHeight(), BufferedImage.TYPE_INT_RGB);

		 Graphics g = image.createGraphics();

		 ii.paintIcon(null, g, 0, 0);
		 g.dispose();

		 ByteArrayOutputStream b = new ByteArrayOutputStream();
		 try {
		 ImageIO.write(image, "jpg", b);
		 } catch (Exception ex) {
		 }
		 byte[] imageInByte = b.toByteArray();

		 return Base64.getEncoder().encodeToString(imageInByte);
	}
	
	
	public static BufferedImage getBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	       return (BufferedImage) img;
	    }

	    BufferedImage bimage = new BufferedImage(img.getWidth(null), 
	                    img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}

