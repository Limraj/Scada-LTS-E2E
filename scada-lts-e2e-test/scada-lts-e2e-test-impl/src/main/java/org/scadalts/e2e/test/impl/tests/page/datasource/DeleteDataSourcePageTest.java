package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class DeleteDataSourcePageTest {

    private DataSourceIdentifier dataSourceToDeleteName = new DataSourceIdentifier("ds_test_delete" + System.nanoTime());
    private DataSourceCriteria dataSourceToDeleteCriteria;

    private DataSourcePointObjectsCreator dataSourcesPageTestsUtil;
    private DataSourcesPage dataSourcesPageSubject;

    @Before
    public void createDataSource() {
        dataSourceToDeleteCriteria = DataSourceCriteria.criteria(dataSourceToDeleteName,
                UpdatePeriodType.SECOND, DataSourceType.VIRTUAL_DATA_SOURCE);
        dataSourcesPageTestsUtil = new DataSourcePointObjectsCreator(E2eAbstractRunnable.getNavigationPage(), dataSourceToDeleteCriteria);
        dataSourcesPageTestsUtil.createDataSources();
        dataSourcesPageSubject = dataSourcesPageTestsUtil.openPage();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.deleteObjects();
    }

    @Test
    public void test_delete_data_source() {

        //given
        String bodyBeforeDelete = dataSourcesPageSubject.getBodyText();

        //then:
        assertThat(bodyBeforeDelete, containsString(dataSourceToDeleteName.getValue()));

        //when:
        String bodyAfterDelete = dataSourcesPageSubject
                .deleteDataSource(dataSourceToDeleteCriteria)
                .getBodyText();

        //then:
        assertThat(bodyAfterDelete, not(containsString(dataSourceToDeleteName.getValue())));
    }
}
