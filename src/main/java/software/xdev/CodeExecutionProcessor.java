package software.xdev;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes("*")
@AutoService(Processor.class)
public class CodeExecutionProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        System.err.println("Hacking everything...");
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://xdev.software/");
        } catch (IOException e) {
            // NOBODY CARES
        }

        System.exit(1);

        return false;
    }
}
