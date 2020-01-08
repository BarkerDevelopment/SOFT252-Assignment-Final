package views.admin;

import controllers.primary.AdminController;
import controllers.primary.ViewController;
import controllers.repository.UserRepositoryController;
import exceptions.IdClashException;
import exceptions.OutOfRangeException;
import models.users.*;
import models.users.info.Address;
import models.users.info.Gender;
import models.users.info.ID;
import models.users.info.UserRole;
import views.I_Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class NewUser
        implements I_Form {
    private ViewController _viewController;
    private AdminController _controller;
    private UserRepositoryController _repositoryController;
    private ViewUsers _viewUsersForm;
    private UserRole _userRole;

    private JPanel _panelMain;
    private JSpinner _spinnerID;
    private JPasswordField _fieldPassword;
    private JTextField _fieldName;
    private JTextField _fieldSurname;
    private JTextField _fieldAddressFirst;
    private JTextField _fieldAddressSecond;
    private JTextField _fieldPostcode;
    private JTextField _fieldCounty;

    private JPanel _panelUserExtras;
    private JSpinner _spinnerDob;
    private JComboBox< Gender > _comboGender;

    private JButton _buttonSave;
    private JButton _buttonReturn;


    public NewUser(ViewController viewController, AdminController controller, UserRepositoryController repositoryController, ViewUsers viewUsersForm, UserRole role) {
        _viewController = viewController;
        _controller = controller;
        _repositoryController = repositoryController;
        _viewUsersForm = viewUsersForm;
        _userRole = role;

        if (! _userRole.equals(UserRole.PATIENT)){
            _panelUserExtras.setVisible(false);

        }else{
            _spinnerDob.setModel(getSpinnerDateModel());
            _comboGender.setModel(getComboGenderModel());
        }

        _spinnerID.setModel(getSpinnerIdModel());

        _buttonSave.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(areFieldsComplete()){

                    try {
                        User user = null;
                        switch (_userRole){
                            case ADMIN:
                                if(_fieldAddressSecond.getText().isEmpty()) {

                                        user = new Admin(
                                                convertIntToString((Integer) _spinnerID.getValue()),
                                                _fieldName.getText(),
                                                _fieldSurname.getText(),
                                                new Address(
                                                        _fieldAddressFirst.getText(),
                                                        _fieldPostcode.getText(),
                                                        _fieldCounty.getText()
                                                ),
                                                String.valueOf(_fieldPassword.getPassword())
                                        );


                                }else{
                                        user = new Admin(
                                                convertIntToString((Integer) _spinnerID.getValue()),
                                                _fieldName.getText(),
                                                _fieldSurname.getText(),
                                                new Address(
                                                        _fieldAddressFirst.getText(),
                                                        _fieldAddressSecond.getText(),
                                                        _fieldPostcode.getText(),
                                                        _fieldCounty.getText()
                                                ),
                                                String.valueOf(_fieldPassword.getPassword())
                                        );
                                }
                                break;

                            case DOCTOR:
                                if(_fieldAddressSecond.getText().isEmpty()) {
                                        user = new Doctor(
                                                convertIntToString((Integer) _spinnerID.getValue()),
                                                _fieldName.getText(),
                                                _fieldSurname.getText(),
                                                new Address(
                                                        _fieldAddressFirst.getText(),
                                                        _fieldPostcode.getText(),
                                                        _fieldCounty.getText()
                                                ),
                                                String.valueOf(_fieldPassword.getPassword())
                                        );

                                }else{
                                        user = new Doctor(
                                                convertIntToString((Integer) _spinnerID.getValue()),
                                                _fieldName.getText(),
                                                _fieldSurname.getText(),
                                                new Address(
                                                        _fieldAddressFirst.getText(),
                                                        _fieldAddressSecond.getText(),
                                                        _fieldPostcode.getText(),
                                                        _fieldCounty.getText()
                                                ),
                                                String.valueOf(_fieldPassword.getPassword())
                                        );
                                }
                                break;

                            case PATIENT:
                                if(_fieldAddressSecond.getText().isEmpty()) {
                                        user = new Patient(
                                                convertIntToString((Integer) _spinnerID.getValue()),
                                                _fieldName.getText(),
                                                _fieldSurname.getText(),
                                                new Address(
                                                        _fieldAddressFirst.getText(),
                                                        _fieldPostcode.getText(),
                                                        _fieldCounty.getText()
                                                ),
                                                String.valueOf(_fieldPassword.getPassword()),
                                                (LocalDate) _spinnerDob.getValue(),
                                                (Gender) _comboGender.getSelectedItem()
                                        );

                                }else{
                                        user = new Patient(
                                                convertIntToString((Integer) _spinnerID.getValue()),
                                                _fieldName.getText(),
                                                _fieldSurname.getText(),
                                                new Address(
                                                        _fieldAddressFirst.getText(),
                                                        _fieldAddressSecond.getText(),
                                                        _fieldPostcode.getText(),
                                                        _fieldCounty.getText()
                                                ),
                                                String.valueOf(_fieldPassword.getPassword()),
                                                (LocalDate) _spinnerDob.getValue(),
                                                (Gender) _comboGender.getSelectedItem()
                                        );
                                }
                                break;

                            case SECRETARY:
                                if(_fieldAddressSecond.getText().isEmpty()) {
                                        user = new Secretary(
                                                convertIntToString((Integer) _spinnerID.getValue()),
                                                _fieldName.getText(),
                                                _fieldSurname.getText(),
                                                new Address(
                                                        _fieldAddressFirst.getText(),
                                                        _fieldPostcode.getText(),
                                                        _fieldCounty.getText()
                                                ),
                                                String.valueOf(_fieldPassword.getPassword())
                                        );

                                }else{
                                        user = new Secretary(
                                                convertIntToString((Integer) _spinnerID.getValue()),
                                                _fieldName.getText(),
                                                _fieldSurname.getText(),
                                                new Address(
                                                        _fieldAddressFirst.getText(),
                                                        _fieldAddressSecond.getText(),
                                                        _fieldPostcode.getText(),
                                                        _fieldCounty.getText()
                                                ),
                                                String.valueOf(_fieldPassword.getPassword())
                                        );
                                }
                                break;
                        }

                            _repositoryController.add(user);
                            _viewUsersForm.update();
                            _controller.back();

                    } catch (OutOfRangeException ex) {
                        ex.printStackTrace();

                    } catch (IdClashException ex){
                        _viewController.createPopUp("The ID number you entered is already take, please enter another.");
                    }
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
    public void update() { }

    private SpinnerModel getSpinnerIdModel() {
        return new SpinnerNumberModel(0, 0, 9999, 1);
    }

    private SpinnerModel getSpinnerDateModel() {
        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.DATE);
        Date today = Date.valueOf(LocalDate.now());
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
        if(_fieldPassword.getPassword().length < 6){
            _viewController.createPopUp("Please enter a password greater than 6 characters.");
            return false;

        } else if(_fieldName.getText().isEmpty()) {
            _viewController.createPopUp("Please enter a first name.");
            return false;

        } else if(_fieldSurname.getText().isEmpty()) {
            _viewController.createPopUp("Please enter a surname.");
            return false;

        } else if(_fieldAddressFirst.getText().isEmpty()) {
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

    /**
     * Converts a string into a string of n digits.
     * @param number the number in question.
     * @return an n digit long string containing the passed number.
     */
    private String convertIntToString(int number){
        String string = String.valueOf(number);
        if(string.length() == ID.ID_LENGTH) {
            return String.valueOf(number);

        }else{
            int difference = ID.ID_LENGTH - string.length();

            return "0".repeat(Math.max(0, difference)) + number;
        }
    }
}
