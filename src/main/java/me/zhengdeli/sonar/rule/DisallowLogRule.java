package me.zhengdeli.sonar.rule;

import org.sonar.check.Rule;

@Rule(key = "DisallowLogRule")
public class DisallowLogRule extends AbstractDisallowMethodCallingRule {
    public DisallowLogRule() {
        setParams(new String[]{"Logger"}, new String[]{"info", "debug", "error", "warn", "trace"}, "测试方法中不应调用 Logger.info()");
    }
}
