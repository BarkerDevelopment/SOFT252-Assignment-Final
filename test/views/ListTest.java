package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListTest implements I_Form{
    private JFrame _frame;
    private JList _list;
    private JPanel _panel1;
    private JButton _addButton;
    private JButton _removeButton;

    public ListTest() {
        _addButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        _removeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {
        DefaultListModel model = new DefaultListModel();

        for (int i = 0; i < 10; i++) model.addElement("Element" + i);

        _list = new JList(model);
    }

    @Override
    public JPanel getMainPanel() {
        return _panel1;
    }
}
