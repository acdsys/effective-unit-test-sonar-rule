package me.zhengdeli.sonar.rule;

import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

public class AbstractDisallowMethodCallingRule extends AbstractMethodDetection {
    private String[] fullyQualifiedTypeNames;
    private String[] methodSimpleNames;

    private String message;

    @Override
    protected MethodMatchers getMethodInvocationMatchers() {
        MethodMatchers.TypeBuilder typeBuilder = MethodMatchers.create();
        MethodMatchers.NameBuilder nameBuilder = typeBuilder.ofTypes(fullyQualifiedTypeNames);
        MethodMatchers.ParametersBuilder parametersBuilder = nameBuilder.names(methodSimpleNames);
        return parametersBuilder.withAnyParameters().build();
    }

    @Override
    protected void onMethodInvocationFound(MethodInvocationTree mit) {
        reportIssue(ExpressionUtils.methodName(mit), message);
    }

    public void setParams(String[] fullyQualifiedTypeNames, String[] methodSimpleNames, String message) {
        this.fullyQualifiedTypeNames = fullyQualifiedTypeNames;
        this.methodSimpleNames = methodSimpleNames;
        this.message = message;
    }
}
