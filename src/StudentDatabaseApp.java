import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;
import java.util.regex.Pattern;
public class StudentDatabaseApp extends JFrame {
    // Local MySQL database settings
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";
    private JTextArea queryArea;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JTextField filterField;
    private JButton executeButton;
    private JButton clearButton;
    private JButton filterButton;
    public StudentDatabaseApp() {
        setTitle("Student Database Query System");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createGUI();
    }
    // Builds the GUI layout and button actions
    private void createGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel titleLabel = new JLabel(
                "STUDENT DATABASE QUERY SYSTEM",
                SwingConstants.CENTER
        );
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        JPanel queryPanel = new JPanel(new BorderLayout(10, 10));
        // Query text area at the top
        JLabel queryLabel = new JLabel("SQL Query:");
        queryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        queryArea = new JTextArea(4, 50);
        queryArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        queryArea.setLineWrap(true);
        queryArea.setWrapStyleWord(true);
        queryArea.setText("SELECT * FROM student;");
        JScrollPane queryScrollPane = new JScrollPane(queryArea);
        queryPanel.add(queryLabel, BorderLayout.NORTH);
        queryPanel.add(queryScrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        executeButton = new JButton("Execute Query");
        clearButton = new JButton("Clear");
        buttonPanel.add(executeButton);
        buttonPanel.add(clearButton);
        queryPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );
        // Result table where query output is shown
        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        resultTable.setRowHeight(25);
        resultTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        JScrollPane tableScrollPane = new JScrollPane(resultTable);
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filterLabel = new JLabel("Filter Results:");
        filterField = new JTextField(30);          
        filterButton = new JButton("Apply Filter");
        JButton showAllButton = new JButton("Show All");
        filterPanel.add(filterLabel);
        filterPanel.add(filterField);
        filterPanel.add(filterButton);
        filterPanel.add(showAllButton);
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(queryPanel, BorderLayout.NORTH);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        centerPanel.add(filterPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        executeButton.addActionListener(e -> executeQuery());
        clearButton.addActionListener(e -> clearResults());
        filterButton.addActionListener(e -> applyFilter());
        showAllButton.addActionListener(e -> showAllRows());
        filterField.addActionListener(e -> applyFilter());
        add(mainPanel);
        setVisible(true);
    }
    // Runs the SQL query entered by the user
    private void executeQuery() {
        String query = queryArea.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter an SQL query.",
                    "Query Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        // Only SELECT queries are allowed
        // because this application is designed
        // to display database results.
        if (!query.toLowerCase().startsWith("select")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Only SELECT queries are allowed.",
                    "Invalid Query",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        try {
            // 1. Load JDBC Driver
            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );
            // 2. Create Connection, Statement, and ResultSet
            //    and close them automatically when the block exits.
            try (Connection con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                // 3. Display ResultSet
                displayResults(rs);
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "MySQL JDBC Driver not found.\n"
                            + "Check the Connector/J JAR.",
                    "Driver Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n"
                            + e.getMessage(),
                    "SQL Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    // Displays the result set in the JTable
    private void displayResults(ResultSet rs)
            throws SQLException {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(metaData.getColumnName(i));
        }
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }
            tableModel.addRow(row);
        }
        for (int column = 0; column < resultTable.getColumnCount(); column++) {
            resultTable.getColumnModel().getColumn(column).setPreferredWidth(120);
        }
    }
    // Filters the currently displayed table rows
    private void applyFilter() {
        String filterText = filterField.getText().trim();
        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(tableModel);
        resultTable.setRowSorter(sorter);
        if (filterText.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            "(?i)" + Pattern.quote(filterText)
                    )
            );
        }
    }
    private void showAllRows() {
        filterField.setText("");
        TableRowSorter<?> sorter =
                (TableRowSorter<?>) resultTable.getRowSorter();
        if (sorter != null) {
            sorter.setRowFilter(null);
        }
    }
    private void clearResults() {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        filterField.setText("");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                StudentDatabaseApp::new
        );
    }
}