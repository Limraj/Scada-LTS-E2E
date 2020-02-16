package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcesPageTestsUtil;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class DeleteDataSourceTest {

    private String dataSourceToDeleteName = "ds_test" + System.nanoTime();
    private DataSourceCriteria dataSourceToDeleteCriteria;

    private DataSourcesPageTestsUtil dataSourcesPageTestsUtil;
    private DataSourcesPage dataSourcesPageSubject;

    @Before
    public void createDataSource() {
        dataSourceToDeleteCriteria = new DataSourceCriteria(dataSourceToDeleteName, DataSourceType.VIRTUAL_DATA_SOURCE,
                UpdatePeriodType.SECOUND);
        dataSourcesPageTestsUtil = new DataSourcesPageTestsUtil(E2eAbstractRunnable.getNavigationPage(), dataSourceToDeleteCriteria);
        dataSourcesPageTestsUtil.addDataSources();
        dataSourcesPageSubject = dataSourcesPageTestsUtil.openDataSourcesPage();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.clean();
    }

    @Test
    public void test_delete_data_source() {

        //given
        String body = dataSourcesPageSubject.getBodyText();

        //then:
        assertThat(body, containsString(dataSourceToDeleteName));

        //when:
        body = dataSourcesPageSubject
                .deleteDataSource(dataSourceToDeleteCriteria)
                .reopen()
                .getBodyText();

        //then:
        assertThat(body, not(containsString(dataSourceToDeleteName)));
    }
}
