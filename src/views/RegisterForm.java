package views;

import controllers.primary.ViewController;
import controllers.primary.login.LoginController;
import controllers.repository.RequestRepositoryController;
import models.requests.AccountCreationRequest;
import models.users.info.Address;
import models.users.info.Gender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class RegisterForm implements I_Form{
    private ViewController _viewController;
    private LoginController _controller;
    private RequestRepositoryController _repositoryController;

    private JTextField _fieldName;
    private JTextField _fieldSurname;
    private JPasswordField _fieldPassword;
    private JPasswordField _fieldPasswordRetype;
    private JTextField _fieldFirstLine;
    private JTextField _fieldSecondLine;
    private JTextField _fieldPostcode;
    private JTextField _fieldCounty;
    private JSpinner _spinnerDOB;
    private JComboBox< Gender > _comboGender;
    private JButton _buttonSubmit;
    private JButton _buttonReturn;
    private JPanel _panelMain;

    public RegisterForm(ViewController viewController, LoginController controller, RequestRepositoryController repositoryController) {
        _viewController = viewController;
        _controller = controller;
        _repositoryController = repositoryController;

        _spinnerDOB.setModel(getSpinnerDateModel());
        _spinnerDOB.setEditor(new JSpinner.DateEditor(_spinnerDOB, new SimpleDateFormat("DD/MM/YYYY").toPattern()));
        _comboGender.setModel(getComboGenderModel());

        _buttonSubmit.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if(areFieldsComplete()){
                    AccountCreationRequest request;

                    if(_fieldSecondLine.getText().isEmpty()){
                        request = new AccountCreationRequest(
                                _fieldName.getText(),
                                _fieldSurname.getText(),
                                new Address(
                                    _fieldFirstLine.getText(),
                                    _fieldPostcode.getText(),
                                    _fieldCounty.getText()
                                ),
                                String.valueOf(_fieldPassword.getPassword()),
                                ( (Date) _spinnerDOB.getValue() ).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                ( (Gender) _comboGender.getSelectedItem() )
                        );

                    }else{
                        request = new AccountCreationRequest(
                                _fieldName.getText(),
                                _fieldSurname.getText(),
                                new Address(
                                    _fieldFirstLine.getText(),
                                    _fieldSecondLine.getText(),
                                    _fieldPostcode.getText(),
                                    _fieldCounty.getText()
                                ),
                                String.valueOf(_fieldPassword.getPassword()),
                                ( (Date) _spinnerDOB.getValue() ).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                ( (Gender) _comboGender.getSelectedItem() )
                        );
                    }

                    _repositoryController.add( request );
                    _viewController.createPopUp( "Account request submitted.");
                    _controller.back();
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
    public void update() {}

    private SpinnerModel getSpinnerDateModel() {
        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.DATE);
        Date today = Date.from(Instant.now());
        model.setEnd(today);
        model.setValue(today);

        return model;
    }

    /**
     * @return the ComboBoxModel.
     */
    private ComboBoxModel< Gender > getComboGenderModel() {
        ComboBoxModel< Gender > model = new DefaultComboBoxModel< >(Gender.values());
        model.setSelectedItem(Gender.MALE);

        return model;
    }

    /**
     * Checks to see if any fields are empty.
     * @return TRUE if all fields are complete, FALSE otherwise.
     */
    private boolean areFieldsComplete(){
        if(_fieldPassword.getPassword().length < 6 ) {
            _viewController.createPopUp("Please enter a password greater than 6 characters.");
            return false;

        }else if(! Arrays.equals(_fieldPassword.getPassword(), _fieldPasswordRetype.getPassword())){
            _viewController.createPopUp("Please ensure your passwords match.");
            return false;

        } else if(_fieldName.getText().isEmpty()) {
            _viewController.createPopUp("Please enter a first name.");
            return false;

        } else if(_fieldSurname.getText().isEmpty()) {
            _viewController.createPopUp("Please enter a surname.");
            return false;

        } else if(_fieldFirstLine.getText().isEmpty()) {
            _viewController.createPopUp("Please enter the first line of your address.");
            return false;

        } else if(! Address.isPostcodeValid(_fieldPostcode.getText())) {
            _viewController.createPopUp("Please enter a valid postcode.");
            return false;

        } else if(_fieldCounty.getText().isEmpty()) {
            _viewController.createPopUp("Please enter a county.");
            return false;

        } else return true;
    }
}
