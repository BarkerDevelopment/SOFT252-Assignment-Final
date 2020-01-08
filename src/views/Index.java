package views;

import controllers.primary.AdminController;
import controllers.primary.I_UserController;
import controllers.primary.ViewController;
import models.messaging.I_Message;
import models.users.User;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Index implements I_Form {
    protected ViewController _viewController;
    protected I_UserController _controller;
    protected User _user;
    private final String[] _columnNames = { "Date", "Time", "Message" };

    public Index(ViewController viewController, I_UserController controller, User user) {
        _viewController = viewController;
        _controller = controller;
        _user = user;
    }

    protected DefaultTableModel getTableMessageModel(ArrayList< I_Message > messages){
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
