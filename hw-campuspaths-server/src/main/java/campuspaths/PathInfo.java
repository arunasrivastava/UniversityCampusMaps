package campuspaths;

/**
 * PathInfo is a  .an abstract data type that represents the path between two points with x and y coordinates.
 */
public class PathInfo {
    private double x1, y1, x2, y2;

    /**
     * Sets the values of the coordinates of this PathInfo to the given coordinates
     * @param x1 x coordinate 1
     * @param y1 y coordinate 1
     * @param x2 x coordinate 2
     * @param y2 y coordinate 2
     */
    public PathInfo(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}