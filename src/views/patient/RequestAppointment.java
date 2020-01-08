package views.patient;

import controllers.primary.PatientController;
import controllers.primary.ViewController;
import controllers.repository.RequestRepositoryController;
import controllers.repository.UserRepositoryController;
import models.feedback.FeedbackWithRating;
import models.feedback.I_Feedback;
import models.requests.AppointmentRequest;
import models.requests.Request;
import models.users.Doctor;
import models.users.Patient;
import models.users.info.UserRole;
import views.I_Form;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Bound class to RequestAppointment form.
 */
public class RequestAppointment implements I_Form {
    private ViewController _viewController;
    private PatientController _controller;
    private UserRepositoryController _userRepositoryController;
    private RequestRepositoryController _requestRepositoryController;
    private ArrayList< Doctor > _doctors;

    private JPanel _panelMain;
    private JList _listDoctors;
    private JList _listFeedback;
    private JLabel _labelRating;
    private JComboBox _comboDate;
    private JButton _buttonSubmit;
    private JButton _buttonReturn;
    private JSpinner _spinnerMinute;
    private JSpinner _spinnerHour;


    /**
     * Default constructor.
     *
     * @param controller the view's controller.
     * @param userRepositoryController the UserRepositoryController.
     */
    public RequestAppointment(ViewController viewController,  PatientController controller, UserRepositoryController userRepositoryController, RequestRepositoryController requestRepositoryController) {
        _viewController = viewController;
        _controller = controller;
        _userRepositoryController = userRepositoryController;
        _requestRepositoryController = requestRepositoryController;


        _listDoctors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        _listDoctors.addListSelectionListener(new ListSelectionListener() {
            /**
             * Called whenever the value of the selection changes.
             *
             * @param e the event that characterizes the change.
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Doctor doctor = _doctors.get(_listDoctors.getSelectedIndex());
                ArrayList< I_Feedback > feedbacks = doctor.getFeedback();

                ArrayList< I_Feedback > feedbackRatings = new ArrayList<>(
                        feedbacks.stream()
                                .filter(feedback -> feedback instanceof FeedbackWithRating)
                                .collect(Collectors.toList())
                );

                ArrayList< Integer > ratings = new ArrayList<>(
                        feedbackRatings.stream()
                                .map(feedback -> ( (FeedbackWithRating) feedback ).getRating())
                                .collect(Collectors.toList())
                );

                float avg = average(ratings);
                _labelRating.setText(( avg ) + "/ 10");

                DefaultListModel< String > feedbackModel = new DefaultListModel<>();
                feedbackModel.addAll(
                        feedbacks.stream().map(I_Feedback::getFeedback).collect(Collectors.toList())
                );

                _listFeedback.setModel(feedbackModel);
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

        _buttonSubmit.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = _listDoctors.getSelectedIndex();

                if( index > -1) {
                    Doctor doctor = _doctors.get(index);
                    submitAppointmentRequest(doctor);

                }else{
                    submitAppointmentRequest(null);
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
        _doctors = new ArrayList<>(_userRepositoryController.getRepository(UserRole.DOCTOR).get().stream().map(user -> ( (Doctor) user )).collect(Collectors.toList()));
        _listDoctors.setModel(getDoctorModel(_doctors));

        _comboDate.setModel(getComboDateModel());
        _spinnerHour.setModel(getSpinnerHourModel());
        _spinnerMinute.setModel(getSpinnerMinuteModel());
    }

    /**
     * Creates the ListDoctorsModel.
     *
     * @param doctors the array of doctors to be displayed.
     * @return the DefaultListModel object.
     */
    private DefaultListModel getDoctorModel(ArrayList< Doctor > doctors){
        DefaultListModel model = new DefaultListModel();
        for (Doctor doctor : doctors) model.addElement(doctor.toString());

        return model;
    }

    /**
     * Creates the ComboDateModel.
     *
     * @return the DefaultComboBoxModel object.
     */
    private DefaultComboBoxModel getComboDateModel(){
        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (int i = 1; i < 8; i++) {
            model.addElement(LocalDate.now().plusDays(i));
        }

        model.setSelectedItem(LocalDate.now());

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
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 45, 15);

        return model;
    }

    /**
     * Calculates the mean of an array of integers.
     *
     * @param integers an array of integers.
     * @return the mean value.
     */
    private float average(ArrayList< Integer > integers) {
        float avg = 0;

        for (int integer : integers) {
            avg += integer;
        }

        return avg / integers.size();
    }

    /**
     * Submits an appointment request.
     *
     * @param doctor the requested doctor.
     */
    private void submitAppointmentRequest(Doctor doctor){
        LocalDateTime dateTime = LocalDateTime.of((LocalDate) _comboDate.getSelectedItem(), LocalTime.of((Integer) _spinnerHour.getValue(), (Integer) _spinnerMinute.getValue()));

        Request request = new AppointmentRequest(( (Patient) _controller.getUser() ), doctor, dateTime);
        _requestRepositoryController.add(request);

        _controller.back();
    }
}
