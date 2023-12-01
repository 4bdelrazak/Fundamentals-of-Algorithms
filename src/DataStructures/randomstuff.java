package DataStructures;

import java.util.HashMap;
import java.util.Map;

public class randomstuff {

    public static void main(String[] args) {
        Map<String,Integer> w = new HashMap<>();
        w.put("hind",1);

        System.out.println(w.get("hind"));
    }

    /*
    *     private void createComponent2s() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMap(g);
            }
        };
        mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));


        // this is a mouseListener responsible for changing source / destination vertex
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickHandler(e,mapPanel);
            }
        });


        // choosing destination / start
        vertexChoiceMenu.addActionListener(e -> changeSource= (vertexes) vertexChoiceMenu.getSelectedItem());


        // shortest path button
        JButton findPathButton = new JButton("Find Shortest Path");
        findPathButton.addActionListener(e -> {
            findShortestPath(selectedStartVertex, selectedDestinationVertex);
            mapPanel.repaint();
        });

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));
        comboBoxPanel.add(vertexChoiceMenu);
        comboBoxPanel.add((findPathButton));

        mainPanel.add(mapPanel, BorderLayout.CENTER);
        mainPanel.add(comboBoxPanel, BorderLayout.SOUTH);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));


        setLayout(new BorderLayout());
        add(mainPanel);
    }*/
}
