package springSession.annotation;

import org.junit.Test;

public class annotationParserTest {
    @Test
    public void should_parse_annotation(){
        AnnotationParser parser = new AnnotationParser();

        parser.parse(Annotation.class);
    }
}
