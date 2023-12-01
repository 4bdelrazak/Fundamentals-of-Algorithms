package DataStructures;

import java.util.*;

public class Graph<T> {
    //T generic type
    //list of vertices list<T>
    // We use Hashmap to store the edges in the graph
    private final Map<T, List<T> > map = new HashMap<>();

    // This function adds a new vertex to the graph
    public void addVertex(T s)
    {
        map.put(s, new LinkedList<T>());
    }

    // This function adds the edge
    // between source to destination



    public void addEdge(T source,
                        T destination,
                        boolean bidirectional)
    {

        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);

        map.get(source).add(destination);
        if (bidirectional) {
            map.get(destination).add(source);
        }
    }
    public boolean removeEdge(T source, T destination) {
        if (map.containsKey(source) && map.containsKey(destination)) {
            boolean removedFromSource = map.get(source).remove(destination);
            boolean removedFromDestination = map.get(destination).remove(source);

            return removedFromSource && removedFromDestination;
        } else {
            return false;
        }
    }


    // This function gives the count of vertices
    public void getVertexCount()
    {
        System.out.println("The graph has "
                + map.keySet().size()
                + " vertex");
    }

    // This function gives the count of edges
    public void getEdgesCount(boolean bidirection)
    {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection) {
            count = count / 2;
        }
        System.out.println("The graph has "
                + count
                + " edges.");
    }

    // This function gives whether
    // a vertex is present or not.
    public void hasVertex(T s)
    {
        if (map.containsKey(s)) {
            System.out.println("The graph contains "
                    + s + " as a vertex.");
        }
        else {
            System.out.println("The graph does not contain "
                    + s + " as a vertex.");
        }
    }

    // This function gives whether an edge is present or not.
    public boolean hasEdge(T s, T d)
    {
        return map.get(s).contains(d);
    }

    // Prints the adjancency list of each vertex.
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for (T v : map.keySet()) {
            builder.append(v.toString()).append(": ");
            for (T w : map.get(v)) {
                builder.append(w.toString()).append(" ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }

    public List<T> getNeighbors(T vertex) {
        return map.get(vertex);
    }

    public Set<T> getVertices() {
        return map.keySet();
    }
    public List<T> dijkstra(T start, T destination) {
        Map<T, Integer> distance = new HashMap<>();
        Map<T, T> previous = new HashMap<>();
        PriorityQueue<T> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));

        for (T vertex : map.keySet()) {
            distance.put(vertex, Integer.MAX_VALUE);
            previous.put(vertex, null);
        }

        distance.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            T current = queue.poll();

            for (T neighbor : map.get(current)) {
                int newDistance = distance.get(current) + 1; // Assuming all edges have weight 1

                if (newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<T> path = new ArrayList<>();
        T current = destination;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);
        return path;
    }



    public void removeRandomEdge() {
        // Check if the graph has any edges
        if (map.isEmpty()) {
            System.out.println("The graph is empty. Cannot remove an edge.");
            return;
        }

        List<T> vertices = new ArrayList<>(map.keySet());
        Random random = new Random();
        T randomVertex = vertices.get(random.nextInt(vertices.size()));

        List<T> edges = map.get(randomVertex);
        if (edges.isEmpty()) {
            return;
        }

        T randomEdge = edges.get(random.nextInt(edges.size()));

        removeEdge(randomVertex, randomEdge);

    }
}


