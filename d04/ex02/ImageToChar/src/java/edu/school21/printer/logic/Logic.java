package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Logic {
	private static final String EMPTY = " ";

	public static String[][] imageToCharArray(URL url, String white, String black) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(url);

		int width = bufferedImage.getWidth();

		int height = bufferedImage.getHeight();

		String[][] array = new String[height][width];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color color = new Color(bufferedImage.getRGB(i, j));

				if (color.equals(Color.WHITE)) {
					if (white.length() == 1) {
						array[j][i] = white;
					} else {
						array[j][i] = Ansi.formatMessage(" ", Ansi.generateCode(Ansi.Attribute.NONE,
								Ansi.FColor.valueOf(white), Ansi.BColor.valueOf(white)));
					}
				} else if (color.equals(Color.BLACK)) {
					if (black.length() == 1) {
						array[j][i] = black;
					} else {
						array[j][i] = Ansi.formatMessage(" ", Ansi.generateCode(Ansi.Attribute.NONE,
								Ansi.FColor.valueOf(black), Ansi.BColor.valueOf(black)));
					}
				} else {
					array[j][i] = EMPTY;
				}
			}
		}

		return array;
	}
}
