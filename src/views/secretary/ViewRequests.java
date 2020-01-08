package views.secretary;

import controllers.primary.SecretaryController;
import controllers.primary.ViewController;
import controllers.repository.RequestRepositoryController;
import models.requests.*;
import views.I_Form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.stream.Collectors;

/**
 * Bound class to ViewRequests view.
 */
public class ViewRequests implements I_Form {
    private ViewController _viewController;
    private SecretaryController _controller;
    private RequestRepositoryController _repositoryController;
    private EnumMap< RequestType, ArrayList< Request > > _requests;


    private JPanel _panelMain;
    private JTabbedPane _tabbedPanel;
    private JTable _tableCreation;
    private JTable _tableTermination;
    private JTable _tableAppointment;
    private JTable _tableDrugs;
    private JTable _tablePrescription;
    private JButton _buttonApprove;
    private JButton _buttonDeny;
    private JButton _buttonReturn;

    public ViewRequests(ViewController viewController, SecretaryController controller, RequestRepositoryController repositoryController) {
        _viewController = viewController;
        _controller = controller;
        _repositoryController = repositoryController;
        _requests = new EnumMap< >(RequestType.class);

        this.update();

        _buttonApprove.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Request selectedRequest = determineSelectedRequest();

                if(selectedRequest != null){
                    try {
                        _repositoryController.approve(selectedRequest);
                        _viewController.createPopUp("Request approved.");

                    } catch (Exception ex) {
                        _viewController.createPopUp(ex.getMessage());
                    }

                }else{
                    _viewController.createPopUp("Please select a request to approve.");
                }
            }
        });

        _buttonDeny.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Request selectedRequest = determineSelectedRequest();

                if(selectedRequest != null){
                    try {
                        _repositoryController.deny(selectedRequest);
                        _viewController.createPopUp("Request denied.");

                    } catch (Exception ex) {
                        _viewController.createPopUp(ex.getMessage());
                    }

                }else{
                    _viewController.createPopUp("Please select a request to deny.");
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
        update();
        return _panelMain;
    }

    /**
     * Update the contents of the form.
     */
    @Override
    public void update(){
        _requests.put(RequestType.ACCOUNT_CREATION, new ArrayList<>(_repositoryController.getRepository(RequestType.ACCOUNT_CREATION).get().stream().map(user -> ( (AccountCreationRequest) user )).collect(Collectors.toList())));
        _requests.put(RequestType.ACCOUNT_TERMINATION, new ArrayList<>(_repositoryController.getRepository(RequestType.ACCOUNT_TERMINATION).get().stream().map(user -> ( (AccountTerminationRequest) user )).collect(Collectors.toList())));
        _requests.put(RequestType.APPOINTMENT, new ArrayList<>(_repositoryController.getRepository(RequestType.APPOINTMENT).get().stream().map(user -> ( (AppointmentRequest) user )).collect(Collectors.toList())));
        _requests.put(RequestType.NEW_DRUG, new ArrayList<>(_repositoryController.getRepository(RequestType.NEW_DRUG).get().stream().map(user -> ( (DrugRequest) user )).collect(Collectors.toList())));
        _requests.put(RequestType.PRESCRIPTION, new ArrayList<>(_repositoryController.getRepository(RequestType.PRESCRIPTION).get().stream().map(user -> ( (PrescriptionRequest) user )).collect(Collectors.toList())));


        _tableCreation.setModel(getTableCreationModel(_requests.get(RequestType.ACCOUNT_CREATION)));
        _tableTermination.setModel(getTableTerminationModel(_requests.get(RequestType.ACCOUNT_TERMINATION)));
        _tableAppointment.setModel(getTableAppointmentModel(_requests.get(RequestType.APPOINTMENT)));
        _tableDrugs.setModel(getTableDrugModel(_requests.get(RequestType.NEW_DRUG)));
        _tablePrescription.setModel(getTablePrescriptionModel(_requests.get(RequestType.PRESCRIPTION)));
    }

    /**
     * @param requests the AccountCreation requests to display.
     * @return the TableCreationModel object.
     */
    private DefaultTableModel getTableCreationModel(ArrayList< Request > requests){
        String[] columns = {"Full name", "Address", "DOB", "Gender"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Request request : requests ){
            AccountCreationRequest accountCreationRequest = ( (AccountCreationRequest) request );
            String[] row = new String[columns.length];

            row[0] = String.format("%s, %s", accountCreationRequest.getSurname(), accountCreationRequest.getName());
            row[1] = accountCreationRequest.getAddress().toString();
            row[2] = accountCreationRequest.getDob().toString();
            row[3] = accountCreationRequest.getGender().toString();

            model.addRow(row);
        }

        return model;
    }

    /**
     * @param requests the AccountTermination requests to display.
     * @return the TableCreationModel object.
     */
    private DefaultTableModel getTableTerminationModel(ArrayList< Request > requests){
        String[] columns = {"User ID"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Request request : requests ){
            AccountTerminationRequest accountTerminationRequest = ( (AccountTerminationRequest) request );
            String[] row = new String[columns.length];

            row[0] = accountTerminationRequest.getRequester().getId().toString();

            model.addRow(row);
        }

        return model;
    }

    /**
     * @param requests the Appointment requests to display.
     * @return the TableCreationModel object.
     */
    private DefaultTableModel getTableAppointmentModel(ArrayList< Request > requests){
        String[] columns = {"Patient ID", "Doctor ID", "Date", "Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);


        for (Request request : requests ){
            AppointmentRequest appointmentRequest = ( (AppointmentRequest) request );
            String[] row = new String[columns.length];

            row[0] = appointmentRequest.getPatient().getId().toString();
            row[1] = appointmentRequest.getDoctor().getId().toString();
            row[2] = appointmentRequest.getDateTime().toLocalDate().toString();
            row[3] = appointmentRequest.getDateTime().toLocalTime().toString();

            model.addRow(row);
        }

        return model;
    }

    /**
     * @param requests the New Drug requests to display.
     * @return the TableCreationModel object.
     */
    private DefaultTableModel getTableDrugModel(ArrayList< Request > requests){
        String[] columns = {"Doctor ID", "Drug Name", "Description", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Request request : requests ){
            DrugRequest drugRequest = ( (DrugRequest) request );
            String[] row = new String[columns.length];

            row[0] = drugRequest.getDoctor().getId().toString();
            row[1] = drugRequest.getName();
            row[2] = drugRequest.getDescription();
            row[3] = String.valueOf(drugRequest.getStartingQty());

            model.addRow(row);
        }

        return model;
    }

    /**
     * @param requests the Prescription requests to display.
     * @return the TableCreationModel object.
     */
    private DefaultTableModel getTablePrescriptionModel(ArrayList< Request > requests){
        String[] columns = {"Patient ID", "Drug Name", "Quantity Prescribed", "Course"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Request request : requests ){
            PrescriptionRequest prescriptionRequest = ( (PrescriptionRequest) request );
            String[] row = new String[columns.length];

            row[0] = prescriptionRequest.getPatient().getId().toString();
            row[1] = prescriptionRequest.getPrescription().getTreatment().getName();
            row[2] = String.valueOf(prescriptionRequest.getPrescription().getQty());
            row[3] = String.valueOf(prescriptionRequest.getPrescription().getCourse());

            model.addRow(row);
        }

        return model;
    }

    /**
     * @return the selected request.
     */
    private Request determineSelectedRequest(){
        int tabIndex = _tabbedPanel.getSelectedIndex();

        int index;
        Request selectedRequest;
        switch (tabIndex){
            case 0:
                index = _tableCreation.getSelectedRow();
                selectedRequest = _requests.get(RequestType.ACCOUNT_CREATION).get(index);
                ( (DefaultTableModel) _tableCreation.getModel() ).removeRow(index);
                break;

            case 1:
                index = _tableTermination.getSelectedRow();
                selectedRequest = _requests.get(RequestType.ACCOUNT_TERMINATION).get(index);
                ( (DefaultTableModel) _tableTermination.getModel() ).removeRow(index);
                break;

            case 2:
                index = _tableAppointment.getSelectedRow();
                selectedRequest = _requests.get(RequestType.APPOINTMENT).get(index);
                ( (DefaultTableModel) _tableAppointment.getModel() ).removeRow(index);
                break;

            case 3:
                index = _tableDrugs.getSelectedRow();
                selectedRequest = _requests.get(RequestType.NEW_DRUG).get(index);
                ( (DefaultTableModel) _tableDrugs.getModel() ).removeRow(index);
                break;

            case 4:
                index = _tablePrescription.getSelectedRow();
                selectedRequest = _requests.get(RequestType.PRESCRIPTION).get(index);
                ( (DefaultTableModel) _tablePrescription.getModel() ).removeRow(index);
                break;

            default:
                selectedRequest = null;
                break;
        }

        return selectedRequest;
    }
}
