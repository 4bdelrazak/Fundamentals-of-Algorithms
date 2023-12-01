package DataStructures;

import java.awt.*;
import java.util.List;

public class TestGraph {
    public static void main(String[] args) {
        // Assuming you have a constant GRID_SIZE defined somewhere
        int GRID_SIZE = 5;

        // Create a new graph of Point objects
        Graph<Point> map = new Graph<>();

        // Add edges for a grid of points
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Point topLeft = new Point(i, j);
                Point topRight = new Point(i + 1, j);
                Point bottomLeft = new Point(i, j + 1);
                Point bottomRight = new Point(i + 1, j + 1);

                map.addEdge(topLeft, topRight, true);
                map.addEdge(topLeft, bottomLeft, true);
                map.addEdge(topRight, bottomRight, true);
                map.addEdge(bottomLeft, bottomRight, true);
            }
        }

        // Print the adjacency list of the graph
        System.out.println("Graph:");
        System.out.println(map);

        // Choose start and destination points for Dijkstra's algorithm
        Point start = new Point(0, 0);
        Point destination = new Point(GRID_SIZE - 1, GRID_SIZE - 1);

        // Find the shortest path from start to destination
        List<Point> shortestPath = map.dijkstra(start, destination);

        // Print the result
        System.out.println("\nShortest path from " + start + " to " + destination + ":");
        if (shortestPath != null) {
            for (Point point : shortestPath) {
                System.out.print(point + " ");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}
