package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.scadalts.e2e.page.core.criterias.Tag.tr;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;
import static org.scadalts.e2e.page.impl.criterias.CriteriaUtil.createCriteriaStructure;

@Log4j2
public class DataSourcePointObjectsCreator implements CreatorObject<DataSourcesPage, DataSourcesPage> {

    private final NavigationPage navigationPage;
    private final Map<DataSourceCriteria, DataPointObjectsCreator> dataSources;

    private static final NodeCriteria ALL_DATA_SOURCES = NodeCriteria.every(1, 0, tr(), clazz("row"));

    @Getter
    private DataSourcesPage dataSourcesPage;

    public DataSourcePointObjectsCreator(NavigationPage navigationPage, DataSourceCriteria dataSourceCriteria, DataPointCriteria... dataPointCriteria) {
        this.dataSources = _convert(navigationPage, createCriteriaStructure(dataSourceCriteria, dataPointCriteria));
        this.navigationPage = navigationPage;
    }

    public DataSourcePointObjectsCreator(NavigationPage navigationPage, DataPointCriteria... dataPointCriterias) {
        this.dataSources = _convert(navigationPage, createCriteriaStructure(dataPointCriterias));
        this.navigationPage = navigationPage;
    }

    public DataSourcePointObjectsCreator(NavigationPage navigationPage, DataSourcePointCriteria... dataPointCriterias) {
        this.dataSources = _convert(navigationPage, createCriteriaStructure(dataPointCriterias));
        this.navigationPage = navigationPage;
    }

    @Override
    public DataSourcesPage createObjects() {
        return _createDataSourcesAndPoints();
    }

    @Override
    public DataSourcesPage openPage() {
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage;
        }
        return dataSourcesPage.reopen();
    }

    @Override
    public DataSourcesPage deleteObjects() {
        return _deleteDataPointsAndDataSources(dataSources);
    }

    public EditDataSourceWithPointListPage createDataSources() {
        EditDataSourceWithPointListPage page = new EditDataSourceWithPointListPage();
        DataSourcesPage dataSourcesPage = openPage();
        for (DataSourceCriteria criteria : dataSources.keySet()) {
            page = _createDataSource(dataSourcesPage, criteria);
        }
        return page;
    }

    public static DataSourcesPage deleteAllDataSourcesTest(NavigationPage navigationPage) {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(new DataSourceIdentifier("ds_test",
                DataSourceType.VIRTUAL_DATA_SOURCE), tr(), clazz("row"));
        dataSourcesPage.deleteAllDataSourcesMatching(nodeCriteria);
        return dataSourcesPage;
    }

    public static DataSourcesPage disableAllDataSources(NavigationPage navigationPage) {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        dataSourcesPage.disableAllDataSourcesMatching(ALL_DATA_SOURCES);
        return dataSourcesPage;
    }

    public static DataSourcesPage enableAllDataSources(NavigationPage navigationPage) {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        dataSourcesPage.enableAllDataSourcesMatching(ALL_DATA_SOURCES);
        return dataSourcesPage;
    }

    private DataSourcesPage _deleteDataPointsAndDataSources(Map<DataSourceCriteria, DataPointObjectsCreator> criteriaMap) {
        DataSourcesPage page = openPage();
        for (DataSourceCriteria criteria : criteriaMap.keySet()) {
            if(page.containsObject(criteria.getIdentifier())) {

                logger.info("delete object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getIdentifier().getType(), criteria.getXid().getValue(), criteria.getClass().getSimpleName());

                page.deleteDataSource(criteria.getIdentifier());
            }
        }
        return page;
    }

    private EditDataSourceWithPointListPage _createDataSource(DataSourcesPage page, DataSourceCriteria criteria) {

        logger.info("create object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                criteria.getIdentifier().getType(), criteria.getXid().getValue(), criteria.getClass().getSimpleName());

        return page.openDataSourceCreator(criteria.getIdentifier().getType())
                .selectUpdatePeriodType(criteria.getUpdatePeriodType())
                .setUpdatePeriods(criteria.getUpdatePeriodValue())
                .setDataSourceName(criteria.getIdentifier())
                .setDataSourceXid(criteria.getXid())
                .saveDataSource()
                .enableDataSource(true);
    }

    private DataSourcesPage _createDataSourcesAndPoints() {
        DataSourcesPage dataSourcesPage = openPage();
        for (DataSourceCriteria criteria : dataSources.keySet()) {
            if(!dataSourcesPage.containsObject(criteria.getIdentifier())) {
                EditDataSourceWithPointListPage editDataSourceWithPointListPage = _createDataSource(dataSourcesPage, criteria);
                DataPointObjectsCreator creator = dataSources.get(criteria);
                creator.createObjects(editDataSourceWithPointListPage);
                dataSourcesPage.reopen();
            }
        }
        return dataSourcesPage;
    }

    private static Map<DataSourceCriteria, DataPointObjectsCreator> _convert(NavigationPage navigationPage, Map<DataSourceCriteria, List<DataPointCriteria>> sources) {
        return sources.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry ->
                        new DataPointObjectsCreator(navigationPage, entry.getKey(), entry.getValue()
                                .toArray(new DataPointCriteria[]{}))));
    }
}
