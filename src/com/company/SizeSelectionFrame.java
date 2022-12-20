package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SizeSelectionFrame extends JFrame implements ActionListener {

    private final int frameWidth = 350;
    private final int frameHeight = 300;

    private JPanel panel;
    private JSlider slider;

    private JButton enterButton;

    private SolverFrame currentSolverFrame = null;

    public SizeSelectionFrame() {

        this.setTitle("Select Maze Size:");

        panel = new JPanel();
        panel.setLayout(null);


        //get centre screen position:
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = 100;
        int y = (int) ((dimension.getHeight() - frameHeight) / 2);
        this.setLocation(x, y);

        //format rest of frame
        this.setSize(frameWidth, frameHeight);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //format main JPanel
        panel.add(new JLabel() {{

            this.setText("Select Maze Size:");
            this.setFont(new Font("Sans Serif", Font.BOLD, 13));
            this.setBounds(110, 10, 200, 100);

        }});

        //format slider
        slider = new JSlider(10, 100, 55) {{
            this.setBounds(20, 90, 300, 50);
            this.setPaintTicks(true);
            this.setMajorTickSpacing(10);
            this.setMinorTickSpacing(5);
            this.setPaintLabels(true);
            this.setFocusable(false);
        }};

        //format enter button
        enterButton = new JButton() {{
            this.setText("Enter");
            this.setFont(new Font("Sarif Sans", Font.BOLD, 12));
            this.setBounds(220,200,75,30);
            this.setFocusable(false);
            this.addActionListener(SizeSelectionFrame.this::actionPerformed);
        }};

        //add swing components to JPanel
        panel.add(slider);
        panel.add(enterButton);

        this.add(panel);
        this.setVisible(true);

    }

    //when enter button is pressed, calls solverFrame with correct maze size.
    @Override
    public void actionPerformed(ActionEvent e) {

        int mazeSize = slider.getValue();

        if (!(currentSolverFrame == null)){
            currentSolverFrame.dispose();
        }

        //if maze size is even, converts to odd so works with recursive maze.
        if (mazeSize %2 == 0){
            currentSolverFrame = new  SolverFrame(mazeSize + 1);
        }else{
            currentSolverFrame = new SolverFrame(mazeSize);
        }

    }
}



