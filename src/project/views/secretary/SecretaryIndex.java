package project.views.secretary;

import project.controllers.primary.SecretaryController;
import project.controllers.primary.ViewController;
import project.views.Index;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Bound class to SecretaryIndex form.
 */
public class SecretaryIndex
        extends Index {
    private JPanel _panelMain;
    private JLabel _labelUserId;

    private JButton _buttonViewRequests;
    private JButton _viewDrugsButton;
    private JButton _buttonRemove;
    private JButton _logoutButton;
    private JTable _tableMessages;

    /**
     * Default constructor.
     * @param viewController the view controller.
     * @param controller the view's controller.
     */
    public SecretaryIndex(ViewController viewController, SecretaryController controller) {
        super(viewController, controller, controller.getUser());

        _buttonViewRequests.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ( (SecretaryController) _controller ).viewRequests();
            }
        });

        _viewDrugsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ( (SecretaryController) _controller ).viewDrugs();
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
                removeMessage(_tableMessages);
            }
        });

        _logoutButton.addActionListener(new ActionListener() {
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

        this.update();
        return _panelMain;
    }

    /**
     * Update the contents of the form.
     */
    @Override
    public void update() {
        _labelUserId.setText(_user.getId().toString());
        _tableMessages.setModel(getTableMessageModel(_user.getMessages()));
    }
}
