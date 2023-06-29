package me.zhengdeli.sonar.rule;

import me.zhengdeli.sonar.rule.utils.PrinterVisitor;
import me.zhengdeli.sonar.rule.utils.TestUtil;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;

import java.text.MessageFormat;
import java.util.regex.Pattern;

@Rule(key = "TestMethodNamingRule")
public class TestMethodNamingRule extends BaseTreeVisitor implements JavaFileScanner {
    private static final Logger LOGGER = Loggers.get(TestMethodNamingRule.class);
    private static final Pattern METHOD_NAME_PATTERN = Pattern.compile("^[^\\s_]+_[^\\s_]+_[^\\s_]+$");

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
        PrinterVisitor.print(context.getTree(), LOGGER::debug);
    }

    @Override
    public void visitMethod(MethodTree tree) {
        try {
            if (!TestUtil.isTestMethod(tree)) {
                return;
            }
            if (!METHOD_NAME_PATTERN.matcher(tree.simpleName().name()).matches()) {
                context.reportIssue(this, tree, MessageFormat.format(
                        "@Test 测试方法应以 When_Given_Then 三段式命名。校验规则：匹配正则表达式'{0}'，例如，queryCustomer_repoReturnsEmpty_throwError()",
                        METHOD_NAME_PATTERN.pattern()));
            }
        } finally {
            super.visitMethod(tree);
        }
    }
}
