package views.patient;

import controllers.primary.PatientController;
import models.drugs.I_Prescription;
import models.drugs.Prescription;
import models.users.Doctor;
import models.users.Patient;
import views.I_Form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Bound class to ViewPrescription form.
 */
public class ViewPrescriptions
        implements I_Form {
    private PatientController _controller;
    private String[] _columns = {"Start Date", "Drug", "Quantity Given", "Drugs per day"};

    private JPanel _panelMain;
    private JTable _tablePrescriptions;
    private JButton _returnButton;

    /**
     * Default constructor.
     *
     * @param controller the view's controller.
     */
    public ViewPrescriptions(PatientController controller) {
        _controller = controller;

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
        ArrayList< I_Prescription > prescriptions = ( (Patient) _controller.getUser() ).getPrescriptions();
        _tablePrescriptions.setModel(getTablePrescriptionModel(prescriptions));
    }

    /**
     * Creates the ListPrescriptionTableModel.
     *
     * @param prescriptions the list of prescriptions.
     * @return the ListViewModel object.
     */
    private DefaultTableModel getTablePrescriptionModel(ArrayList< I_Prescription > prescriptions){
        DefaultTableModel model = new DefaultTableModel(_columns, 0);

        for (I_Prescription prescription : prescriptions){
            String[] row = new String[_columns.length];
            row[0] = prescription.getStartDate().toString();
            row[1] = prescription.getTreatment().getName();
            row[2] = String.valueOf(prescription.getQty());
            row[3] = String.valueOf(prescription.getQty() / prescription.getCourse());

            model.addRow(row);
        }

        return model;
    }
}
