package annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationParser {

    public void parse(Class<Annotation> annotationClass) {
        try {
            Method annotatedMethod = annotationClass.getDeclaredMethod("annotatedMethod");
            MethodAnnotation annotation = annotatedMethod.getAnnotation(MethodAnnotation.class);
            System.out.println(annotation.value());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            Field annotatedField = annotationClass.getDeclaredField("annotatedField");
            FieldAnnotation annotation = annotatedField.getAnnotation(FieldAnnotation.class);
            System.out.println(annotation.field());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            Constructor<Annotation> constructor = annotationClass.getConstructor();
            ConstructorAnnotation annotation = constructor.getAnnotation(ConstructorAnnotation.class);
            System.out.println(annotation.constructorName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Package aPackage = annotationClass.getPackage();
        PackageAnnotation annotation = aPackage.getAnnotation(PackageAnnotation.class);
        System.out.println(annotation.value());

        TypeAnnotation typeAnnotation = annotationClass.getAnnotation(TypeAnnotation.class);
        System.out.println(typeAnnotation.typeName());

        @VariableAnnotation(variableName = "variableName") Annotation variable = new Annotation();
        VariableAnnotation variableAnnotation = variable.getClass().getAnnotation(VariableAnnotation.class);
        System.out.println(variableAnnotation.variableName());

    }
}
