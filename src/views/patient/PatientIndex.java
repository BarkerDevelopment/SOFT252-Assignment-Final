package views.patient;

import controllers.primary.PatientController;
import models.messaging.I_Message;
import models.messaging.Message;
import views.I_Form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PatientIndex
        implements I_Form {
    private PatientController _controller;
    private ArrayList< I_Message > _messages;
    private String[] _columnNames = { "Date", "Time", "Message" };

    private JPanel _panelMain;
    private JTable _tableMessages;
    private JButton _buttonRemoveMessages;
    private JButton _buttonLogout;
    private JButton _buttonViewAppointments;
    private JButton _buttonViewPrescriptions;


    public PatientIndex(PatientController controller, ArrayList< I_Message > messages) {
        _controller = controller;

        _tableMessages.setModel(getTableMessageModel(messages));
        _tableMessages.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        _buttonViewAppointments.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _controller.viewAppointments();
            }
        });

        _buttonViewPrescriptions.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _controller.viewPrescriptions();
            }
        });

        _buttonRemoveMessages.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        _buttonLogout.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _controller.logout();
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

    private DefaultTableModel getTableMessageModel(ArrayList< I_Message > messages){
        DefaultTableModel model = new DefaultTableModel(_columnNames, 0);
        for (I_Message message : messages){
            String[] row = new String[3];

            LocalDateTime dateTime = message.getDatetime();
            row[0] = dateTime.toLocalDate().toString();
            row[1] = dateTime.toLocalDate().toString();
            row[2] = message.getMessage();

            model.addRow(row);
        }

        return model;
    }
}
