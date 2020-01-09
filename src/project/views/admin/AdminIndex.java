package project.views.admin;

import project.controllers.primary.AdminController;
import project.controllers.primary.ViewController;
import project.views.Index;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminIndex extends Index {
    private JPanel _panelMain;
    private JLabel _labelUserId;
    private JButton _buttonViewUsers;
    private JButton _buttonLogout;
    private JButton _removeButton;
    private JTable _tableMessages;


    public AdminIndex(ViewController viewController, AdminController controller) {
        super(viewController, controller, controller.getUser());

        _tableMessages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        _buttonViewUsers.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ( (AdminController) _controller ).viewUsers();
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
                removeMessage(_tableMessages);
            }
        });

        _buttonLogout.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _controller.logout();
            }
        });
    }

    /**
     * @return the main panel of the form.
     */
    @Override
    public JPanel getMainPanel() {

        this.update();
        return _panelMain;
    }

    /**
     * Update the contents of the form.
     */
    @Override
    public void update() {
        _labelUserId.setText(_user.getId().toString());
        _tableMessages.setModel(getTableMessageModel(_user.getMessages()));
    }
}
