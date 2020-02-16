package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcesPageTestsUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(E2eTestRunner.class)
public class EditDataSourceTest {

    private DataSourceCriteria criteria;
    private DataSourcesPageTestsUtil dataSourcesPageTestsUtil;

    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private DataSourcesPage dataSourcesPage;

    @Before
    public void createDataSource() {
        String dataSourceName = "ds_test" + System.nanoTime();
        criteria = new DataSourceCriteria(dataSourceName, DataSourceType.VIRTUAL_DATA_SOURCE, UpdatePeriodType.SECOUND);
        dataSourcesPageTestsUtil = new DataSourcesPageTestsUtil(E2eAbstractRunnable.getNavigationPage(), criteria);
        dataSourcesPage = dataSourcesPageTestsUtil.openDataSourcesPage();
        editDataSourceWithPointListPageSubject = dataSourcesPageTestsUtil.addDataSources();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.clean();
    }

    @Test
    public void test_edit_update_period_in_data_source() {

        //given:
        int updatePeriodsExp = 12;

        //when
        int updatePeriodsBefore = editDataSourceWithPointListPageSubject
                .getUpdatePeriods();

        //then
        assertNotEquals(updatePeriodsExp, updatePeriodsBefore);

        //and when:
        editDataSourceWithPointListPageSubject
                .setUpdatePeriods(updatePeriodsExp)
                .saveDataSource();

        //then:
        int updatePeriods = dataSourcesPage.reopen()
                .openDataSourceEditor(criteria)
                .getUpdatePeriods();

        assertEquals(updatePeriodsExp, updatePeriods);
    }


    @Test
    public void test_edit_update_period_type_in_data_source() {

        //given:
        UpdatePeriodType updatePeriodsTypeExp = UpdatePeriodType.MILLISECOUND;

        //when
        UpdatePeriodType updatePeriodTypeBefore = editDataSourceWithPointListPageSubject
                .getUpdatePeriodType();

        //then
        assertNotEquals(updatePeriodsTypeExp, updatePeriodTypeBefore);

        //and when:
        editDataSourceWithPointListPageSubject
                .selectUpdatePeriodType(updatePeriodsTypeExp)
                .saveDataSource();

        //then:
        UpdatePeriodType updatePeriodType = dataSourcesPage.reopen()
                .openDataSourceEditor(criteria)
                .getUpdatePeriodType();

        assertEquals(updatePeriodsTypeExp, updatePeriodType);
    }
}
