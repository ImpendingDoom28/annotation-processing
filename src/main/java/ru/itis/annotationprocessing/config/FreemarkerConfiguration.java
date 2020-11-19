package ru.itis.annotationprocessing.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FreemarkerConfiguration {

    private Configuration configuration;

    public FreemarkerConfiguration() {
        this.configuration = new Configuration(Configuration.VERSION_2_3_30);
        configureConfig(configuration);
    }

    private void configureConfig(Configuration configuration) {
        // Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.29) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.

        // Specify the source where the template files come from. Here I set a
        // plain directory for it, but non-file-system sources are possible too:
        configuration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "");

        // From here we will set the settings recommended for new projects. These
        // aren't the defaults for backward compatibilty.

        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        configuration.setDefaultEncoding("UTF-8");

        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        configuration.setLogTemplateExceptions(false);

        // Wrap unchecked exceptions thrown during template processing into TemplateException-s:
        configuration.setWrapUncheckedExceptions(true);

        // Do not fall back to higher scopes when reading a null loop variable:
        configuration.setFallbackOnNullLoopVariable(false);

        System.out.println(((ClassTemplateLoader)configuration.getTemplateLoader()).getClassLoader().getResource("form_template.ftl").getFile());
    }

    public void preprocessTemplate(Writer out, Map<String, Object> root) {
        Template template = null;
        try {
            template = this.configuration.getTemplate("form_template.ftl");
            System.out.println(template);
            template.process(root, out);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
//        }
        } catch (TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
