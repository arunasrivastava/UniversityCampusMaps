package pathfinder.junitTests.datastructures;

import org.junit.BeforeClass;
import org.junit.Test;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestPath {

    private static final double EPSILON = Math.pow(10.0D, -9.0D); // Accuracy required to 1 PPB

    @Test
    public void testEmptyPathHasNoDistance() {
        Path <Point> path = new Path <Point> (new Point(1.0, 2.0));
        assertEquals(0, path.getCost(), EPSILON);
    }

    @Test
    public void testEmptyPathLastNode() {
        Path <Point> path = new Path <Point> (new Point(1.0, 2.0));
        assertEquals(new Point(1.0, 2.0), path.getEnd());
        assertEquals(path.getStart(), path.getEnd());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorUnsupportedRemove() {
        Path <Point> path = new Path<Point>(new Point(1.0, 2.0));
        path = path.extend(new Point(3.0, 2.0), 2.0D);
        Iterator<Path<Point>.Segment> iterator = path.iterator();
        // Next must be called before remove or the iterator would be in an illegal state.
        iterator.next();
        iterator.remove();
    }

    @Test
    public void testExtendPathLastNodeUpdate() {
        Path <Point> path = new Path<Point>(new Point(1.0, 2.0));
        assertEquals(new Point(1.0, 2.0), path.getEnd());
        path = path.extend(new Point(3.0, 2.0), 2.0D);
        assertEquals(new Point(3.0, 2.0), path.getEnd());
    }

    @Test
    public void testExtendPathDistanceUpdate() {
        Path <Point> path = new Path <Point>(new Point(1.0, 2.0));
        assertEquals(0, path.getCost(), 1.0D / (1000.0D * 1000.0D * 1000.0D));
        path = path.extend(new Point(3.0, 2.0), 2.0D);
        assertEquals(2.0D, path.getCost(), 1.0D / (1000.0D * 1000.0D * 1000.0D));
    }

    @Test
    public void testNonemptyPathEquality() {
        Path <Point> path1 = new Path <Point>(new Point(1.0, 2.0));
        path1 = path1.extend(new Point(3.0, 2.0), 2.0D);
        Path <Point> path2 = new Path <Point>(new Point(1.0, 2.0));
        path2 = path2.extend(new Point(3.0, 2.0), 2.0D);
        Path <Point> path3 = new Path <Point>(new Point(3.0, 4.0));
        path3 = path3.extend(new Point(3.0, 4.0), 2.0D);
        assertEquals(path1, path2);
        assertEquals(path2, path1);
        assertNotEquals(path1, path3);
        assertNotEquals(path2, path3);
        assertNotEquals(path3, path1);
        assertNotEquals(path3, path1);
        assertEquals(path1, path1);
        assertEquals(path2, path2);
        assertEquals(path3, path3);
    }

    private static Path <Point> path1, path2, path3;

    @BeforeClass
    public static void initializeEqualityTests() {
        path1 = new Path <Point>(new Point(1.0, 2.0));
        path2 = new Path <Point>(new Point(1.0, 2.0));
        path3 = new Path <Point>(new Point(3.0, 4.0));
    }

    @Test
    public void testEmptyPathEquality() {
        assertEquals(path1, path2);
        assertEquals(path2, path1);
        assertNotEquals(path1, path3);
        assertNotEquals(path2, path3);
        assertNotEquals(path3, path1);
        assertNotEquals(path3, path1);
        assertEquals(path1, path1);
        assertEquals(path2, path2);
        assertEquals(path3, path3);
    }

    @Test
    public void testPathHashcodeConsistent() {
        assertEquals(path1.hashCode(), path2.hashCode());
        assertEquals(path2.hashCode(), path1.hashCode());
        assertNotEquals(path1.hashCode(), path3.hashCode());
        assertNotEquals(path2.hashCode(), path3.hashCode());
        assertNotEquals(path3.hashCode(), path1.hashCode());
        assertNotEquals(path3.hashCode(), path1.hashCode());
        assertEquals(path1.hashCode(), path1.hashCode());
        assertEquals(path2.hashCode(), path2.hashCode());
        assertEquals(path3.hashCode(), path3.hashCode());
    }
}
