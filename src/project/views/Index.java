package project.views;

import project.controllers.primary.I_UserController;
import project.controllers.primary.ViewController;
import project.controllers.repository.UserRepositoryController;
import project.models.messaging.I_Message;
import project.models.users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Index implements I_Form {
    protected ViewController _viewController;
    protected I_UserController _controller;
    protected User _user;

    /**
     * Default constructor.
     *
     * @param viewController the view controller.
     * @param controller the view's controller.
     * @param user the logged in user.
     */
    public Index(ViewController viewController, I_UserController controller, User user) {
        _viewController = viewController;
        _controller = controller;
        _user = user;
    }

    /**
     * @param messages the list of messages of the user.
     * @return the TableMessageModel object.
     */
    protected DefaultTableModel getTableMessageModel(ArrayList< I_Message > messages){
        String[] columnNames = { "Date", "Time", "Message" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (I_Message message : messages){
            String[] row = new String[3];

            LocalDateTime dateTime = message.getDatetime();
            row[0] = dateTime.toLocalDate().toString();
            row[1] = dateTime.toLocalTime().toString();
            row[2] = message.getMessage();

            model.addRow(row);
        }

        return model;
    }

    /**
     * Removes a message from the messageTable and the user.
     * @param messageTable the target messageTable.
     */
    protected void removeMessage(JTable messageTable){
        int index = messageTable.getSelectedRow();

        if(index > -1){
            _user.getMessages().remove(index);
            UserRepositoryController.getInstance().save();
            update();

        }else{
            _viewController.createPopUp("Please select a message to delete.");
        }
    }
}
