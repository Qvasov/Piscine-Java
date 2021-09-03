package edu.school21.preprocessor;

public class PreProcessorToLower implements PreProcessor {

	@Override
	public String modifyMessage(String message) {
		return message.toLowerCase();
	}
}
