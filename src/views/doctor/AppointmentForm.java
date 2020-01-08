package views.doctor;

import controllers.primary.DoctorController;
import controllers.primary.ViewController;
import controllers.repository.AppointmentRepositoryController;
import controllers.repository.DrugRepositoryController;
import exceptions.ObjectNotFoundException;
import models.appointments.Appointment;
import models.drugs.DrugStock;
import models.drugs.I_Prescription;
import models.drugs.Prescription;
import views.I_Form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AppointmentForm implements I_Form {
    private ViewController _viewController;
    private DoctorController _controller;
    private DrugRepositoryController _drugRepositoryController;
    private AppointmentRepositoryController _appointmentRepositoryController;
    private Appointment _appointment;
    private ArrayList< DrugStock > _drugs;

    private JPanel _panelMain;
    private JTable _tableDrugs;
    private JTable _tablePrescribed;
    private JTextArea _fieldNotes;
    private JSpinner _spinnerDays;
    private JSpinner _spinnerQuantity;
    private JButton _buttonPrescribe;
    private JButton _buttonRemove;
    private JButton _buttonComplete;
    private JButton _buttonPatientHistory;

    public AppointmentForm(ViewController viewController, DoctorController controller, DrugRepositoryController drugRepositoryController, AppointmentRepositoryController appointmentRepositoryController, Appointment appointment) {
        _viewController = viewController;
        _controller = controller;
        _drugRepositoryController = drugRepositoryController;
        _appointmentRepositoryController = appointmentRepositoryController;
        _appointment = appointment;

        _spinnerQuantity.setModel(new SpinnerNumberModel(1, 1, 150, 1));
        _spinnerDays.setModel(new SpinnerNumberModel(1, 1, 30, 1));

        _buttonPrescribe.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = _tableDrugs.getSelectedRow();

                if(index > -1){
                    DrugStock drugStock = _drugs.get(index);

                    _appointment.getPrescriptions().add(
                            new Prescription(drugStock.getDrug(), ( (Integer) _spinnerQuantity.getValue() ), ( (Integer) _spinnerDays.getValue() ))
                    );
                    update();

                }else{
                    viewController.createPopUp("Please select a drug to prescribe.");
                }
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
                int index = _tablePrescribed.getSelectedRow();

                if(index > -1){
                    _appointment.getPrescriptions().remove(index);
                    update();

                }else{
                    viewController.createPopUp("Please select a drug to remove.");
                }
            }
        });

        _buttonPatientHistory.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _controller.viewPatientHistory(_appointment.getPatient());
            }
        });

        _buttonComplete.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    _appointment.setNotes(_fieldNotes.getText());
                    _appointmentRepositoryController.complete(_appointment);

                    viewController.createPopUp("Appointment complete.");

                    _controller.back();

                } catch (ObjectNotFoundException ex) {
                    ex.printStackTrace();
                }
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
        _drugs = _drugRepositoryController.get();
        _tableDrugs.setModel(getTableDrugModel(_drugs));
        _tablePrescribed.setModel(getTablePrescriptionModel(_appointment.getPrescriptions()));
    }

    /**
     * @param drugStocks the drugs to display.
     * @return the TableDrugModel object.
     */
    private DefaultTableModel getTableDrugModel(ArrayList< DrugStock > drugStocks) {
        String[] columns = {"Drug Name", "Description", "Side Effects", "Stock"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (DrugStock drugStock : drugStocks){
            String[] row = new String[columns.length];

            row[0] = drugStock.getDrug().getName();
            row[1] = drugStock.getDrug().getDescription();
            row[2] = drugStock.getDrug().getSideEffects().toString();
            row[3] = String.valueOf(drugStock.getStock());

            model.addRow(row);
        }

        return model;
    }

    /**
     * @param prescriptions the prescriptions to display.
     * @return the TableDrugModel object.
     */
    private DefaultTableModel getTablePrescriptionModel(ArrayList< I_Prescription > prescriptions) {
        String[] columns = {"Drug", "Quantity", "Days", "Dosage"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (I_Prescription prescription : prescriptions){
            String[] row = new String[columns.length];

            row[0] = prescription.getTreatment().getName();
            row[1] = String.valueOf(prescription.getQty());
            row[2] = String.valueOf(prescription.getCourse());
            row[3] = String.valueOf((prescription.getQty() / prescription.getCourse()));

            model.addRow(row);
        }

        return model;
    }
}
