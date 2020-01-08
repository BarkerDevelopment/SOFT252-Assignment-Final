package views.patient;

import controllers.primary.PatientController;
import controllers.primary.ViewController;
import controllers.repository.RequestRepositoryController;
import models.messaging.I_Message;
import models.messaging.Message;
import models.requests.AccountTerminationRequest;
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
    private RequestRepositoryController _repositoryController;

    private JPanel _panelMain;
    private JLabel _labelUserId;
    private JTable _tableMessages;
    private JButton _buttonRemoveMessages;
    private JButton _buttonLogout;
    private JButton _buttonViewAppointments;
    private JButton _buttonViewPrescriptions;
    private JButton _buttonTerminate;
    private JPanel Messages;


    public PatientIndex(ViewController viewController, PatientController controller, RequestRepositoryController repositoryController) {
        super(viewController, controller, controller.getUser());
        _repositoryController = repositoryController;

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

        _buttonTerminate.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _repositoryController.add(new AccountTerminationRequest(_controller.getUser()));
                _viewController.createPopUp("Account termination request submitted.");

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
