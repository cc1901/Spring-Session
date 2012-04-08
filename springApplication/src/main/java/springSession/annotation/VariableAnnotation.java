package springSession.annotation;

import java.lang.annotation.*;

@Target(ElementType.LOCAL_VARIABLE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VariableAnnotation {
    String variableName();
}
