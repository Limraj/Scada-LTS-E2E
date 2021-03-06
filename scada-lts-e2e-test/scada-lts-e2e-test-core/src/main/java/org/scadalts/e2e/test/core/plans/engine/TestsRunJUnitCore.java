package org.scadalts.e2e.test.core.plans.engine;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.scadalts.e2e.common.config.E2eConfiguration;

import java.util.ArrayList;
import java.util.List;

@Log4j2
class TestsRunJUnitCore implements TestsRunEngine {

    private final JUnitCore jUnitCore;

    TestsRunJUnitCore(JUnitCore jUnitCore) {
        this.jUnitCore = jUnitCore;
    }

    @Override
    public List<E2eResult> run(List<Class<?>> tests) {
        List<E2eResult> results = new ArrayList<>();
        for (Class<?> test: tests) {
            E2eResult result = _run(test, new E2eRunListener(test));
            if(!result.wasSuccessful()) {
                logger.info("repeats test...{}", test.getSimpleName());
                result = _run(test, new E2eRunListener(test));
            }
            results.add(result);
        }
        return results;
    }

    private E2eResult _run(Class<?> test, RunListener runListener) {
        jUnitCore.addListener(runListener);
        Result result = jUnitCore.run(test);
        jUnitCore.removeListener(runListener);
        return E2eResult.builder()
                .url(E2eConfiguration.baseUrl)
                .result(result)
                .sessionId(E2eConfiguration.sessionId)
                .simpleTestName(test.getSimpleName())
                .testName(test.getName())
                .build();
    }
}
