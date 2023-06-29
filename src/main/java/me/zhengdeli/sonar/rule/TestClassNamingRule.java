package me.zhengdeli.sonar.rule;

import me.zhengdeli.sonar.rule.utils.PrinterVisitor;
import me.zhengdeli.sonar.rule.utils.TestUtil;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;

import java.text.MessageFormat;
import java.util.regex.Pattern;

@Rule(key = "TestClassNamingRule")
public class TestClassNamingRule extends BaseTreeVisitor implements JavaFileScanner {
    private static final Logger LOGGER = Loggers.get(TestClassNamingRule.class);
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("^\\S+Test$");

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
        PrinterVisitor.print(context.getTree(), LOGGER::debug);
    }

    @Override
    public void visitClass(ClassTree tree) {
        try {
            if (!TestUtil.isTestClass(tree)) {
                return;
            }
            IdentifierTree simpleName = tree.simpleName();
            if (simpleName == null) {
                return;
            }
            if (!CLASS_NAME_PATTERN.matcher(simpleName.name()).matches()) {
                context.reportIssue(this, simpleName, MessageFormat.format(
                        "测试类应以 *Test 命名。校验规则：匹配正则表达式'{0}'，例如，CustomerServiceTest。",
                        CLASS_NAME_PATTERN.pattern()));
            }
        } finally {
            super.visitClass(tree);
        }
    }
}
