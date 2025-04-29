package com.qa.orangehrm.testcase;

import com.qa.orangehrm.keywordEngine.KeywordEngine;
import org.testng.annotations.Test;

public class LoginTest {
    public KeywordEngine keywordEngine;
    @Test
    public void loginTest() throws InterruptedException {
        keywordEngine=new KeywordEngine();
        keywordEngine.startExecution("Login");
    }
}
