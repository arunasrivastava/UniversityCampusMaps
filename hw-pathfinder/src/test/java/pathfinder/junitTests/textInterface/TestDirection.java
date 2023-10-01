package pathfinder.junitTests.textInterface;

import org.junit.Test;
import pathfinder.textInterface.CoordinateProperties;
import pathfinder.textInterface.Direction;

import static org.junit.Assert.assertEquals;

public class TestDirection {

    @Test(expected = IllegalArgumentException.class)
    public void testResolveDirectionNan() {
        Direction.resolveDirection(Double.NaN, 0.0D, 1.0D, 1.0D,
                                   CoordinateProperties.INCREASING_UP_RIGHT);
    }

    @Test
    public void testResolveDirectionBasic() {
        Direction d1 = Direction.resolveDirection(1.0D, 1.0D, 2.0D, 2.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.NE, d1);
        Direction d2 = Direction.resolveDirection(1.0D, 1.0D, -2.0D, -2.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.SW, d2);
        Direction d3 = Direction.resolveDirection(1.0D, 1.0D, 2.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.E, d3);
        Direction d4 = Direction.resolveDirection(1.0D, 1.0D, 1.0D, 2.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.N, d4);
        Direction d5 = Direction.resolveDirection(1.0D, 1.0D, 1.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.S, d5);
        Direction d6 = Direction.resolveDirection(1.0D, 1.0D, 0.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.W, d6);
        Direction d7 = Direction.resolveDirection(1.0D, 1.0D, 0.0D, 2.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.NW, d7);
        Direction d8 = Direction.resolveDirection(1.0D, 1.0D, 2.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.SE, d8);
    }

    @Test
    public void testResolveDirectionBasicOrigin() {
        Direction d1 = Direction.resolveDirection(1.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.NE, d1);
        Direction d2 = Direction.resolveDirection(-1.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.SW, d2);
        Direction d3 = Direction.resolveDirection(1.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.E, d3);
        Direction d4 = Direction.resolveDirection(0.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.N, d4);
        Direction d5 = Direction.resolveDirection(0.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.S, d5);
        Direction d6 = Direction.resolveDirection(-1.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.W, d6);
        Direction d7 = Direction.resolveDirection(-1.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.NW, d7);
        Direction d8 = Direction.resolveDirection(1.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_UP_RIGHT);
        assertEquals(Direction.SE, d8);
    }

    @Test
    public void testResolveDirectionDownLeft() {
        Direction d1 = Direction.resolveDirection(1.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_LEFT);
        assertEquals(Direction.SW, d1);
        Direction d2 = Direction.resolveDirection(-1.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_LEFT);
        assertEquals(Direction.NE, d2);
        Direction d3 = Direction.resolveDirection(1.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_DOWN_LEFT);
        assertEquals(Direction.W, d3);
        Direction d4 = Direction.resolveDirection(0.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_LEFT);
        assertEquals(Direction.S, d4);
        Direction d5 = Direction.resolveDirection(0.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_LEFT);
        assertEquals(Direction.N, d5);
        Direction d6 = Direction.resolveDirection(-1.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_DOWN_LEFT);
        assertEquals(Direction.E, d6);
        Direction d7 = Direction.resolveDirection(-1.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_LEFT);
        assertEquals(Direction.SE, d7);
        Direction d8 = Direction.resolveDirection(1.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_LEFT);
        assertEquals(Direction.NW, d8);
    }

    @Test
    public void testResolveDirectionDownRight() {
        Direction d1 = Direction.resolveDirection(1.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_RIGHT);
        assertEquals(Direction.SE, d1);
        Direction d2 = Direction.resolveDirection(-1.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_RIGHT);
        assertEquals(Direction.NW, d2);
        Direction d3 = Direction.resolveDirection(1.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_DOWN_RIGHT);
        assertEquals(Direction.E, d3);
        Direction d4 = Direction.resolveDirection(0.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_RIGHT);
        assertEquals(Direction.S, d4);
        Direction d5 = Direction.resolveDirection(0.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_RIGHT);
        assertEquals(Direction.N, d5);
        Direction d6 = Direction.resolveDirection(-1.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_DOWN_RIGHT);
        assertEquals(Direction.W, d6);
        Direction d7 = Direction.resolveDirection(-1.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_RIGHT);
        assertEquals(Direction.SW, d7);
        Direction d8 = Direction.resolveDirection(1.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_DOWN_RIGHT);
        assertEquals(Direction.NE, d8);
    }

    @Test
    public void testResolveDirectionUpLeft() {
        Direction d1 = Direction.resolveDirection(1.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_UP_LEFT);
        assertEquals(Direction.NW, d1);
        Direction d2 = Direction.resolveDirection(-1.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_UP_LEFT);
        assertEquals(Direction.SE, d2);
        Direction d3 = Direction.resolveDirection(1.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_UP_LEFT);
        assertEquals(Direction.W, d3);
        Direction d4 = Direction.resolveDirection(0.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_UP_LEFT);
        assertEquals(Direction.N, d4);
        Direction d5 = Direction.resolveDirection(0.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_UP_LEFT);
        assertEquals(Direction.S, d5);
        Direction d6 = Direction.resolveDirection(-1.0D, 0.0D,
                                                  CoordinateProperties.INCREASING_UP_LEFT);
        assertEquals(Direction.E, d6);
        Direction d7 = Direction.resolveDirection(-1.0D, 1.0D,
                                                  CoordinateProperties.INCREASING_UP_LEFT);
        assertEquals(Direction.NE, d7);
        Direction d8 = Direction.resolveDirection(1.0D, -1.0D,
                                                  CoordinateProperties.INCREASING_UP_LEFT);
        assertEquals(Direction.SW, d8);
    }
}
