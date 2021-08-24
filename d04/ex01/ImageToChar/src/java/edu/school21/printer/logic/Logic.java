package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Logic {
	private static final char EMPTY = ' ';

	public static char[][] imageToCharArray(URL url, char white, char black) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(url);

		int width = bufferedImage.getWidth();

		int height = bufferedImage.getHeight();

		char[][] array = new char[height][width];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color color = new Color(bufferedImage.getRGB(i, j));

				if (color.equals(Color.WHITE)) {
					array[j][i] = white;
				} else if (color.equals(Color.BLACK)) {
					array[j][i] = black;
				} else {
					array[j][i] = EMPTY;
				}
			}
		}

		return array;
	}
}
