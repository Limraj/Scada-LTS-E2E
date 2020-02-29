package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestObjectsUtil;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class DeleteGraphicalViewPageTest {

    private final GraphicalViewIdentifier viewName = IdentifierObjectFactory.viewName();

    private GraphicalViewCriteria criteria;
    private GraphicalViewTestObjectsUtil graphicalViewTestsUtil;
    private GraphicalViewsPage graphicalViewsPageSubject;

    @Before
    public void createView() {
        criteria = new GraphicalViewCriteria(viewName);
        graphicalViewTestsUtil = new GraphicalViewTestObjectsUtil(E2eAbstractRunnable.getNavigationPage(), criteria);
        graphicalViewTestsUtil.createObjects();
        graphicalViewsPageSubject = graphicalViewTestsUtil.openPage();
    }

    @After
    public void clean() {
        graphicalViewTestsUtil.deleteObjects();
    }

    @Test
    public void test_delete_view() {

        //when:
        graphicalViewsPageSubject
                .openViewEditor(criteria)
                .delete();

        //then:
        String body = graphicalViewsPageSubject.reopen().getBodyText();

        assertThat(body, not(containsString(viewName.getValue())));
    }
}