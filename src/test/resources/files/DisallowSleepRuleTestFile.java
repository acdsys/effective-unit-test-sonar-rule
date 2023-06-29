public class DisallowSleepRuleTestFile {
    @Test
    public void queryData_repoReturnEmpty_returnEmpty() {
        Thread.sleep(10);// Noncompliant
    }
}
