package me.zhengdeli.sonar.rule;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.sonar.api.utils.log.LogTester;
import org.sonar.api.utils.log.LoggerLevel;
import org.sonar.java.checks.verifier.CheckVerifier;

public class TestMethodNamingRuleTest {
    @Rule
    public LogTester logTester = new LogTester().setLevel(LoggerLevel.DEBUG);

    @Test
    void detected() {
        CheckVerifier.newVerifier()
                .onFile(this.getClass().getResource("/files/TestMethodNamingRuleTestFile.java").getFile())
                .withCheck(new TestMethodNamingRule())
                .verifyIssues();
    }
}
