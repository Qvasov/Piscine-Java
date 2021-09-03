package edu.school21.render;

import edu.school21.preprocessor.PreProcessor;

import java.io.PrintStream;

public class RendererStandardImpl implements Render {

	private PreProcessor preProcessor;
	private final PrintStream out = System.out;

	public RendererStandardImpl(PreProcessor preProcessor) {
		this.preProcessor = preProcessor;
	}

	@Override
	public void renderMessage(String message) {
		out.println(preProcessor.modifyMessage(message));
	}
}
