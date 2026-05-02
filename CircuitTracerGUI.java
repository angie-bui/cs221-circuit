import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Circuit Tracer GUI
 * 
 * Displays the solved Circuit Board and allows
 * interaction with the best solution paths
 * 
 * @author angiebui
 */
public class CircuitTracerGUI extends JFrame {

    private JLabel[][] gridLabels;
    private final CircuitBoard circuitBoard;

    /**
     * Initializes the GUI with the solved circuit board and the list of best paths.
     *
     * @param circuitBoard The solved circuit board to display
     * @param bestPaths    List of TraceState objects containing the best paths
     */
    public CircuitTracerGUI(CircuitBoard circuitBoard, List<TraceState> bestPaths) {
        super("Circuit Tracer GUI");
        this.gridLabels = null;
        this.circuitBoard = circuitBoard;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // set up the menu bar
        setUpMenuBar();

        // set up the board panel
        JPanel boardPanel = createBoardPanel();

        // set up the path list panel
        JScrollPane pathListPanel = createPathListPanel(bestPaths);

        // addd components to the frame
        add(boardPanel, BorderLayout.CENTER);
        add(pathListPanel, BorderLayout.EAST);

        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(600, 400));
        pack();
        setVisible(true);
    }

    /**
     * menu with File and Help bar
     */
    private void setUpMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem quitMenuItem = createMenuItem("Quit", e -> exitApplication());
        fileMenu.add(quitMenuItem);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = createMenuItem("About", e -> showAboutDialog());
        helpMenu.add(aboutMenuItem);

        // add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Creates a menu item with a label and an action listener
     *
     * @param label          text of the menu item
     * @param actionListener action to be executed when the item is selected
     * @return created menu item
     */
    private JMenuItem createMenuItem(String label, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    /**
     * Creates the board panel with the circuit board grid
     *
     * @return JPanel containing the grid of labels
     */
    private JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(circuitBoard.numRows(), circuitBoard.numCols()));
        gridLabels = new JLabel[circuitBoard.numRows()][circuitBoard.numCols()];

        for (int row = 0; row < circuitBoard.numRows(); row++) {
            for (int col = 0; col < circuitBoard.numCols(); col++) {
                char cellChar = circuitBoard.charAt(row, col);
                JLabel label = createLabel(cellChar, row, col);
                gridLabels[row][col] = label;
                boardPanel.add(label);
            }
        }
        return boardPanel;
    }

    /**
     * Creates a label for a specific cell in the grid
     *
     * @param cellChar character to display in the label
     * @param row      row position of the label
     * @param col      col position of the label
     * @return created JLabel
     */
    private JLabel createLabel(char cellChar, int row, int col) {
        JLabel label = new JLabel(String.valueOf(cellChar), SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        return label;
    }

    /**
     * Creates the path list panel containing the list of solution paths
     *
     * @param bestPaths list of TraceState objs representing the best paths
     * @return JScrollPane with JList of paths
     */
    private JScrollPane createPathListPanel(List<TraceState> bestPaths) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < bestPaths.size(); i++) {
            TraceState pathState = bestPaths.get(i);
            listModel.addElement("Path #" + (i + 1) + ", Length: " + pathState.pathLength());
        }

        JList<String> pathList = new JList<>(listModel);
        pathList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pathList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = pathList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    TraceState selectedPath = bestPaths.get(selectedIndex);
                    displayPath(selectedPath);
                }
            }
        });

        return new JScrollPane(pathList);
    }

    /**
     * Displays a selected path by highlighting it in the grid
     *
     * @param traceState TraceState object containing the selected path
     */
    private void displayPath(TraceState traceState) {
        // reset the board to its default state
        resetBoard();

        // highlights the starting and ending points
        for (int row = 0; row < circuitBoard.numRows(); row++) {
            for (int col = 0; col < circuitBoard.numCols(); col++) {
                if (gridLabels[row][col].getText().equals("1") ||
                        gridLabels[row][col].getText().equals("2")) {
                    gridLabels[row][col].setBackground(Color.GRAY);
                }
            }
        }

        // highlights the tracer path cells
        for (Point point : traceState.getPath()) {
            gridLabels[point.x][point.y].setText("T");
            gridLabels[point.x][point.y].setBackground(Color.BLUE);
        }
    }

    /**
     * Resets the grid to its original state
     */
    private void resetBoard() {
        for (int row = 0; row < circuitBoard.numRows(); row++) {
            for (int col = 0; col < circuitBoard.numCols(); col++) {
                char originalChar = circuitBoard.charAt(row, col);
                gridLabels[row][col].setText(String.valueOf(originalChar));
                gridLabels[row][col].setBackground(Color.WHITE);
            }
        }
    }

    /**
     * Displays the About menu
     */
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(
                this,
                "Circuit Tracer\n\nAngie Bui angiebui@u.boisestate.edu",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exits GUI
     */
    private void exitApplication() {
        dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        // Assume CircuitBoard and TraceState are defined elsewhere.
    }
}