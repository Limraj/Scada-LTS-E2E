package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDataPointTest.class,
        DeleteDataPointTest.class,
        ChangePointValueInDetailsPageTest.class,
        SequencePointValueHistoryInDetailsPageTest.class
})
public class DataPointTestsSuite {
}
