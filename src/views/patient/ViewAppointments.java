package views.patient;

import controllers.primary.PatientController;
import controllers.primary.ViewController;
import controllers.repository.AppointmentRepositoryController;
import exceptions.ObjectNotFoundException;
import models.appointments.Appointment;
import models.users.Doctor;
import views.I_Form;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Bound class to the ViewAppointments form.
 */
public class ViewAppointments implements I_Form {
    private ViewController _viewController;
    private PatientController _controller;
    private String[] _columns = { "Date", "Time", "Doctor", };
    private ArrayList< Appointment > _appointments;
    private ArrayList< Appointment > _pastAppointments;


    private JPanel _panelMain;
    private JTabbedPane _tabbedPane;
    private JTable _tableAppointments;
    private JTable _tablePastAppointments;
    private JButton _returnButton;
    private JButton _requestAppointmentButton;
    private JButton _cancelAppointmentButton;
    private JButton _reviewDoctorButton;

    /**
     * Default constructor.
     *
     * @param viewController the view controller.
     * @param controller the view's controller.
     * @param appointments the patient's future appointments.
     * @param pastAppointments the patient's past appointments.
     */
    public ViewAppointments(ViewController viewController, PatientController controller, ArrayList< Appointment > appointments, ArrayList< Appointment > pastAppointments) {
        _viewController = viewController;
        _controller = controller;

        _appointments = appointments;
        _tableAppointments.setModel(getAppointmentModel(appointments));
        _pastAppointments = pastAppointments;
        _tablePastAppointments.setModel(getAppointmentModel(pastAppointments));

        _reviewDoctorButton.setVisible(false);

        _returnButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _controller.back();
            }
        });

        _requestAppointmentButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _controller.requestAppointment();
            }
        });

        _tabbedPane.addChangeListener(new ChangeListener() {

            /**
             * Invoked when a tab is changed.
             *
             * @param e the event to be passed.
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {

                    switch (_tabbedPane.getSelectedIndex()){
                        case 0:
                            _cancelAppointmentButton.setVisible(true);
                            _reviewDoctorButton.setVisible(false);
                            break;

                        case 1:
                            _cancelAppointmentButton.setVisible(false);
                            _reviewDoctorButton.setVisible(true);
                            break;

                        default:
                            break;
                    }
                }
            }
        });

        _cancelAppointmentButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentRepositoryController appointmentRepositoryController = AppointmentRepositoryController.getInstance();
                int index = _tableAppointments.getSelectedRow();

                if(index > -1){
                    try {
                        appointmentRepositoryController.remove(_appointments.get(index));

                    } catch (ObjectNotFoundException ex) {
                        _viewController.createPopUp( ex.getMessage() );
                    }

                }else{
                    _viewController.createPopUp("Please select an appointment to remove.");
                }
            }
        });


        _reviewDoctorButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = _tablePastAppointments.getSelectedRow();

                if(index > -1) {
                    Doctor doctor = _pastAppointments.get(index).getDoctor();

                    _controller.reviewDoctor(doctor);

                }else{
                    _viewController.createPopUp("Please select a past Appointment.");
                }
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

    /**
     * Creates the TableAppointmentModel.
     *
     * @param appointments the appointments to be listed in the table.
     * @return the TableModelObject.
     */
    private DefaultTableModel getAppointmentModel(ArrayList< Appointment > appointments){
        DefaultTableModel model = new DefaultTableModel(_columns, 0);

        _appointments.forEach(appointment -> {
                    String[] row = new String[ _columns.length];

                    row[0] = appointment.getDateTime().toLocalDate().toString();
                    row[1] = appointment.getDateTime().toLocalTime().toString();
                    row[2] = appointment.getDoctor().toString();

                    model.addRow(row);
                }
        );

        return model;
    }
}
