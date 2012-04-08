package springSession.annotation;

@TypeAnnotation(typeName = "type name")
public class Annotation {
    @FieldAnnotation(field = 10)
    int annotatedField;

    @MethodAnnotation(value = 5)
    void annotatedMethod() {

    }

    @ConstructorAnnotation(constructorName = "name")
    public Annotation() {
    }
}
