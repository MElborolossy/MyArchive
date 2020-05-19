package guiUtils;

import javax.swing.*;
import java.awt.*;

public class ImageLayeredPane extends JLayeredPane{
	private final StretchIcon img;

	public ImageLayeredPane (StretchIcon img) {
		this.img = img;
		setLayout(null);
	}
	
	public void paintComponent (Graphics g) {
		img.paintIcon(this, g, 0, 0);
	}
}
