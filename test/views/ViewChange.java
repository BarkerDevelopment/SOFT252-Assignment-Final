package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewChange implements I_Form {
    private JButton _changeButton;
    private JPanel _panel1;

    public ViewChange(ChangeController controller) {
        _changeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.listTest();
            }
        });
    }

    @Override
    public JPanel getMainPanel() {
        this.update();
        return _panel1;
    }

    /**
     * Update the contents of the form.
     */
    @Override
    public void update() {

    }
}
