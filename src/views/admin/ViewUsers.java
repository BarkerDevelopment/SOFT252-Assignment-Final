package views.admin;

import controllers.primary.AdminController;
import controllers.primary.ViewController;
import controllers.repository.UserRepositoryController;
import exceptions.ObjectNotFoundException;
import models.feedback.FeedbackWithRating;
import models.feedback.I_Feedback;
import models.users.*;
import models.users.info.UserRole;
import views.I_Form;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.stream.Collectors;

public class ViewUsers
        implements I_Form {
    private ViewUsers _this;

    private ViewController _viewController;
    private AdminController _controller;
    private UserRepositoryController _repositoryController;
    private EnumMap< UserRole, ArrayList< User > > _users;

    private JPanel _panelMain;
    private JTabbedPane _tabbedPanel;
    private JTable _tableAdmins;
    private JTable _tablePatients;
    private JTable _tableSecretaries;
    private JList< String > _listDoctors;
    private JList< String > _listFeedback;
    private JLabel _labelRating;
    private JButton _buttonRemove;
    private JButton _buttonAdd;
    private JButton _buttonReturn;

    public ViewUsers(ViewController viewController, AdminController controller, UserRepositoryController repositoryController) {
        _this = this;
        _viewController = viewController;
        _controller = controller;
        _repositoryController = repositoryController;

        _users = new EnumMap< >(UserRole.class);
        this.update();

        _tabbedPanel.setSelectedIndex(0);

        _listDoctors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _listDoctors.setLayoutOrientation(JList.VERTICAL);

        _listDoctors.addListSelectionListener(new ListSelectionListener() {
            /**
             * Called whenever the value of the selection changes.
             *
             * @param e the event that characterizes the change.
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {

                Doctor doctor = ( (Doctor) _users.get(UserRole.DOCTOR).get(_listDoctors.getSelectedIndex()) );

                _labelRating.setText(( getDoctorRatingAverage(doctor) ) + "/ 10");

                _listFeedback.setModel(getTableFeedbackModel(doctor));
            }
        });

        _buttonAdd.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int tabIndex = _tabbedPanel.getSelectedIndex();

                UserRole role;
                switch (tabIndex){
                    case 0:
                        role = UserRole.ADMIN;
                        break;
                    case 1:
                        role = UserRole.DOCTOR;
                        break;
                    case 2:
                        role = UserRole.PATIENT;
                        break;
                    case 3:
                        role = UserRole.SECRETARY;
                        break;
                    default:
                        role = null;
                }

                if(role != null){
                    _controller.createUser(role);

                }else{
                    _viewController.createPopUp("ERROR: Unidentified user role.");
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
                int tabIndex = _tabbedPanel.getSelectedIndex();

                int index;
                User selectedUser;
                switch (tabIndex){
                    case 0:
                        index = _tableAdmins.getSelectedRow();
                        selectedUser = _users.get(UserRole.ADMIN).get(index);
                        ( (DefaultTableModel) _tableAdmins.getModel() ).removeRow(index);
                        break;

                    case 1:
                        index = _listDoctors.getSelectedIndex();
                        selectedUser = _users.get(UserRole.DOCTOR).get(_listDoctors.getSelectedIndex());
                        _listDoctors.remove(index);
                        break;

                    case 2:
                        index = _tablePatients.getSelectedRow();
                        selectedUser = _users.get(UserRole.PATIENT).get(index);
                        ( (DefaultTableModel) _tableSecretaries.getModel() ).removeRow(index);
                        break;

                    case 3:
                        index = _tableSecretaries.getSelectedRow();
                        selectedUser = _users.get(UserRole.SECRETARY).get(index);
                        ( (DefaultTableModel) _tableSecretaries.getModel() ).removeRow(index);
                        break;

                    default:
                        selectedUser = null;
                        break;
                }

                if(selectedUser != null){
                    try {
                        _repositoryController.remove(selectedUser);
                        _viewController.createPopUp(String.format("User %s has been deleted.", selectedUser.getId().toString()));

                    } catch (ObjectNotFoundException ex) {
                        ex.printStackTrace();
                    }

                }else{
                    _viewController.createPopUp("Please select a user.");
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
        return _panelMain;
    }

    /**
     * Updates the tables and lists of the form with new data.
     */
    public void update(){
        _users.put(UserRole.ADMIN, new ArrayList<>(_repositoryController.getRepository(UserRole.ADMIN).get().stream().map(user -> ( (Admin) user )).collect(Collectors.toList())));
        _users.put(UserRole.DOCTOR, new ArrayList<>(_repositoryController.getRepository(UserRole.DOCTOR).get().stream().map(user -> ( (Doctor) user )).collect(Collectors.toList())));
        _users.put(UserRole.PATIENT, new ArrayList<>(_repositoryController.getRepository(UserRole.PATIENT).get().stream().map(user -> ( (Patient) user )).collect(Collectors.toList())));
        _users.put(UserRole.SECRETARY, new ArrayList<>(_repositoryController.getRepository(UserRole.SECRETARY).get().stream().map(user -> ( (Secretary) user )).collect(Collectors.toList())));

        _tableAdmins.setModel(getTableUserModel(_users.get(UserRole.ADMIN)));
        _listDoctors.setModel(getListDoctorModel(_users.get(UserRole.DOCTOR)));
        _tablePatients.setModel(getTablePatientModel(_users.get(UserRole.PATIENT)));
        _tableSecretaries.setModel(getTableUserModel(_users.get(UserRole.SECRETARY)));
    }

    /**
     * @param users the array of patients to display.
     * @return the created TableModel.
     */
    private DefaultTableModel getTablePatientModel(ArrayList< User > users){
        String[] columns = {"ID", "Name", "Address", "DOB", "Gender"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (User user : users){
            Patient patient = ( (Patient) user );
            String[] row = new String[columns.length];
            row[0] = patient.getId().toString();
            row[1] = String.format("%s, %s", patient.getSurname(), patient.getName());
            row[2] = patient.getAddress().toString();
            row[3] = patient.getDob().toString();
            row[4] = patient.getGender().toString();

            model.addRow(row);
        }

        return model;
    }

    /**
     * @param users the array of users to display.
     * @return the created TableModel.
     */
    private DefaultTableModel getTableUserModel(ArrayList< User > users){
        String[] columns = {"ID", "Name", "Address"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (User user : users){
            String[] row = new String[columns.length];
            row[0] = user.getId().toString();
            row[1] = String.format("%s, %s", user.getSurname(), user.getName());
            row[2] = user.getAddress().toString();

            model.addRow(row);
        }

        return model;
    }

    /**
     * @param users the array of doctors to display.
     * @return the created ListModel.
     */
    private DefaultListModel< String > getListDoctorModel(ArrayList< User > users){
        DefaultListModel< String > model = new DefaultListModel<>();
        model.addAll(
                users.stream().map(Object::toString).collect(Collectors.toList())
        );

        return model;
    }

    /**
     * @param doctor the target doctor.
     * @return the created ListModel.
     */
    private DefaultListModel< String >  getTableFeedbackModel(Doctor doctor){
        ArrayList< I_Feedback > feedbacks = doctor.getFeedback();

        DefaultListModel< String > model = new DefaultListModel<>();
        model.addAll(
                feedbacks.stream().map(I_Feedback::getFeedback).collect(Collectors.toList())
        );

        return model;
    }

    /**
     * Calculates a doctors average rating.
     * @param doctor the target doctor.
     * @return the average rating.
     */
    private float getDoctorRatingAverage(Doctor doctor){
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

        return average(ratings);
    }

    /**
     * Calculates the average value in an ArrayList of integers.
     * @param integers the list of integers.
     * @return the mean value.
     */
    private float average(ArrayList< Integer > integers) {
        float avg = 0;

        for (int integer : integers) {
            avg += integer;
        }

        return avg / integers.size();
    }


}
