package org.scadalts.e2e.test.impl.tests.check;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.check.login.LoginCheckTest;
import org.scadalts.e2e.test.impl.tests.page.navigation.NavigationPageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginCheckTest.class,
        NavigationPageTest.class
})
public class ScadaCheckTestsSuite {
}
