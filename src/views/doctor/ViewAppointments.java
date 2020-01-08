package views.doctor;

import controllers.primary.DoctorController;
import controllers.primary.ViewController;
import controllers.repository.AppointmentRepositoryController;
import models.appointments.Appointment;
import models.appointments.I_AppointmentParticipant;
import views.I_Form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewAppointments implements I_Form {
    private ViewController _viewController;
    private DoctorController _controller;
    private AppointmentRepositoryController _repositoryController;
    private ArrayList< Appointment > _appointments;

    private JPanel _panelMain;
    private JTable _tableAppointments;
    private JButton _buttonReturn;
    private JButton _buttonStart;

    public ViewAppointments(ViewController viewController, DoctorController controller, AppointmentRepositoryController repositoryController) {
        _viewController = viewController;
        _controller = controller;
        _repositoryController = repositoryController;

        _buttonStart.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = _tableAppointments.getSelectedRow();
                if(index > -1){
                    _controller.viewAppointment(_appointments.get(index));

                }else{
                    _viewController.createPopUp("Please select an appointment.");
                }
            }
        });

        _buttonReturn.addActionListener(new ActionListener() {
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
        _appointments = _repositoryController.getFuture((I_AppointmentParticipant) _controller.getUser());

        _tableAppointments.setModel(getAppointmentModel(_appointments));
    }

    /**
     * Creates the TableAppointmentModel.
     *
     * @param appointments the appointments to be listed in the table.
     * @return the TableModelObject.
     */
    private DefaultTableModel getAppointmentModel(ArrayList< Appointment > appointments){
        String[] columns = { "Date", "Time", "Doctor", };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        _appointments.forEach(appointment -> {
                    String[] row = new String[ columns.length];

                    row[0] = appointment.getDateTime().toLocalDate().toString();
                    row[1] = appointment.getDateTime().toLocalTime().toString();
                    row[2] = appointment.getPatient().toString();

                    model.addRow(row);
                }
        );

        return model;
    }
}
