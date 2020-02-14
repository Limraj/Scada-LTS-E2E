package org.scadalts.e2e.test.impl.providers;

import org.scadalts.e2e.common.types.TestPlan;
import org.scadalts.e2e.test.core.plan.provider.TestClassByPlanProvider;
import org.scadalts.e2e.test.impl.tests.ScadaAllTestsSuite;
import org.scadalts.e2e.test.impl.tests.ScadaPageAndServiceTestsSuite;
import org.scadalts.e2e.test.impl.tests.check.ScadaCheckTestsSuite;
import org.scadalts.e2e.test.impl.tests.check.login.LoginCheckTest;
import org.scadalts.e2e.test.impl.tests.check.login.LogoutCheckTest;
import org.scadalts.e2e.test.impl.tests.page.ScadaPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.webservice.ScadaWebServiceTestsSuite;

import java.util.HashMap;
import java.util.Map;

public class ScadaTestClassByPlanProvider implements TestClassByPlanProvider {

    private static Map<TestPlan, Class<?>> tests = new HashMap<>();

    static {
        tests.put(TestPlan.CHECK, ScadaCheckTestsSuite.class);
        tests.put(TestPlan.PAGE, ScadaPageTestsSuite.class);
        tests.put(TestPlan.ALL, ScadaAllTestsSuite.class);
        tests.put(TestPlan.SERVICE, ScadaWebServiceTestsSuite.class);
        tests.put(TestPlan.PAGE_SERVICE, ScadaPageAndServiceTestsSuite.class);
        tests.put(TestPlan.LOGIN, LoginCheckTest.class);
        tests.put(TestPlan.LOGOUT, LogoutCheckTest.class);
    }

    @Override
    public boolean containsPlan(TestPlan plan) {
        return tests.containsKey(plan);
    }

    @Override
    public Class<?> getPlan(TestPlan testPlan) {
        return tests.computeIfAbsent(testPlan, (plan) -> {throw new IllegalArgumentException(plan.name());});
    }
}
