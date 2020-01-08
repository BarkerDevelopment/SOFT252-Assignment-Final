package views.patient;

import controllers.primary.PatientController;
import controllers.primary.ViewController;
import models.messaging.I_Message;
import models.messaging.Message;
import models.users.User;
import views.I_Form;
import views.Index;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PatientIndex extends Index {
    private JPanel _panelMain;
    private JTable _tableMessages;
    private JButton _buttonRemoveMessages;
    private JButton _buttonLogout;
    private JButton _buttonViewAppointments;
    private JButton _buttonViewPrescriptions;


    public PatientIndex(ViewController viewController, PatientController controller, User user) {
        super(viewController, controller, user);

        _tableMessages.setModel(getTableMessageModel(_user.getMessages()));
        _tableMessages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        _buttonViewAppointments.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ( (PatientController) _controller ).viewAppointments();
            }
        });

        _buttonViewPrescriptions.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ( (PatientController) _controller ).viewPrescriptions();
            }
        });

        _buttonRemoveMessages.addActionListener(new ActionListener() {
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
        return _panelMain;
    }
}
