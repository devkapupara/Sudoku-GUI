package Sudoku;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    private JTextArea puzzle;
    private JTextField size;
    private JTextField rows;
    private JTextField columns;
    private JButton solveBtn;
    private int iSize;
    private int iRows;
    private int iColumns;
    private String iPuzzle;
    private Sudoku sud;
    private long runTime;
    private JButton quitBtn;
    private JButton reloadBtn;

    public MainFrame()
    {
        super("Sudoku Solver");

        setLayout(new BorderLayout());
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        size = new JTextField(5);
        rows = new JTextField(5);
        columns = new JTextField(5);

        ToolBar upperToolBar = new ToolBar();
        upperToolBar.add(new JLabel("Size:"));
        upperToolBar.add(size);
        upperToolBar.add(new JLabel("# of Rows of Inner Box:"));
        upperToolBar.add(rows);
        upperToolBar.add(new JLabel("# of Columns of Inner Box:"));
        upperToolBar.add(columns);

        ToolBar bottomToolBar = new ToolBar();
        solveBtn = new JButton("Solve");
        quitBtn = new JButton("Quit");
        reloadBtn = new JButton("Reload");

        solveBtn.addActionListener(e -> {
            loadSudoku();
            solveSudoku();
        });
        quitBtn.addActionListener(e -> {
            System.exit(1);
        });

        reloadBtn.addActionListener(e -> {
            reload();
        });

        bottomToolBar.add(solveBtn);
        bottomToolBar.add(reloadBtn);
        bottomToolBar.add(quitBtn);

        puzzle = new JTextArea();
        puzzle.setText(defaultText());


        add(upperToolBar,BorderLayout.NORTH);
        add(new JScrollPane(puzzle), BorderLayout.CENTER);
        add(bottomToolBar, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void loadSudoku()
    {
        if (puzzle.getText().length() == 0)
        {
            puzzle.setText("Invalid Input. Try Again.");
            puzzle.append(defaultText());
        }

        iPuzzle = puzzle.getText();
        iSize = Integer.parseInt(size.getText());
        iRows = Integer.parseInt(rows.getText());
        iColumns = Integer.parseInt(columns.getText());
        sud = new Sudoku(iSize,iRows,iColumns);
        sud.loadSudoku(iPuzzle);
    }
    private void solveSudoku()
    {
        long sTime = System.currentTimeMillis();
        if (sud.solve(0,0))
        {
            puzzle.setText("Solution Found!\n");
            runTime = System.currentTimeMillis() - sTime;
            puzzle.append(sud.printSudoku());
            puzzle.append("It took me " + runTime + "ms to solve it.");
        }
        else
            puzzle.setText("No Solution Found\n");
    }

    private void reload()
    {
        puzzle.setText(defaultText());
        size.setText(null);
        rows.setText(null);
        columns.setText(null);
    }
    private String defaultText()
    {
        return "#Enter the puzzle here. \n#0 for empty cell and space between each column. \n#New line for a new Row.\n#Might take a few seconds to find a solution.\n";
    }
}
