package views.admin;

import controllers.primary.AdminController;
import controllers.primary.ViewController;
import views.Index;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminIndex extends Index {
    private JPanel _panelMain;
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
                int index = _tableMessages.getSelectedRow();

                if(index > -1){
                    _user.getMessages().remove(index);
                    _tableMessages.remove(index);

                }else{
                    _viewController.createPopUp("Please select a message to delete.");
                }
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
        _tableMessages.setModel(getTableMessageModel(_user.getMessages()));
    }
}
