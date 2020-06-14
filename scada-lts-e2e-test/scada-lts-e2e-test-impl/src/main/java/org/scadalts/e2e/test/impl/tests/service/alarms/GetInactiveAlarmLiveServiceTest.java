package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class GetInactiveAlarmLiveServiceTest {

    @Parameterized.Parameters(name = "{index}: data point name: {0}, alarm level: {1}")
    public static Object[][] data() {
        return new Object[][] {
                {IdentifierObjectFactory.dataPointAlarmName(DataPointType.BINARY), AlarmLevel.INFORMATION},
                {IdentifierObjectFactory.dataPointStorungName(DataPointType.BINARY), AlarmLevel.URGENT},
        };
    }

    private final DataPointIdentifier dataPointIdentifier;
    private final AlarmLevel alarmLevel;

    public GetInactiveAlarmLiveServiceTest(DataPointIdentifier identifier, AlarmLevel alarmLevel) {
        this.dataPointIdentifier = identifier;
        this.alarmLevel = alarmLevel;
    }

    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private WatchListObjectsCreator watchListObjectsCreator;
    private List<AlarmResponse> alarmResponse;

    @BeforeClass
    public void setup() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria point = DataPointCriteria.noChange(dataPointIdentifier, "0");

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, point);
        dataSourcePointObjectsCreator.createObjects();

        DataSourcePointIdentifier identifier = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(),
                dataPointIdentifier);

        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, WatchListCriteria.criteria(identifier));
        watchListObjectsCreator.createObjects();

        navigationPage.openWatchList()
                .setValue(identifier, "1")
                .setValue(identifier, "0");

        //given:
        PaginationParams paginationParams = PaginationParams.builder()
                .limit(Integer.MAX_VALUE)
                .offset(0)
                .build();

        //when:
        E2eResponse<List<AlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        List<AlarmResponse> getResult = getResponse.getValue();
        alarmResponse = AlarmsAndStorungsUtil.getAlarmsFor(dataPointIdentifier, getResult);
    }

    @AfterClass
    public void clean() {

        watchListObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_alarm_response_activation_time_then_not_empty() {
        //then:
        assertEquals(1, alarmResponse.size());
        assertNotEquals("", alarmResponse.get(0).getActivationTime());
    }

    @Test
    public void test_alarm_response_inactivation_time_then_empty() {
        //then:
        assertEquals(1, alarmResponse.size());
        assertEquals("", alarmResponse.get(0).getInactivationTime());
    }

    @Test
    public void test_alarm_response_name_then_point_name() {
        //then:
        assertEquals(1, alarmResponse.size());
        assertEquals(dataPointIdentifier.getValue(), alarmResponse.get(0).getName());
    }

    @Test
    public void test_alarm_response_level_then_AlarmLevel() {
        //then:
        assertEquals(1, alarmResponse.size());
        assertEquals(alarmLevel.getId(), alarmResponse.get(0).getLevel());
    }
}