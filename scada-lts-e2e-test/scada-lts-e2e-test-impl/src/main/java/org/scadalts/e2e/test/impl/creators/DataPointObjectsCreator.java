package org.scadalts.e2e.test.impl.creators;


import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

public class DataPointObjectsCreator implements CreatorObject<EditDataSourceWithPointListPage, EditDataSourceWithPointListPage> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull DataSourceCriteria dataSourceCriteria;
    private final @NonNull DataPointCriteria[] dataPointCriterias;
    private DataSourcesPage dataSourcesPage;

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull DataSourceCriteria dataSourceCriteria) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourceCriteria;
        this.dataPointCriterias = new DataPointCriteria[]{DataPointCriteria.binaryAlternate()};
    }

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull DataSourceCriteria dataSourceCriteria, @NonNull DataPointCriteria... dataPointCriterias) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourceCriteria;
        this.dataPointCriterias = dataPointCriterias;
    }

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull DataSourcePointCriteria dataSourcePointCriteria) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourcePointCriteria.getDataSource();
        this.dataPointCriterias = new DataPointCriteria[]{dataSourcePointCriteria.getDataPoint()};
    }

    @Override
    public EditDataSourceWithPointListPage deleteObjects() {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = openPage();
        for (DataPointCriteria dataPointCriteria : dataPointCriterias) {
            if(editDataSourceWithPointListPage.containsObject(dataPointCriteria)) {
                _deleteDataPoint(editDataSourceWithPointListPage, dataPointCriteria);
            }
        }
        return editDataSourceWithPointListPage;
    }

    @Override
    public EditDataSourceWithPointListPage createObjects() {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = openPage();
        return createObjects(editDataSourceWithPointListPage);
    }

    public EditDataSourceWithPointListPage createObjects(EditDataSourceWithPointListPage editDataSourceWithPointListPage) {
        for (DataPointCriteria dataPointCriteria : dataPointCriterias) {
            if(!editDataSourceWithPointListPage.containsObject(dataPointCriteria)) {
                _createDataPoint(editDataSourceWithPointListPage, dataPointCriteria);
            }
        }
        return editDataSourceWithPointListPage.enableAllDataPoint();
    }

    @Override
    public EditDataSourceWithPointListPage openPage() {
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage.openDataSourceEditor(dataSourceCriteria);
        }
        return dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria);
    }

    private EditDataPointPage _createDataPoint(EditDataSourceWithPointListPage page, DataPointCriteria pointParams) {
        return page.addDataPoint()
                .setDataPointName(pointParams.getIdentifier())
                .selectDataPointType(pointParams.getType())
                .setSettable(pointParams.isSettable())
                .selectChangeType(pointParams.getChangeType())
                .setDataPointXid(pointParams.getXid())
                .setStartValue(pointParams)
                .saveDataPoint();
    }

    private EditDataSourceWithPointListPage _deleteDataPoint(EditDataSourceWithPointListPage page, DataPointCriteria pointParams) {
        return page.openDataPointEditor(pointParams)
                .deleteDataPoint();
    }
}
