package edu.school21.render;

import edu.school21.preprocessor.PreProcessor;

import java.io.PrintStream;

public class RendererErrImpl implements Render {

	private PreProcessor preProcessor;
	private final PrintStream output = System.err;

	public RendererErrImpl(PreProcessor preProcessor) {
		this.preProcessor = preProcessor;
	}

	@Override
	public void renderMessage(String message) {
		output.println(preProcessor.modifyMessage(message));
	}
}
