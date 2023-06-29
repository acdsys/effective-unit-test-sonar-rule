public class DisallowLogRuleTestFile {
    @Test
    public void queryData_repoReturnEmpty_returnEmpty() {
        org.slf4j.Logger log;
        log.info("test");// Noncompliant
    }
}
