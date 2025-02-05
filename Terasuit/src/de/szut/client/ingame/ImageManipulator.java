package de.szut.client.ingame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * 
 * @author Alexander
 *
 */
public class ImageManipulator {

	int xSkalierung = 0;
	int ySkalierung = 0;
	static int Colorbrightness = 100;

	/**
	 * Gibt die X-Skalierung zur�ck
	 * 
	 * @return
	 */
	public int getxSkalierung() {
		return xSkalierung;
	}

	/**
	 * Setzt die X-Skalierung
	 * 
	 * @param xSkalierung
	 */
	public void setxSkalierung(int xSkalierung) {
		this.xSkalierung = xSkalierung;
	}

	/**
	 * Gibt die Y-Skalierung zur�ck
	 * 
	 * @return
	 */
	public int getySkalierung() {
		return ySkalierung;
	}

	/**
	 * Setzt die Y-Skalierung
	 * 
	 * @param ySkalierung
	 */
	public void setySkalierung(int ySkalierung) {
		this.ySkalierung = ySkalierung;
	}

	/**
	 * Ersetzt die RGB Farbe 255,0,255 durch einer der 4 Spielfarben
	 * 
	 * @param img
	 * @param color
	 * @return BufferedImage
	 */
	public BufferedImage setnewColors(BufferedImage img, int color) {
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color imageColor = new Color(img.getRGB(x, y));
				if (imageColor.getBlue() == 255 && imageColor.getGreen() == 0
						&& imageColor.getBlue() == 255) {
					// Blau
					if (color == 1) {
						imageColor = new Color(0, 0, 255);
						// Rot
					} else if (color == 2) {
						imageColor = new Color(255, 0, 0);
						// gelb
					} else if (color == 3) {
						imageColor = new Color(255, 255, 0);
						// Gr�n
					} else if (color == 4) {
						imageColor = new Color(0, 255, 0);
					}
					img.setRGB(x, y, imageColor.getRGB());
				}
			}
		}
		return img;
	}

	/**
	 * Sucht sich die Dimension f�r die Einheit raus und skaliert sie neu und
	 * gibt es wieder zur�ck als Image
	 * 
	 * @param img
	 * @param Entitytype
	 * @return Image
	 */
	public Image setnewDimension(BufferedImage img, String Entitytype) {
		this.getUnitDeminsionSize(Entitytype);
		return img.getScaledInstance(getxSkalierung(), getySkalierung(),
				Image.SCALE_AREA_AVERAGING);
	}

	/**
	 * Iteriert �ber ein Bild und erh�ht alle RGB-Werte
	 * 
	 * @param img
	 * @return
	 */
	public BufferedImage setSelection(BufferedImage img) {
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color imageColor = new Color(img.getRGB(x, y));
				if ((img.getRGB(x, y) >> 24) == 0x00) {
				} else {
					imageColor = new Color(
							newSelectionColor(imageColor.getRed()),
							newSelectionColor(imageColor.getGreen()),
							newSelectionColor(imageColor.getBlue()));
					img.setRGB(x, y, imageColor.getRGB());
				}
			}
		}
		return img;
	}

	/**
	 * Ermittlung der neuen Farbe
	 * 
	 * @param Color
	 * @return
	 */
	private int newSelectionColor(int Color) {
		Color = Color + Colorbrightness;
		if (Color >= 256) {
			Color = 255;
		}
		return Color;
	}

	/**
	 * Giebt die Skalierungsgr��e zur�ck, diese wird an Hand des Namens
	 * ermittelt.
	 * 
	 * @return int
	 */
	public void getUnitDeminsionSize(String Entitytype) {
		switch (Entitytype) {
		// GROUND UNITS
		case "Unit/Ground/Marine.png":
			setxSkalierung(15);
			setySkalierung(38);
			return;
		case "Unit/Ground/Chronite Tank.png":
			setxSkalierung(65);
			setySkalierung(27);
			return;
		case "Unit/Ground/Gr�ditz.png":
			setxSkalierung(40);
			setySkalierung(60);
			return;
		case "Unit/Ground/Sphinx.png":
			setxSkalierung(80);
			setySkalierung(44);
			return;
		case "Unit/Ground/Meditec.png":
			setxSkalierung(15);
			setySkalierung(38);
			return;
		case "Unit/Ground/Sakata-MK2.png":
			setxSkalierung(60);
			setySkalierung(50);
			return;
		case "Unit/Ground/Gladiator.png":
			setxSkalierung(20);
			setySkalierung(38);
			return;
		case "Unit/Ground/Sniper.png":
			setxSkalierung(30);
			setySkalierung(38);
			return;
		case "Unit/Ground/A25-Roman.png":
			setxSkalierung(15);
			setySkalierung(38);
			return;
		case "Unit/Ground/Hover Tank.png":
			setxSkalierung(65);
			setySkalierung(30);
			return;
		case "Unit/Ground/Modified Sakata.png":
			setxSkalierung(60);
			setySkalierung(50);
			return;
		case "Unit/Ground/Sakata Spider.png":
			setxSkalierung(60);
			setySkalierung(50);
			return;

			// AIR UNITS
		case "Unit/Air/Scout.png":
			setxSkalierung(60);
			setySkalierung(27);
			return;
		case "Unit/Air/Phantom.png":
			setxSkalierung(115);
			setySkalierung(30);
			return;
		case "Unit/Air/Modified Phantom.png":
			setxSkalierung(115);
			setySkalierung(30);
			return;
		case "Unit/Air/Black Queen.png":
			setxSkalierung(90);
			setySkalierung(65);
			return;
		case "Unit/Air/Saint.png":
			setxSkalierung(60);
			setySkalierung(35);
			return;

		}
	}

	/**
	 * Spiegel erhaltene ImageIcons
	 * 
	 * @author Sbrun
	 */
	@SuppressWarnings("serial")
	class MirrorImageIcon extends ImageIcon {

		public MirrorImageIcon(String filename) {
			super(filename);
		}

		@Override
		public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.translate(getIconWidth(), 0);
			g2.scale(-1, 1);
			super.paintIcon(c, g2, x, y);
		}
	}

	/**
	 * Rotiert das Bild
	 * 
	 * @param img
	 * @return
	 */
	public BufferedImage rotate(BufferedImage img) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-img.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return img = op.filter(img, null);
	}
}
