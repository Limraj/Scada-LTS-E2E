package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.creators.AlarmsAndStorungsObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class GetLiveStructureServiceTest {

    @Parameterized.Parameters(name = "{index}: offset: {0}, limit: {1}")
    public static Object[][] data() {
        return new Object[][] {
                {0, 12}, {1, 11}, {2, 10}, {3, 9}, {4, 8}, {5, 7}, {6, 6}, {7, 5},
                {8, 4}, {9, 3}, {10, 4}, {11, 1}, {12, 0}, {8, 2}, {9, 1}, {10, 0},
                {0, 2}, {2, 2}, {4, 2}, {6, 2}, {8, 2}, {10, 2}, {0, 3}, {3, 3},
                {6, 3}, {9, 3}, {0, 4}, {4, 4}, {8, 4}, {0, 5}, {5, 5}, {10, 2},
                {0, 6}, {6, 6}, {0, 7}, {7, 5}, {0, 8}, {8, 4}, {0, 9999}
        };
    }

    private final int offset;
    private final int limit;

    public GetLiveStructureServiceTest(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    private static AlarmsAndStorungsObjectsCreator activePoints;
    private static AlarmsAndStorungsObjectsCreator inactivePoints;

    @BeforeClass
    public static void setup() {

        DataPointIdentifier[] activeIdnetifiers = new DataPointIdentifier[] {

                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),

                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
        };

        DataPointIdentifier[] inactiveIdnetifiers = new DataPointIdentifier[] {

                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),

                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
        };

        DataPointCriteria[] activeNotifierAlarams = Stream.of(activeIdnetifiers)
                .map(a -> DataPointCriteria.noChange(a, "1"))
                .toArray(a -> new DataPointCriteria[activeIdnetifiers.length]);

        DataPointCriteria[] inactiveNotifierAlarams = Stream.of(inactiveIdnetifiers)
                .map(a -> DataPointCriteria.noChange(a, "1"))
                .toArray(a -> new DataPointCriteria[inactiveIdnetifiers.length]);


        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        activePoints = new AlarmsAndStorungsObjectsCreator(navigationPage, activeNotifierAlarams);
        activePoints.createObjects();

        inactivePoints = new AlarmsAndStorungsObjectsCreator(navigationPage, inactiveNotifierAlarams);
        inactivePoints.createObjects();
        inactivePoints.setDataPointValue(0);

        AlarmsAndStorungsUtil.getAlarmsAndStorungs(PaginationParams.builder()
                .offset(0)
                .limit(9999)
                .build(), 12);

        AlarmsAndStorungsUtil.getActiveAlarmsAndStorungs(PaginationParams.builder()
                .offset(0)
                .limit(9999)
                .build(), 6);

        AlarmsAndStorungsUtil.getInactiveAlarmsAndStorungs(PaginationParams.builder()
                .offset(0)
                .limit(9999)
                .build(), 6);
    }

    @AfterClass
    public static void clean() {

        activePoints.deleteObjects();
        inactivePoints.deleteObjects();
    }

    @Test
    public void test_sort_structure_active_lives() {

        //when:
        List<AlarmResponse> responses = AlarmsAndStorungsUtil.getActiveAlarmsAndStorungs(PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build());

        List<AlarmResponse> sorted = AlarmsAndStorungsUtil.sortByActivationTime(responses);

        //then:
        assertEquals(sorted, responses);
    }

    @Test
    public void test_sort_structure_inactive_lives() {

        //when:
        List<AlarmResponse> responses = AlarmsAndStorungsUtil.getInactiveAlarmsAndStorungs(PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build());

        List<AlarmResponse> sorted = AlarmsAndStorungsUtil.sortByActivationTime(responses);

        //then:
        assertEquals(sorted, responses);
    }

    @Test
    public void test_ref_structure_live() {

        //when:
        List<AlarmResponse> responses = AlarmsAndStorungsUtil.getAlarmsAndStorungs(PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build());

        List<AlarmResponse> ref = AlarmsAndStorungsUtil.getReferenceStructure(responses);

        //then:
        assertEquals(ref, responses);
    }

}
