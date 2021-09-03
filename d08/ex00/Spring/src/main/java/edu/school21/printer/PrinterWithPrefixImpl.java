package edu.school21.printer;

import edu.school21.render.Render;

public class PrinterWithPrefixImpl implements Printer {

	private Render render;
	private String prefix;

	public PrinterWithPrefixImpl(Render render) {
		this.render = render;
		this.prefix = "";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void print(String message) {
		render.renderMessage(prefix + " " + message);
	}
}
