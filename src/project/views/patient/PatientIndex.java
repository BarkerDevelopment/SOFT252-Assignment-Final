package project.views.patient;

import project.controllers.primary.PatientController;
import project.controllers.primary.ViewController;
import project.controllers.repository.RequestRepositoryController;
import project.models.requests.AccountTerminationRequest;
import project.views.Index;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientIndex extends Index {
    private RequestRepositoryController _repositoryController;

    private JPanel _panelMain;
    private JPanel Messages;
    private JLabel _labelUserId;
    private JTable _tableMessages;
    private JButton _buttonRemoveMessages;
    private JButton _buttonLogout;
    private JButton _buttonViewAppointments;
    private JButton _buttonViewPrescriptions;
    private JButton _buttonTerminate;

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
