package edu.school21.processors;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@SupportedAnnotationTypes({"edu.school21.processors.HtmlInput", "edu.school21.processors.HtmlForm"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (annotations.isEmpty()) {
			return false;
		}

		Map<TypeElement, List<VariableElement>> htmlFormClasses = new HashMap<>();

		for (Element e : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
			if (e.getKind() != ElementKind.CLASS) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
						String.format("Only classes can be annotated with %s", HtmlForm.class.getSimpleName()));
				return true;
			} else {
				htmlFormClasses.put((TypeElement) e, new ArrayList<>());
			}
		}

		for (Element e : roundEnv.getElementsAnnotatedWith(HtmlInput.class)) {
			if (e.getKind() != ElementKind.FIELD) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
						String.format("Only fields can be annotated with %s", HtmlForm.class.getSimpleName()));
				return true;
			} else if (e.getEnclosingElement().getKind() != ElementKind.CLASS) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
						String.format("Field annotated with %s is outside of class", HtmlInput.class.getSimpleName()));
				return true;
			} else if (!htmlFormClasses.containsKey((TypeElement) e.getEnclosingElement().getSimpleName())) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
						String.format("Field annotated with %s is out of class annotated %s",
								HtmlInput.class.getSimpleName(), HtmlForm.class.getSimpleName()));
				return true;
			} else {
				htmlFormClasses.get((TypeElement) e.getEnclosingElement()).add((VariableElement) e);
			}
		}

		for (TypeElement e : htmlFormClasses.keySet()) {
			HtmlForm htmlForm = e.getAnnotation(HtmlForm.class);
			try {
				PrintWriter writer = new PrintWriter(
						new BufferedWriter(
								processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT,
										"", htmlForm.fileName()).openWriter()
						)
				);
				writer.printf("<form action = \"%s\" method = \"%s\">\n", htmlForm.action(), htmlForm.method());

				for (VariableElement field : htmlFormClasses.get(e)) {
					HtmlInput htmlInput = field.getAnnotation(HtmlInput.class);
					writer.printf("<input type = \"%s\" name = \"%s\" placeholder = \"%s\">\n",
							htmlInput.type(), htmlInput.name(), htmlInput.placeholder());
				}

				writer.printf("<input type = \"submit\" value = \"Send\">\n");
				writer.printf("</form\n>");
				writer.close();
			} catch (IOException ioException) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, ioException.getMessage());
			}
		}
		return true;
	}
}
