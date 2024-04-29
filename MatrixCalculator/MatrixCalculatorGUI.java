import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MatrixCalculatorGUI extends JFrame implements ActionListener {
    private JTextField[][] matrixFields1, matrixFields2, resultFields;
    private JLabel matrixLabel1, matrixLabel2, resultLabel;
    private JButton addButton, subtractButton, multiplyButton;
    private int rows1, columns1, rows2, columns2;

    public MatrixCalculatorGUI(int rows1, int columns1, int rows2, int columns2) {
        this.rows1 = rows1;
        this.columns1 = columns1;
        this.rows2 = rows2;
        this.columns2 = columns2;
        
        setTitle("Matrix Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JPanel matrixPanel1 = new JPanel();
        matrixPanel1.setLayout(new GridLayout(rows1, columns1));
        matrixFields1 = new JTextField[rows1][columns1];
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < columns1; j++) {
                matrixFields1[i][j] = new JTextField(5);
                matrixPanel1.add(matrixFields1[i][j]);
            }
        }
        matrixLabel1 = new JLabel("Matrix 1");

        JPanel matrixPanel2 = new JPanel();
        matrixPanel2.setLayout(new GridLayout(rows2, columns2));
        matrixFields2 = new JTextField[rows2][columns2];
        for (int i = 0; i < rows2; i++) {
            for (int j = 0; j < columns2; j++) {
                matrixFields2[i][j] = new JTextField(5);
                matrixPanel2.add(matrixFields2[i][j]);
            }
        }
        matrixLabel2 = new JLabel("Matrix 2");

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        subtractButton = new JButton("Subtract");
        multiplyButton = new JButton("Multiply");
        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(rows1, columns2));
        resultFields = new JTextField[rows1][columns2];
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < columns2; j++) {
                resultFields[i][j] = new JTextField(5);
                resultFields[i][j].setEditable(false);
                resultPanel.add(resultFields[i][j]);
            }
        }
        resultLabel = new JLabel("Result");

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(matrixLabel1, BorderLayout.NORTH);
        leftPanel.add(matrixPanel1, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(matrixLabel2, BorderLayout.NORTH);
        rightPanel.add(matrixPanel2, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(resultLabel, BorderLayout.NORTH);
        bottomPanel.add(resultPanel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.EAST);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            if (canAddOrSubtract()) {
                calculate('+');
            } else {
                JOptionPane.showMessageDialog(this, "Cannot add matrices with different dimensions!");
            }
        } else if (e.getSource() == subtractButton) {
            if (canAddOrSubtract()) {
                calculate('-');
            } else {
                JOptionPane.showMessageDialog(this, "Cannot subtract matrices with different dimensions!");
            }
        } else if (e.getSource() == multiplyButton) {
            if (canMultiply()) {
                calculate('*');
            } else {
                JOptionPane.showMessageDialog(this, "Cannot multiply matrices with inappropriate dimensions!");
            }
        }
    }

    private boolean canAddOrSubtract() {
        return rows1 == rows2 && columns1 == columns2;
    }

    private boolean canMultiply() {
        return columns1 == rows2;
    }

    private void calculate(char operation) {
        double[][] matrix1 = getMatrixValues(matrixFields1, rows1, columns1);
        double[][] matrix2 = getMatrixValues(matrixFields2, rows2, columns2);
        double[][] result = new double[rows1][columns2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < columns2; j++) {
                switch (operation) {
                    case '+':
                        result[i][j] = matrix1[i][j] + matrix2[i][j];
                        break;
                    case '-':
                        result[i][j] = matrix1[i][j] - matrix2[i][j];
                        break;
                    case '*':
                        result[i][j] = 0;
                        for (int k = 0; k < columns1; k++) {
                            result[i][j] += matrix1[i][k] * matrix2[k][j];
                        }
                        break;
                }
                resultFields[i][j].setText(Double.toString(result[i][j]));
            }
        }
    }

    private double[][] getMatrixValues(JTextField[][] matrixFields, int rows, int columns) {
        double[][] matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = Double.parseDouble(matrixFields[i][j].getText());
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int rows1 = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rows for Matrix 1:"));
            int columns1 = Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns for Matrix 1:"));
            int rows2 = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rows for Matrix 2:"));
            int columns2 = Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns for Matrix 2:"));

            new MatrixCalculatorGUI(rows1, columns1, rows2, columns2);
        });
    }
}
