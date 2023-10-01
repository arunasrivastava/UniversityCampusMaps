package pathfinder.junitTests.datastructures;

import org.junit.BeforeClass;
import org.junit.Test;
import pathfinder.datastructures.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestPoint {

    private static final double EPSILON = Math.pow(10.0D, -9.0D); // Accuracy required to 1 PPB

    @Test
    public void testXCorrect() {
        Point point = new Point(15.5D, 19.0D);
        Point point2 = new Point(19.0D, 37.5D);
        assertEquals(15.5D, point.getX(), EPSILON);
        assertEquals(19.0D, point2.getX(), EPSILON);
    }

    @Test
    public void testRightCorrect() {
        Point point = new Point(15.5D, 19.5D);
        Point point2 = new Point(19.5D, 37.5D);
        assertEquals(19.5D, point.getY(), EPSILON);
        assertEquals(37.5D, point2.getY(), EPSILON);
        assertNotEquals(45D, point2.getY());
    }

    private static Point point1;
    private static Point point2;
    private static Point point3;
    private static Point point4;
    private static Point point5;

    @BeforeClass
    public static void initializeEqualityTests() {
        point1 = new Point(25D, 42D);
        point2 = new Point(331D, 143D);
        point3 = new Point(25D, 19.5D);
        point4 = new Point(19.5D, 25D);
        point5 = new Point(25D, 42D);
    }

    @Test
    public void testEquals() {
        assertEquals(point1, point1);
        assertNotEquals(point1, point2);
        assertNotEquals(point1, point3);
        assertNotEquals(point1, point4);
        assertEquals(point1, point5);
        //
        assertNotEquals(point2, point1);
        assertEquals(point2, point2);
        assertNotEquals(point2, point3);
        assertNotEquals(point2, point4);
        assertNotEquals(point2, point5);
        //
        assertNotEquals(point3, point1);
        assertNotEquals(point3, point2);
        assertEquals(point3, point3);
        assertNotEquals(point3, point4);
        assertNotEquals(point3, point5);
        //
        assertNotEquals(point4, point1);
        assertNotEquals(point4, point2);
        assertNotEquals(point4, point3);
        assertEquals(point4, point4);
        assertNotEquals(point4, point5);
        //
        assertEquals(point5, point1);
        assertNotEquals(point5, point2);
        assertNotEquals(point5, point3);
        assertNotEquals(point5, point4);
        assertEquals(point5, point5);
    }

    @Test
    public void testHashcodeConsistent() {
        assertEquals(point1.hashCode(), point1.hashCode());
        assertEquals(point1.hashCode(), point5.hashCode());
        //
        assertEquals(point2.hashCode(), point2.hashCode());
        //
        assertEquals(point3.hashCode(), point3.hashCode());
        //
        assertEquals(point4.hashCode(), point4.hashCode());
        //
        assertEquals(point5.hashCode(), point1.hashCode());
        assertEquals(point5.hashCode(), point5.hashCode());
    }
}
