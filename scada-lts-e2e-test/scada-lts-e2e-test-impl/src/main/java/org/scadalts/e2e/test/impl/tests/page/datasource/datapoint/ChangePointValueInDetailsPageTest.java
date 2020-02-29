package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;


import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.utils.TestObjectsUtilible;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.DataSourcePointTestObjectsUtil;
import org.scadalts.e2e.test.impl.utils.WatchListTestObjectsUtil;

import java.util.Collection;

import static org.junit.Assert.*;

@Log4j2
@RunWith(E2eTestParameterizedRunner.class)
public class ChangePointValueInDetailsPageTest {

    @Parameterized.Parameters(name = "{index}: value:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public ChangePointValueInDetailsPageTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static TestObjectsUtilible<WatchListPage, WatchListPage> watchListTestsUtil;
    private static TestObjectsUtilible<DataSourcesPage, EditDataSourceWithPointListPage> dataSourcesAndPointsPageTestsUtil;
    private static DataPointDetailsPage dataPointDetailsPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(123);

        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria
                .criteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        dataSourcesAndPointsPageTestsUtil =
                new DataSourcePointTestObjectsUtil(navigationPage, dataSourcePointCriteria);
        dataSourcesAndPointsPageTestsUtil.createObjects();

        watchListTestsUtil = new WatchListTestObjectsUtil(navigationPage, dataSourcePointCriteria);
        dataPointDetailsPageSubject = watchListTestsUtil.createObjects()
                .openDataPointDetails(dataSourcePointCriteria);
    }

    @AfterClass
    public static void clean() {
        watchListTestsUtil.deleteObjects();
        dataSourcesAndPointsPageTestsUtil.deleteObjects();
    }

    @Test
    public void test_change_data_point_value_in_details() {

        //when:
        dataPointDetailsPageSubject
                .setDataPointValue(valueExpected)
                .confirmDataPointValue();

        //and:
        String result = dataPointDetailsPageSubject.refreshPage().getDataPointValue(valueExpected);

        //then:
        assertNotNull(result);
        assertNotEquals("", result);

        //then:
        assertEquals(valueExpected, result);
    }
}