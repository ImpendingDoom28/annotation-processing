package ru.itis.annotationprocessing.processors;

import com.google.auto.service.AutoService;
import ru.itis.annotationprocessing.annotations.HtmlForm;
import ru.itis.annotationprocessing.annotations.HtmlInput;
import ru.itis.annotationprocessing.config.FreemarkerConfiguration;
import ru.itis.annotationprocessing.models.FormObject;
import ru.itis.annotationprocessing.models.InputObject;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {
        "ru.itis.annotationprocessing.annotations.HtmlForm",
})
public class HtmlAnnotationsProcessor extends AbstractProcessor {

    public HtmlAnnotationsProcessor() {}

    private Filer filer;
    private FreemarkerConfiguration freemarkerConfig;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.freemarkerConfig = new FreemarkerConfiguration();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> htmlFormAnnotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);

        Map<String, Object> root = new HashMap<>();

        for (Element annotatedElement: htmlFormAnnotatedElements) {
            String path = HtmlAnnotationsProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring(1) + annotatedElement.getSimpleName().toString() + ".html";
            Path out = Paths.get(path);
            try {
                FileObject fileObject =  filer.createResource(
                        StandardLocation.CLASS_OUTPUT,
                        "",
                        annotatedElement.getSimpleName().toString() + ".html",
                        (Element[]) null
                );

                Writer writer = fileObject.openWriter();

                HtmlForm annotation = annotatedElement.getAnnotation(HtmlForm.class);

//                writer.write("<form action='" + annotation.action() +
//                        "' method='"+ annotation.method() + "' />\n");

                FormObject formObject = new FormObject();

                formObject.setAction(annotation.action());
                formObject.setMethod(annotation.method());

                List<InputObject> inputs = new ArrayList<>();

                annotatedElement.getEnclosedElements().forEach(element -> {
                    HtmlInput inputAnnotation = element.getAnnotation(HtmlInput.class);
                    if(inputAnnotation != null) {
//                        try {
                            InputObject inputObject = new InputObject();
                            inputObject.setName(inputAnnotation.name());
                            inputObject.setType(inputAnnotation.type());
                            inputObject.setPlaceholder(inputAnnotation.placeholder());
                            inputs.add(inputObject);
//                              writer.write("\t<input " +
//                                            "name=\"" + inputAnnotation.name() + "\" " +
//                                            "placeholder=\"" + inputAnnotation.placeholder() + "\" " +
//                                            "type=\"" + inputAnnotation.type() + "\" " +
//                                            "/>\n"
//                            );
//                        } catch (IOException e) {
//                            throw new IllegalArgumentException(e);
//                        }
                    }
                });
//                writer.write("</form>");

                formObject.setInputs(inputs);

                root.put("form", formObject);

                freemarkerConfig.preprocessTemplate(writer, root);

                writer.close();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return super.getCompletions(element, annotation, member, userText);
    }

    @Override
    protected synchronized boolean isInitialized() {
        return super.isInitialized();
    }
}
