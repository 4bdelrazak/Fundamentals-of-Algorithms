package View;

import DataStructures.Graph;
import Models.Hospital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class GazaMap extends  JFrame{
private static final int GRID_SIZE = 10 ;
private static final int PIXEL_SIZE = 50 ;
private Graph<Point> map ;
private final JComboBox<vertexes> vertexChoiceMenu = new JComboBox<>(new vertexes[]{vertexes.source});
private Point selectedStartVertex = new Point(-1,-1);

private List<Point> shortestPath ;

private vertexes changeSource = vertexes.source ;

private JPanel mapPanel;

private final ArrayList<Hospital> hospitals = new ArrayList<>();

private ArrayList<Point> allPoints  ;

enum vertexes {
    source
}


    public GazaMap() {
        initializeMap();
        createComponents();


        //size of the window
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

private void initializeMap(){
   map = new Graph<>();
   allPoints = new ArrayList<>();
    for (int i = 0; i < GRID_SIZE; i++) {
        for (int j = 0; j < GRID_SIZE; j++) {
            Point topLeft = new Point(i, j);
            Point topRight = new Point(i + 1, j);
            Point bottomLeft = new Point(i, j + 1);
            Point bottomRight = new Point(i + 1, j + 1);
            allPoints.add(topRight);
            allPoints.add(topLeft);
            allPoints.add(bottomLeft);
            allPoints.add(bottomRight);
            map.addEdge(topLeft, topRight, true);
            map.addEdge(topLeft, bottomLeft, true);
            map.addEdge(topRight, bottomRight, true);
            map.addEdge(bottomLeft, bottomRight, true);
        }
    }
}

private void removeRandomEdges(){
    Random r = new Random();
    for (int i = 0; i <r.nextInt(81) + 20 ; i++) {
        map.removeRandomEdge();
    }
}



    private void findShortestPath(Point selectedStartVertex) {
        if (selectedStartVertex == null || hospitals.isEmpty()) {
            System.out.println("Invalid Point or no hospitals available");
            return;
        }

        List<Point> destinationVertices = new ArrayList<>();
        hospitals.sort(Comparator.comparing(Hospital::getPriority).reversed());
        for (Hospital h : hospitals) {
            destinationVertices.add(h.location);
        }

        List<Point> path = new ArrayList<>();
        Point currentSource = selectedStartVertex;

        for (Point destination : destinationVertices) {
            List<Point> partialPath = map.dijkstra(currentSource, destination);
            path.addAll(partialPath.subList(0, partialPath.size() - 1));
            currentSource = destination;
        }

        shortestPath = path;
    }




    private void createComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMap(g);
            }
        };
        mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickHandler(e, mapPanel);
            }
        });


        Dimension buttonSize = new Dimension(150, 25);

        JButton removeRandomEdges  =new JButton("Generate Random Edges");
        removeRandomEdges.addActionListener(e->{
            clearBoard();
            removeRandomEdges();
            mapPanel.repaint();
        });


        JButton generateHospitals  =new JButton("Generate Hospitals");
        generateHospitals.addActionListener(e->{
            clearBoard();
            generateHospitals();
            mapPanel.repaint();

        });
        generateHospitals.setPreferredSize(buttonSize);
        // Buttons Panel
        JButton findPathButton = new JButton("Find Shortest Path");
        findPathButton.addActionListener(e -> {
            findShortestPath(selectedStartVertex);
            mapPanel.repaint();
        });
        findPathButton.setPreferredSize(buttonSize);


        vertexChoiceMenu.addActionListener(e -> changeSource = (vertexes) vertexChoiceMenu.getSelectedItem());
        vertexChoiceMenu.setPreferredSize(buttonSize);

        JPanel buttonsPanel = new JPanel(new GridLayout(12,1));
        buttonsPanel.add(vertexChoiceMenu);
        buttonsPanel.add(removeRandomEdges);
        buttonsPanel.add(generateHospitals);
        buttonsPanel.add(findPathButton);


        // Adjusting sizes
        Dimension mapSize = new Dimension(800, 600);
        mapPanel.setPreferredSize(mapSize);

        // Adding components to mainPanel
        mainPanel.add(mapPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.WEST);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20)); // Adjust as needed

        setLayout(new BorderLayout());
        add(mainPanel);
    }




    private void generateHospitals (){
        Collections.shuffle(allPoints);
        for (int i = 0; i <3 ; i++) {
           hospitals.add(new Hospital(allPoints.get(i)));
        }
//        for (Hospital hospital : hospitals) {
//            System.out.println(hospital);
//        }
    }


    private void drawMap(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(2.0f));



        int offsetX = (getWidth() - PIXEL_SIZE * (GRID_SIZE +6)) / 2;
        int offsetY = (getHeight() - PIXEL_SIZE * (GRID_SIZE +6)) / 2;

        for (Point vertex : map.getVertices()) {
            int x1 = vertex.x * PIXEL_SIZE + offsetX + PIXEL_SIZE / 2;
            int y1 = vertex.y * PIXEL_SIZE + offsetY + PIXEL_SIZE / 2;

            for (Point neighbor : map.getNeighbors(vertex)) {
                // Check if there is an edge between the current vertex and its neighbor
                if (map.hasEdge(vertex, neighbor)) {
                    int x2 = neighbor.x * PIXEL_SIZE + offsetX + PIXEL_SIZE / 2;
                    int y2 = neighbor.y * PIXEL_SIZE + offsetY + PIXEL_SIZE / 2;

                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }


        for (Point vertex : map.getVertices()) {
            int x = vertex.x * PIXEL_SIZE + offsetX;
            int y = vertex.y * PIXEL_SIZE + offsetY;

            int circleSize = 20;
            int circleX = x + (PIXEL_SIZE - circleSize) / 2;
            int circleY = y + (PIXEL_SIZE - circleSize) / 2;

            Color selectedColor = getSelectedColor(vertex);
            g2d.setColor(selectedColor);
            g2d.fillOval(circleX, circleY, circleSize, circleSize);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(circleX, circleY, circleSize, circleSize);
        }



        g2d.setStroke(new BasicStroke(1.0f));
    }

    private Color getSelectedColor(Point vertex) {
        Color selectedColor =Color.white;
        if(shortestPath!=null && shortestPath.contains(vertex)){
        selectedColor=Color.orange;
        }
        if(vertex.equals(selectedStartVertex)){
            selectedColor=Color.PINK;
        }

        for (Hospital currentH : hospitals) {
            if (vertex.equals(currentH.location)) {
                switch (currentH.priority) {
                    case LOW -> selectedColor = Color.green;
                    case MEDIUM -> selectedColor = Color.yellow;
                    case HIGH -> selectedColor = Color.red;
                }

            }
        }
        return selectedColor;
    }


    public void clickHandler(MouseEvent e,JPanel     mapPanel){
        int offsetX = (getWidth() - PIXEL_SIZE * (GRID_SIZE +6)) / 2;
        int offsetY = (getHeight() - PIXEL_SIZE * (GRID_SIZE +6)) / 2;

        for (Point vertex : map.getVertices()) {
            int x = vertex.x * PIXEL_SIZE + offsetX;
            int y = vertex.y * PIXEL_SIZE + offsetY;

            int circleSize = 20;
            int circleX = x + (PIXEL_SIZE - circleSize) / 2;
            int circleY = y + (PIXEL_SIZE - circleSize) / 2;

            if (e.getX() >= circleX && e.getX() <= circleX + circleSize &&
                    e.getY() >= circleY && e.getY() <= circleY + circleSize) {
                switch (changeSource){
                    case source -> selectedStartVertex=vertex;
                }
                mapPanel.repaint();
                break;
            }
        }
    }
    private void clearBoard() {
        selectedStartVertex = null;
        shortestPath = null;
        hospitals.clear();
        mapPanel.repaint();
    }
}
