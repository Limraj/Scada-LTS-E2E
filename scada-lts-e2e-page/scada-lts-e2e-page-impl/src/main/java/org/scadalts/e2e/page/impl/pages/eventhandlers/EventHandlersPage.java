package org.scadalts.e2e.page.impl.pages.eventhandlers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.criterias.Tag.div;
import static org.scadalts.e2e.page.core.criterias.Tag.span;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findActionInNodeInTree;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.reopenWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;


public class EventHandlersPage extends MainPageObjectAbstract<EventHandlersPage> {

    @FindBy(css= "div[dojoattachpoint='containerNode']")
    private SelenideElement tree;

    public final static String TITLE = "Event handlers";

    private final static By SELECT_NODE_PLUS_BY = By.cssSelector("img[src*='treenode_expand_plus']");

    public EventHandlersPage(SelenideElement source) {
        super(source, TITLE);
    }

    public EditEventHandlersPage openEventHandlerCreator(EventDetectorCriteria criteria) {
        delay();
        SelenideElement selenideElement = _findActionInTree(criteria);
        acceptAlertAfterClick(selenideElement);
        return page(new EditEventHandlersPage(this));
    }

    @Override
    public EventHandlersPage waitForCompleteLoad() {
        waitWhile(tree, not(Condition.visible));
        return this;
    }

    public EditEventHandlersPage openEventHandlerEditor(EventHandlerCriteria criteria) {
        delay();
        SelenideElement selenideElement = _findActionInTree(criteria);
        acceptAlertAfterClick(selenideElement);
        return page(new EditEventHandlersPage(this));
    }

    @Override
    public boolean containsObject(IdentifierObject identifier) {
        delay();
        return reopenWhile(this,findObject(identifier.getNodeCriteria(),tree),not(Condition.exist)).is(Condition.exist);
    }

    @Override
    public EventHandlersPage getPage() {
        return this;
    }

    private SelenideElement _findActionInTree(EventDetectorCriteria criteria) {
        DataSourcePointCriteria dataSourcePointCriteria = criteria.getDataSourcePointCriteria();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(dataSourcePointCriteria.getIdentifier(), div(), clazz("dojoTreeNode"));
        NodeCriteria nodeCriteria2 = NodeCriteria.exactlyTypeAny(criteria.getIdentifier(), span());
        return findActionInNodeInTree(this, tree, SELECT_NODE_PLUS_BY, nodeCriteria,nodeCriteria2);
    }

    private SelenideElement _findActionInTree(EventHandlerCriteria criteria) {
        EventDetectorCriteria eventDetectorCriteria = criteria.getEventDetectorCriteria();
        DataSourcePointCriteria dataSourcePointCriteria = eventDetectorCriteria.getDataSourcePointCriteria();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(dataSourcePointCriteria.getIdentifier(), div(), clazz("dojoTreeNode"));
        NodeCriteria nodeCriteria2 = NodeCriteria.exactlyTypeAny(eventDetectorCriteria.getIdentifier(), div());
        NodeCriteria nodeCriteria3 = NodeCriteria.exactlyTypeAny(criteria.getIdentifier(), span());
        return findActionInNodeInTree(this, tree,SELECT_NODE_PLUS_BY,nodeCriteria,nodeCriteria2,nodeCriteria3);
    }
}