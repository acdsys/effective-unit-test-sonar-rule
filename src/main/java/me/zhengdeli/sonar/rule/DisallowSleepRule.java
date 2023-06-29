package me.zhengdeli.sonar.rule;

import org.sonar.check.Rule;

@Rule(key = "DisallowSleepRule")
public class DisallowSleepRule extends AbstractDisallowMethodCallingRule {
    public DisallowSleepRule() {
        setParams(new String[]{"java.lang.Thread"}, new String[]{"sleep"}, "测试方法中不应调用 java.lang.Thread.sleep()，因为 sleep 可能导致测试结果不确定");
    }
}
