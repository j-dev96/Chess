package gui;

import javax.swing.*;

public class PlayerSelected extends JPanel {

    private static final String[] Labels = {"Easy", "Medium", "Hard", "Hardest", "Good Luck",};

    private final JRadioButton human = new JRadioButton("Human");

    private final JRadioButton ai = new JRadioButton("Computer");

    private final JComboBox<String> level = new JComboBox<>(Labels);

    public static int level_ai;


    public PlayerSelected(final String title, final boolean humanSet) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(title);
        add(label);

        ButtonGroup group = new ButtonGroup();
        group.add(human);
        group.add(ai);
        human.setSelected(humanSet);
        ai.setSelected(!humanSet);
        level.setSelectedIndex(3);
        level.setEnabled(!humanSet);
        human.setAlignmentX(LEFT_ALIGNMENT);
        ai.setAlignmentX(LEFT_ALIGNMENT);
        level.setAlignmentX(LEFT_ALIGNMENT);

        human.addActionListener(e -> level.setEnabled(!human.isSelected()));
        ai.addActionListener(e -> level.setEnabled(ai.isSelected()));
        level.addActionListener(e -> level.getSelectedItem());

        add(human);
        add(ai);
        add(level);

    }
    public final String getPlayer() {
        if (human.isSelected()) {
            return "human";
        } else {
            if (level.getSelectedItem() == "Easy") level_ai = 1;
            if (level.getSelectedItem() == "Medium") level_ai = 2;
            if (level.getSelectedItem() == "Hard") level_ai = 3;
            if (level.getSelectedItem() == "Hardest") level_ai = 4;
            if (level.getSelectedItem() == "Good Luck") level_ai = 5;
            return null;
        }
    }
}




