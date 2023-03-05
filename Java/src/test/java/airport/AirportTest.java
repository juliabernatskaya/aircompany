package airport;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import plane.ExperimentalPlane;
import plane.classification.ClassificationLevel;
import plane.type.ExperimentalType;
import plane.type.MilitaryType;
import org.testng.annotations.Test;
import plane.MilitaryPlane;
import plane.PassengerPlane;
import plane.Plane;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class AirportTest {
    private static final List<Plane> planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalType.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalType.VTOL, ClassificationLevel.TOP_SECRET)
    );
    private static final PassengerPlane planeWithMaxPassengerCapacity = new PassengerPlane(
            "Boeing-747", 980, 16100, 70500, 242);
    private Airport airport;

    @BeforeTest
    void init() {
        airport = new Airport(planes);
    }

    @Test
    void testGetAllPlanes() {
        assertEquals(planes, airport.getAllPlanes());
    }

    @Test
    void testGetPassengerPlanes() {
        assertEquals(8, airport.getPassengerPlanes().size());
    }

    @Test
    void testGetMilitaryPlanes() {
        assertEquals(6, airport.getMilitaryPlanes().size());
    }

    @Test
    void testGetTransportMilitaryPlanes() {
        assertTrue(airport.getTransportMilitaryPlanes().stream().allMatch(plane ->
                plane.getMilitaryType() == MilitaryType.TRANSPORT));
    }

    @Test
    void testGetBomberMilitaryPlanes() {
        assertTrue(airport.getBomberMilitaryPlanes().stream().allMatch(plane ->
                plane.getMilitaryType() == MilitaryType.BOMBER));
    }

    @Test
    void getExperimentalPlanes() {
        assertEquals(2, airport.getExperimentalPlanes().size());
    }

    @Test
    public void testGetPassengerPlaneWithMaxCapacity() {
        assertEquals(planeWithMaxPassengerCapacity, airport.getPassengerPlaneWithMaxPassengersCapacity());
    }

    @Test
    void testSortByMaxFlightDistance() {
        assertEquals(planes.stream().sorted(Comparator.comparingInt(Plane::getMaxFlightDistance)).collect(Collectors.toList()),
                airport.sortByMaxFlightDistance().getAllPlanes());
    }

    @Test
    public void testSortByMaxSpeed() {
        assertEquals(planes.stream().sorted(Comparator.comparingInt(Plane::getMaxSpeed)).collect(Collectors.toList()),
                airport.sortByMaxSpeed().getAllPlanes());
    }

    @Test
    void testSortByMaxLoadCapacity() {
        assertEquals(planes.stream().sorted(Comparator.comparingInt(Plane::getMaxLoadCapacity)).collect(Collectors.toList()),
                airport.sortByMaxLoadCapacity().getAllPlanes());
    }

    @Test
    @Ignore("this test seems to be useless until we add constrains for ExperimentalPlane object creation")
    void testExperimentalPlanesHasClassificationLevelHigherThanUnclassified(){
        assertTrue(airport.getExperimentalPlanes().stream().noneMatch(plane ->
                plane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED));
    }
}
