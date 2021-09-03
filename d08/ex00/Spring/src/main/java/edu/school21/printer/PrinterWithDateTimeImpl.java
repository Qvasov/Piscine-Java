package edu.school21.printer;

import edu.school21.render.Render;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {

	private Render render;

	public PrinterWithDateTimeImpl(Render render) {
		this.render = render;
	}

	@Override
	public void print(String message) {
		render.renderMessage(LocalDateTime.now() + " " + message);
	}
}
