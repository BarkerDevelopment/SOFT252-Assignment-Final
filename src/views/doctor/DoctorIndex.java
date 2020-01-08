package views.doctor;

import controllers.primary.AdminController;
import controllers.primary.DoctorController;
import controllers.primary.PatientController;
import controllers.primary.ViewController;
import views.Index;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorIndex extends Index {
    private JPanel _panelMain;
    private JTable _tableMessages;
    private JLabel _labelUserId;
    private JButton _viewAppointmentsButton;
    private JButton _viewDrugsButton;
    private JButton _proposeNewAppointmentButton;
    private JButton _buttonRemove;
    private JButton _buttonLogout;

    public DoctorIndex(ViewController viewController, DoctorController controller) {
        super(viewController, controller, controller.getUser());

        _viewAppointmentsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ( (DoctorController) _controller ).viewAppointments();
            }
        });

        _viewDrugsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ( (DoctorController) _controller ).requestDrugs();
            }
        });

        _proposeNewAppointmentButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ( (DoctorController) _controller ).proposeFutureAppointments();
            }
        });

        _buttonRemove.addActionListener(new ActionListener() {
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
        _labelUserId.setText(_user.getId().toString());
        _tableMessages.setModel(getTableMessageModel(_user.getMessages()));
    }
}
