package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@Log4j2
@RunWith(E2eTestRunner.class)
public class MultiTabGraphicalViewTest {

    private final String viewName = "viewNameTest" + System.nanoTime();

    private final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final GraphicalViewTestsUtil testsUtil = new GraphicalViewTestsUtil(navigationPage);
    private GraphicalViewsPage graphicalViewsPageSubject;

    @Before
    public void createView() throws ConfigureTestException {
        logger.info("viewName: {}", viewName);
        testsUtil.openGraphicalViews()
                .openViewCreator()
                .chooseFile(testsUtil.getBackgroundFile())
                .uploadFile()
                .setViewName(viewName)
                .selectComponentByName("Alarms List")
                .addViewComponent()
                .dragAndDropViewComponent()
                .save();
        graphicalViewsPageSubject = testsUtil.openGraphicalViews();
    }

    @After
    public void clean() throws ConfigureTestException {
        NavigationPage.closeAllButOnePage();
        testsUtil.openGraphicalViews().openViewEditor(viewName)
                .delete();
    }

    @Test
    public void test_open_multi_tab_for_view() {

        //when:
        graphicalViewsPageSubject.openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure()
                .openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure()
                .openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure()
                .openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure()
                .openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure();

        //then:
        Set<String> tabs = NavigationPage.tabsOpened();
        assertEquals(6, tabs.size());
    }
}
