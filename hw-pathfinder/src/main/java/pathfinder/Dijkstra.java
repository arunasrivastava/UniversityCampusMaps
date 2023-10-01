package pathfinder;

import graph.*;
import pathfinder.datastructures.Path;

import java.util.HashSet;
import java.util.PriorityQueue;

public class Dijkstra {
    /**
     * @spec.requires g's edges are all doubles, and are all non-negative doubles, and not null
     * @spec.requires n1, n2, and g are not null
     * @throws IllegalArgumentException if n1 or n2 is not in g
     * @param g a graph
     * @param n1 starting node
     * @param n2 ending node
     * @return a path, representing min cost path from n1 to n2, null if path doesn't exist
     */
    public static <N> Path<N> FindPath (Graph<N, Double> g, N n1, N n2){
        // Dijkstra's algorithm assumes a graph with nonnegative edge weights.
        N start = n1;
        N dest = n2;

        // Priority Queue consisting of path weights
        PriorityQueue <Path<N>> active = new PriorityQueue <Path<N>> ();
        // Each element is a path from start to a given node.
        // A path's “priority” in the queue is the total cost of that path.
        // Nodes for which no path is known yet are not in the queue.

        // Set of nodes containing the minimum cost path from the starting node
        HashSet <N> finished = new HashSet<N>();
        Path <N> startPath = new Path<N>(start);

        // Initially we only know of the path from start to itself, which has
        // a cost of zero because it contains no edges.
        active.add(startPath);
        while(!active.isEmpty()){
            // minPath is the lowest-cost path in active
            Path <N> minPath = active.poll();
            // if minDest isn't already 'finished,' is the
            // minimum-cost path to the node minDest
            N minDest = minPath.getEnd();
            if (minDest.equals(dest)) {
                return minPath;
            }
            if (finished.contains(minDest)) {
                continue;
            }
            for (Graph.GraphEdge<N, Double> e : g.findChildren(minDest)) {
                // If we don't know the minimum-cost path from start to child,
                // examine the path we've just found
                if (!finished.contains(e.getChild())) {
                    Path<N> newPath = minPath.extend(e.getChild(), e.getEdgeLabel());
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }
        // If the loop terminates, then no path exists from start to dest.
        return null;
    }



}
