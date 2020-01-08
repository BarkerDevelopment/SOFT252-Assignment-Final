package views.doctor;

import controllers.primary.DoctorController;
import controllers.primary.PatientController;
import controllers.primary.ViewController;
import controllers.repository.AppointmentRepositoryController;
import models.appointments.Appointment;
import models.appointments.I_AppointmentParticipant;
import models.drugs.I_Prescription;
import models.drugs.Prescription;
import models.users.Patient;
import views.I_Form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewPatientHistory implements I_Form {
    private ViewController _viewController;
    private DoctorController _controller;
    private AppointmentRepositoryController _repositoryController;
    private Patient _patient;

    private JPanel _panelMain;
    private JTable _tableAppointments;
    private JTable _tableNotes;
    private JTable _tablePrescriptions;
    private JButton _buttonReturn;

    public ViewPatientHistory(ViewController viewController, DoctorController controller, AppointmentRepositoryController repositoryController, Patient patient) {
        _viewController = viewController;
        _controller = controller;
        _repositoryController = repositoryController;
        _patient = patient;

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
        _tableAppointments.setModel(getAppointmentModel(_repositoryController.get(_patient)));
        _tablePrescriptions.setModel(getPrescriptionModel(_patient.getPrescriptions()));
        _tableNotes.setModel(getNotesModel(_repositoryController.getPast(_patient)));
    }

    /**
     * Creates the TableAppointmentModel.
     *
     * @param appointments the appointments to be listed in the table.
     * @return the TableModelObject.
     */
    private DefaultTableModel getAppointmentModel(ArrayList< Appointment > appointments){
        String[] _columns = { "Date", "Time", "Doctor ID" };
        DefaultTableModel model = new DefaultTableModel(_columns, 0);

        appointments.forEach(appointment -> {
                    String[] row = new String[ _columns.length];

                    row[0] = appointment.getDateTime().toLocalDate().toString();
                    row[1] = appointment.getDateTime().toLocalTime().toString();
                    row[2] = appointment.getDoctor().getId().toString();

                    model.addRow(row);
                }
        );

        return model;
    }

    /**
     *
     * @param appointments
     * @return
     */
    private TableModel getNotesModel(ArrayList< Appointment > appointments) {
        String[] _columns = { "Date", "Time", "Notes" };
        DefaultTableModel model = new DefaultTableModel(_columns, 0);

        appointments.forEach(appointment -> {
                    String[] row = new String[ _columns.length ];

                    row[ 0 ] = appointment.getDateTime().toLocalDate().toString();
                    row[ 1 ] = appointment.getDateTime().toLocalTime().toString();
                    row[ 2 ] = appointment.getNotes();

                    model.addRow(row);
                }
        );

        return model;
    }

    /**
     *
     * @param prescriptions
     * @return
     */
    private TableModel getPrescriptionModel(ArrayList< I_Prescription > prescriptions) {
        String[] _columns = {"Date prescribed", "Drug Name", "Quantity", "Dosage" };
        DefaultTableModel model = new DefaultTableModel(_columns, 0);

        prescriptions.forEach(prescription -> {
                    String[] row = new String[ _columns.length];

                    row[0] = prescription.getStartDate().toString();
                    row[1] = prescription.getTreatment().getName();
                    row[2] = String.valueOf(prescription.getQty());
                    row[3] = String.valueOf(prescription.getQty() / prescription.getCourse());

                    model.addRow(row);
                }
        );

        return model;
    }
}
