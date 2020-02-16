package org.scadalts.e2e.page.impl.pages.datasource;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criteria.ActionCriteria;
import org.scadalts.e2e.page.core.criteria.RowCriteria;
import org.scadalts.e2e.page.core.exceptions.DynamicElementException;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.PropertiesDataPointPage;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.util.DynamicElementUtil.findAction;
import static org.scadalts.e2e.page.core.util.E2eUtil.acceptAlert;
import static org.scadalts.e2e.page.core.util.StabilityUtil.waitWhile;


public class EditDataSourceWithPointListPage extends PageObjectAbstract<EditDataSourceWithPointListPage> {

    @FindBy(id = "dsStatusImg")
    private SelenideElement dataSourceOnOff;

    @FindBy(id = "enableAllImg")
    private SelenideElement enableAllDataPoint;

    @FindBy(id = "editImg-1")
    private SelenideElement addDataPoint;

    @FindBy(css = "tbody #pointsList")
    private SelenideElement dataPointsTable;

    private EditDataSourcePage editDataSourcePage;

    private static final By SELECTOR_ACTION_EDIT_DATA_POINT_BY = By.cssSelector("img[onclick*='editPoint(']");
    private static final By SELECTOR_ACTION_PROPERTIES_DATA_POINT_BY = By.cssSelector("img[onclick*='data_point_edit.shtm?dpid=']");
    private static final By SELECTOR_ACTION_ENABLE_DATA_POINT_BY = By.cssSelector("img[src='images/brick_stop.png']");
    private static final By SELECTOR_ACTION_DISABLE_DATA_POINT_BY = By.cssSelector("img[src='images/brick_go.png']");
    private static final By SELECTOR_ENABLE_DATA_SOURCE_BY = By.cssSelector("img[src='images/database_go.png']");
    private static final By SELECTOR_DISABLE_DATA_SOURCE_BY = By.cssSelector("img[src='images/database_stop.png']");


    public static final String TITLE = "Points";

    public EditDataSourceWithPointListPage() {
        super(TITLE);
        editDataSourcePage = page(EditDataSourcePage.class);
    }

    public EditDataSourceWithPointListPage enableDataSource() {
        dataSourceOnOff.click();
        acceptAlert();
        waitWhile($(SELECTOR_ENABLE_DATA_SOURCE_BY), not(Condition.visible));
        return this;
    }

    public EditDataSourceWithPointListPage disableDataSource() {
        dataSourceOnOff.click();
        acceptAlert();
        waitWhile($(SELECTOR_DISABLE_DATA_SOURCE_BY), not(Condition.visible));
        return this;
    }

    public EditDataSourceWithPointListPage enableAllDataPoint() {
        enableAllDataPoint.click();
        acceptAlert();
        return this;
    }

    @Override
    public EditDataSourceWithPointListPage getPage() {
        return this;
    }

    public EditDataPointPage addDataPoint() {
        addDataPoint.click();
        acceptAlert();
        return page(EditDataPointPage.class);
    }

    public EditDataPointPage openDataPointEditor(DataPointCriteria criteria) {
        _findAction(criteria, SELECTOR_ACTION_EDIT_DATA_POINT_BY).click();
        return page(EditDataPointPage.class);
    }

    public PropertiesDataPointPage openDataPointProperties(DataPointCriteria criteria) {
        _findAction(criteria, SELECTOR_ACTION_PROPERTIES_DATA_POINT_BY).click();
        return page(PropertiesDataPointPage.class);
    }

    public EditDataSourceWithPointListPage enableDataPoint(DataPointCriteria criteria) {
        _findAction(criteria, SELECTOR_ACTION_ENABLE_DATA_POINT_BY).click();
        return this;
    }

    public EditDataSourceWithPointListPage disableDataPoint(DataPointCriteria criteria) {
        _findAction(criteria, SELECTOR_ACTION_DISABLE_DATA_POINT_BY).click();
        return this;
    }

    public EditDataSourcePage setDataSourceName(String dataSourceName) {
        return editDataSourcePage.setDataSourceName(dataSourceName);
    }

    public EditDataSourcePage setDataSourceXid(String dataSourceXid) {
        return editDataSourcePage.setDataSourceXid(dataSourceXid);
    }

    public EditDataSourcePage setUpdatePeriods(int updatePeriods) {
        return editDataSourcePage.setUpdatePeriods(updatePeriods);
    }

    public EditDataSourcePage selectUpdatePeriodType(UpdatePeriodType componentName) {
        return editDataSourcePage.selectUpdatePeriodType(componentName);
    }

    public String selectUpdatePeriodTypeValue(UpdatePeriodType componentName) {
        return editDataSourcePage.selectUpdatePeriodTypeValue(componentName);
    }

    public EditDataSourceWithPointListPage saveDataSource() {
        return editDataSourcePage.saveDataSource();
    }

    public String getDataSourceName() {
        return editDataSourcePage.getDataSourceName();
    }

    public String getDataSourceXid() {
        return editDataSourcePage.getDataSourceXid();
    }

    public int getUpdatePeriods() {
        return editDataSourcePage.getUpdatePeriods();
    }

    public UpdatePeriodType getUpdatePeriodType() {
        return editDataSourcePage.getUpdatePeriodType();
    }

    private SelenideElement _findAction(DataPointCriteria criteria, By selectAction) {
        RowCriteria rowCriteria = new RowCriteria(criteria.getIdentifier(), criteria.getType());
        ActionCriteria actionCriteria = new ActionCriteria(rowCriteria, selectAction);
        try {
            return findAction(actionCriteria, dataPointsTable);
        } catch (DynamicElementException e) {
            throw new RuntimeException(e);
        }
    }
}
