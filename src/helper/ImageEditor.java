package helper;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageEditor {

	// Rotate single element
	public static BufferedImage getRotImage(BufferedImage img, int rotAngle) {

		int w = img.getWidth();
		int h = img.getHeight();

		BufferedImage newImg = new BufferedImage(w, h, img.getType());

		Graphics2D g2d = newImg.createGraphics();

		g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();

		return newImg;

	}

	// Rotate image merged from 2 sub-images
	public static BufferedImage getBuildRotImage(BufferedImage[] imgs, int rotAngle, int rotAtIndex) {

		int w = imgs[0].getWidth();
		int h = imgs[0].getHeight();

		BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
		Graphics2D g2d = newImg.createGraphics();

		for (int i = 0; i < imgs.length; i++) {
			if (rotAtIndex == i) {
				g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
			}
			g2d.drawImage(imgs[i], 0, 0, null);
			if (rotAtIndex == i) {
				g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
			}
		}
		g2d.dispose();
		return newImg;

	}

	// Animation
	public static BufferedImage[] getBuildRotImage(BufferedImage[] imgs, BufferedImage secondImage, int rotAngle) {

		int w = imgs[0].getWidth();
		int h = imgs[0].getHeight();

		BufferedImage[] arr = new BufferedImage[imgs.length];

		for (int i = 0; i < imgs.length; i++) {
			BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
			Graphics2D g2d = newImg.createGraphics();
			g2d.drawImage(imgs[i], 0, 0, null);
			g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
			g2d.drawImage(secondImage, 0, 0, null);
			g2d.dispose();

			arr[i] = newImg;
		}

		return arr;

	}


}
