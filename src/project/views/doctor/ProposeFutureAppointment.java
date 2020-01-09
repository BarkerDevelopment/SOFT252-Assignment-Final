package project.views.doctor;

import project.controllers.primary.DoctorController;
import project.controllers.primary.ViewController;
import project.controllers.repository.RequestRepositoryController;
import project.controllers.repository.UserRepositoryController;
import project.models.requests.AppointmentRequest;
import project.models.requests.Request;
import project.models.users.Doctor;
import project.models.users.Patient;
import project.models.users.info.UserRole;
import project.views.I_Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProposeFutureAppointment implements I_Form {
    private ViewController _viewController;
    private DoctorController _controller;
    private UserRepositoryController _userRepositoryController;
    private RequestRepositoryController _requestRepositoryController;
    private ArrayList< Patient > _patients;

    private JPanel _panelMain;
    private JList< String > _listPatient;
    private JComboBox< LocalDate > _comboDate;
    private JSpinner _spinnerMinute;
    private JSpinner _spinnerHour;
    private JButton _buttonSubmit;
    private JButton _buttonReturn;



    public ProposeFutureAppointment(ViewController viewController, DoctorController controller, UserRepositoryController userRepositoryController, RequestRepositoryController requestRepositoryController) {
        _viewController = viewController;
        _controller = controller;
        _userRepositoryController = userRepositoryController;
        _requestRepositoryController = requestRepositoryController;

        _buttonSubmit.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = _listPatient.getSelectedIndex();

                if( index > -1) {
                    Patient patient = _patients.get(index);
                    submitAppointmentRequest(patient);
                    _viewController.createPopUp(String.format("Appointment request for %s submitted.", patient.getId().toString()));

                }else{
                    submitAppointmentRequest(null);
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
        _patients = new ArrayList<>(_userRepositoryController.getRepository(UserRole.PATIENT).get().stream().map(user -> ( (Patient) user )).collect(Collectors.toList()));
        _listPatient.setModel(getListPatientModel(_patients));

        _comboDate.setModel(getComboDateModel());
        _spinnerHour.setModel(getSpinnerHourModel());
        _spinnerMinute.setModel(getSpinnerMinuteModel());
    }

    /**
     * Creates the ListDoctorsModel.
     *
     * @param patients the array of doctors to be displayed.
     * @return the DefaultListModel object.
     */
    private DefaultListModel< String > getListPatientModel(ArrayList< Patient > patients){
        DefaultListModel< String > model = new DefaultListModel<>();
        for (Patient patient : patients) model.addElement(String.format("%s %s, %s", patient.getId().toString(), patient.getSurname(), patient.getName()));

        return model;
    }

    /**
     * Creates the ComboDateModel.
     *
     * @return the DefaultComboBoxModel object.
     */
    private DefaultComboBoxModel< LocalDate > getComboDateModel(){
        DefaultComboBoxModel< LocalDate > model = new DefaultComboBoxModel<>();

        for (int i = 1; i < 8; i++) {
            model.addElement(LocalDate.now().plusDays(i));
        }

        model.setSelectedItem(LocalDate.now().plusDays(1));

        return model;
    }

    /**
     * Creates the SpinnerHourModel.
     *
     * @return the SpinnerNumberModel object.
     */
    private SpinnerNumberModel getSpinnerHourModel(){
        SpinnerNumberModel model = new SpinnerNumberModel(9, 9, 17, 1);

        return model;
    }

    /**
     * Creates the SpinnerMinuteModel.
     *
     * @return the SpinnerNumberModel object.
     */
    private SpinnerNumberModel getSpinnerMinuteModel(){

        return new SpinnerNumberModel(0, 0, 45, 15);
    }

    /**
     * Submits an appointment request.
     *
     * @param patient the requested patient.
     */
    private void submitAppointmentRequest(Patient patient){
        LocalDateTime dateTime = LocalDateTime.of((LocalDate) _comboDate.getSelectedItem(), LocalTime.of((Integer) _spinnerHour.getValue(), (Integer) _spinnerMinute.getValue()));
        Request request = new AppointmentRequest(patient, ( (Doctor) _controller.getUser() ), dateTime);
        _requestRepositoryController.add(request);

        _controller.back();
    }
}
