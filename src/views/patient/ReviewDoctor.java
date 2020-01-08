package views.patient;

import controllers.auxiliary.FeedbackController;
import controllers.primary.PatientController;
import exceptions.OutOfRangeException;
import models.feedback.FeedbackFactory;
import models.feedback.I_Feedback;
import models.users.Doctor;
import views.I_Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Bound class to the ReviewDoctor form.
 */
public class ReviewDoctor
        implements I_Form {
    private PatientController _controller;
    private Doctor _doctor;

    private JPanel _panelMain;
    private JLabel _doctorName;
    private JSpinner _spinnerRating;
    private JTextArea _textAreaComments;
    private JButton _buttonSubmit;
    private JButton _returnButton;

    /**
     * Default constructor.
     *
     * @param controller the view's controller.
     * @param doctor the target doctor.
     */
    public ReviewDoctor(PatientController controller, Doctor doctor) {
        _controller = controller;
        _doctor = doctor;
        _doctorName.setText(doctor.toString());

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

        _buttonSubmit.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FeedbackFactory f = new FeedbackFactory();
                    I_Feedback feedback = f.create(_textAreaComments.getText(), (Integer) _spinnerRating.getValue());

                    FeedbackController.send(_doctor, feedback);
                    _controller.back();

                }catch (OutOfRangeException ex){
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
        _spinnerRating.setModel(new SpinnerNumberModel(FeedbackFactory.MIN_RATING, FeedbackFactory.MIN_RATING, FeedbackFactory.MAX_RATING, 1));
    }
}
