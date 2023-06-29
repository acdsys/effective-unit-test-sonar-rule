package me.zhengdeli.sonar.rule.utils;

import org.sonar.plugins.java.api.tree.*;

import java.util.List;

public class TestUtil {
    public static boolean isTestMethod(MethodTree tree) {
        List<AnnotationTree> annotations = tree.modifiers().annotations();
        boolean isTestMethod = Boolean.FALSE;
        for (AnnotationTree annotationTree : annotations) {
            TypeTree annotationType = annotationTree.annotationType();
            if (annotationType.is(Tree.Kind.IDENTIFIER)) {
                String annotationName = ((IdentifierTree) annotationType).name();
                if ("Test".equals(annotationName)) {
                    isTestMethod = Boolean.TRUE;
                }
            }
        }
        return isTestMethod;
    }

    public static boolean isTestClass(ClassTree tree) {
        if (!tree.is(Tree.Kind.CLASS)) {
            return false;
        }
        List<AnnotationTree> annotations = tree.modifiers().annotations();
        boolean isTestClass = Boolean.FALSE;
        for (AnnotationTree annotationTree : annotations) {
            TypeTree annotationType = annotationTree.annotationType();
            if (annotationType.is(Tree.Kind.IDENTIFIER)) {
                String annotationName = ((IdentifierTree) annotationType).name();
                if ("RunWith".equals(annotationName)) {
                    isTestClass = Boolean.TRUE;
                }
            }
        }
        return isTestClass;
    }
}
