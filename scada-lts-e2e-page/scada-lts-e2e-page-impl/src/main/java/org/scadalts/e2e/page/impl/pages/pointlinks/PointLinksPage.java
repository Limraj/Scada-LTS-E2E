package org.scadalts.e2e.page.impl.pages.pointlinks;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.CssClass;
import org.scadalts.e2e.page.core.criterias.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObjects;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

@Log4j2
public class PointLinksPage extends MainPageObjectAbstract<PointLinksPage> {

    @FindBy(id = "pl-1Img")
    private SelenideElement addPointLink;

    @FindBy(id = "pointLinksTable")
    private SelenideElement pointLinksTable;

    public static final String TITLE = "Point links";

    public PointLinksPage(SelenideElement source) {
        super(source, TITLE);
    }

    public PointLinksDetailsPage openPointLinkCreator() {
        delay();
        waitWhile(addPointLink, not(Condition.visible)).click();
        return page(new PointLinksDetailsPage(this));
    }

    @Override
    public PointLinksPage waitForCompleteLoad() {
        waitWhile(pointLinksTable, not(Condition.visible));
        NodeCriteria nodeCriteria = NodeCriteria.every(1, 0,
                Tag.td(), new CssClass("link"));
        waitWhile(a -> findObjects(nodeCriteria, pointLinksTable).size() == 0,null);
        return this;
    }

    public String getPointLinksTableText() {
        delay();
        waitForCompleteLoad();
        String text = pointLinksTable.getText();
        if(StringUtils.isBlank(text))
            waitWhileNotVisible(pointLinksTable);
        return pointLinksTable.getText();
    }

    public String getPointLinksTableHtml() {
        delay();
        return pointLinksTable.innerHtml();
    }

    public PointLinksDetailsPage openPointLinkEditor(PointLinkCriteria criteria) {
        _findAction(criteria).click();
        return page(new PointLinksDetailsPage(this));
    }

    @Override
    public PointLinksPage getPage() {
        return this;
    }

    @Override
    public boolean containsObject(CriteriaObject criteria) {
        delay();
        String bodyText = getPointLinksTableText();
        return bodyText.contains(criteria.getIdentifier().getValue());
    }

    @Override
    public PointLinksPage waitForObject(CriteriaObject criteriaObject) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteriaObject.getIdentifier(),
                Tag.td(), new CssClass("link"));
        return waitForObject(nodeCriteria);
    }

    private SelenideElement _findAction(PointLinkCriteria criteria) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getSource().getIdentifier(), criteria.getSource().getIdentifier(), Tag.tbody());
        return findObject(nodeCriteria, pointLinksTable);
    }
}