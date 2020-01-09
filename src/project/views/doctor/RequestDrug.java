package project.views.doctor;

import project.controllers.primary.DoctorController;
import project.controllers.primary.ViewController;
import project.controllers.repository.RequestRepositoryController;
import project.models.requests.DrugRequest;
import project.models.users.Doctor;
import project.views.I_Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestDrug implements I_Form {
    private ViewController _viewController;
    private DoctorController _controller;
    private RequestRepositoryController _repositoryController;

    private JPanel _panelMain;
    private JTextField _fieldName;
    private JTextField _fieldDescription;
    private JTextArea _fieldSideEffects;
    private JSpinner _spinnerQty;
    private JButton _buttonSubmit;
    private JButton _buttonReturn;

    public RequestDrug(ViewController viewController, DoctorController controller, RequestRepositoryController repositoryController) {
        _viewController = viewController;
        _controller = controller;
        _repositoryController = repositoryController;

        _buttonSubmit.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(areFieldsComplete()){
                    _repositoryController.add(
                            new DrugRequest(
                                    ( (Doctor) _controller.getUser() ),
                                    _fieldName.getText(),
                                    _fieldDescription.getText(),
                                    cvsStringToArray(_fieldSideEffects.getText()),
                                    ( (Integer) _spinnerQty.getValue() )
                            )
                    );

                    _viewController.createPopUp("Drug request submitted.");
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
    public void update() { }

    /**
     * Checks to see if any fields are empty.
     * @return TRUE if all fields are complete, FALSE otherwise.
     */
    private boolean areFieldsComplete(){
        if(_fieldName.getText().isEmpty()) {
            _viewController.createPopUp("Please enter a drug name.");
            return false;

        } else if(_fieldDescription.getText().isEmpty()) {
            _viewController.createPopUp("Please enter a drug description.");
            return false;

        } else if(_fieldSideEffects.getText().isEmpty()) {
            _viewController.createPopUp("Please enter all known side effects.");
            return false;

        } else return true;
    }

    /**
     *
     * @param cvsString
     * @return
     */
    private ArrayList< String > cvsStringToArray(String cvsString){
        return new ArrayList<>(Arrays.asList(cvsString.split(",")));
    }
}
