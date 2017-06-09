package Client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import packages.ClientPackage;
import packages.GameBoard;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


import static com.esotericsoftware.jsonbeans.JsonValue.ValueType.object;

public class MainFrame implements ActionListener {

    private JButton[][] buttons = new JButton[7][6];

    public MainFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponents(frame.getContentPane());

        frame.setSize(new Dimension(640, 480));
        frame.setVisible(true);
    }

    private JButton getNewButton() {
        JButton button = new JButton();
        button.setBorder(new LineBorder(Color.GRAY));
        button.setBackground(Color.DARK_GRAY);
        button.setOpaque(true);
        button.setForeground(Color.GRAY);
        button.setFont(new Font("Impact", Font.PLAIN, 160));
        button.setText("\u2022");
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.addActionListener(this);
        return button;
    }

    private void addComponents(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Make the buttons scale up when the window is resized.
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0 / 7.0;
        c.weighty = 1.0 / 6.0;

        // Create a button in each row in each column.
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                JButton button = getNewButton();
                button.setActionCommand("" + x);

                c.gridx = x;
                c.gridy = 5 - y;
                buttons[x][y] = button;
                pane.add(button, c);
            }
        }
    }

    public JButton getButton(int x, int y) {
        return buttons[x][y];
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("0")) {

        } else if (ae.getActionCommand().equals("1")) {


        } else if (ae.getActionCommand().equals("2")) {


        } else if (ae.getActionCommand().equals("3")) {


        } else if (ae.getActionCommand().equals("4")) {


        } else if (ae.getActionCommand().equals("5")) {


        } else if (ae.getActionCommand().equals("6")) {


        }
    }
}
