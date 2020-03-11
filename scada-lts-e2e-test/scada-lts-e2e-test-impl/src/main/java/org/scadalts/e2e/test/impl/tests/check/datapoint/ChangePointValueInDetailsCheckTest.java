package org.scadalts.e2e.test.impl.tests.check.datapoint;


import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.Collection;

import static org.junit.Assert.*;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class ChangePointValueInDetailsCheckTest {

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public ChangePointValueInDetailsCheckTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static DataPointDetailsPage dataPointDetailsPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond(new DataSourceIdentifier(TestImplConfiguration.dataSourceName));
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(new DataPointIdentifier(TestImplConfiguration.dataPointName));
        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);

        dataPointDetailsPageSubject = TestWithPageUtil.getNavigationPage().openWatchList()
                .openDataPointDetails(dataSourcePointCriteria);
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