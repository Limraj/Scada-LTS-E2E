package org.scadalts.e2e.test.impl.tests.check.datapoint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.page.watchlist.ChangePointValueOnWatchListPageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ChangePointValueOnWatchListPageTest.class,
        SequencePointValueHistoryInDetailsCheckTest.class
})
public class DataPointDetailsCheckTestsSuite {
}
