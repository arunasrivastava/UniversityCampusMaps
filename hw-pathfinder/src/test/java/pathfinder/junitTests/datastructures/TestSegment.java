package pathfinder.junitTests.datastructures;

import org.junit.BeforeClass;
import org.junit.Test;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestSegment {

    private static final double EPSILON = Math.pow(10.0D, -9.0D);

    private static Path.Segment makeSegment(Point start, Point end, Double cost) {
        Path <Point> path = new Path <Point> (start);
        path = path.extend(end, cost);
        return path.iterator().next();
    }

    @Test
    public void testMakeSegment() {
        Path.Segment segment = makeSegment(new Point(0.0, 0.0), new Point(0.0, 19.0), 19.0D);
        assertEquals(new Point(0.0, 0.0), segment.getStart());
        assertEquals(new Point(0.0, 19.0), segment.getEnd());
        assertEquals(19.0D, segment.getCost(), EPSILON);
    }

    private static Path.Segment seg1, seg2, seg3, seg4, seg5, seg6;

    @BeforeClass
    public static void initializeEqualityTestData() {
        seg1 = makeSegment(new Point(0.0D, 0.0D), new Point(0.0D, 10.0D), 10.0D);
        seg2 = makeSegment(new Point(0.0D, 0.0D), new Point(0.0D, 10.000001D), 10.000001D);
        seg3 = makeSegment(new Point(0.0D, 0.0D), new Point(25.0D, 0.0D), 25.0D);
        seg4 = makeSegment(new Point(0.0D, 0.0D), new Point(10.0D, 0.0D), 10.0D);
        seg5 = makeSegment(new Point(10.0D, 0.0D), new Point(0.0D, 0.0D), 10.0D);
        seg6 = makeSegment(new Point(0.0D, 0.0D), new Point(0.0D, 10.0D), 10.0D);
    }

    @Test
    public void testEquals() {
        assertEquals(seg1, seg1);
        assertNotEquals(seg1, seg2);
        assertNotEquals(seg1, seg3);
        assertNotEquals(seg1, seg4);
        assertNotEquals(seg1, seg5);
        assertEquals(seg1, seg6);
        //
        assertNotEquals(seg2, seg1);
        assertEquals(seg2, seg2);
        assertNotEquals(seg2, seg3);
        assertNotEquals(seg2, seg4);
        assertNotEquals(seg2, seg5);
        assertNotEquals(seg2, seg6);
        //
        assertNotEquals(seg3, seg1);
        assertNotEquals(seg3, seg2);
        assertEquals(seg3, seg3);
        assertNotEquals(seg3, seg4);
        assertNotEquals(seg3, seg5);
        assertNotEquals(seg3, seg6);
        //
        assertNotEquals(seg4, seg1);
        assertNotEquals(seg4, seg2);
        assertNotEquals(seg4, seg3);
        assertEquals(seg4, seg4);
        assertNotEquals(seg4, seg5);
        assertNotEquals(seg4, seg6);
        //
        assertNotEquals(seg5, seg1);
        assertNotEquals(seg5, seg2);
        assertNotEquals(seg5, seg3);
        assertNotEquals(seg5, seg4);
        assertEquals(seg5, seg5);
        assertNotEquals(seg5, seg6);
        //
        assertEquals(seg6, seg1);
        assertNotEquals(seg6, seg2);
        assertNotEquals(seg6, seg3);
        assertNotEquals(seg6, seg4);
        assertNotEquals(seg6, seg5);
        assertEquals(seg6, seg6);
    }

    @Test
    public void testHashcodeConsistent() {
        assertEquals(seg1.hashCode(), seg1.hashCode());
        assertEquals(seg1.hashCode(), seg6.hashCode());
        //
        assertEquals(seg2.hashCode(), seg2.hashCode());
        //
        assertEquals(seg3.hashCode(), seg3.hashCode());
        //
        assertEquals(seg4.hashCode(), seg4.hashCode());
        //
        assertEquals(seg5.hashCode(), seg5.hashCode());
        //
        assertEquals(seg6.hashCode(), seg1.hashCode());
        assertEquals(seg6.hashCode(), seg6.hashCode());
    }
}
