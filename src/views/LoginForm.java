package views;

import controllers.primary.ViewController;
import controllers.primary.login.I_LoginState;
import controllers.primary.login.LoggedInState;
import controllers.primary.login.LoggedOutState;
import controllers.primary.login.LoginController;
import exceptions.LoginException;
import models.users.info.ID;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginForm
        implements I_Form{
    private LoginController _controller;

    private JPanel _panelMain;
    private JPasswordField _fieldPassword;
    private JTextField _fieldUsername;
    private JButton _btnLogin;

    public LoginForm(LoginController controller) {
        _controller = controller;

        _btnLogin.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param event the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent event) {
                I_LoginState state = _controller.getState();

                if(state instanceof LoggedOutState){
                    LoggedOutState outState = (LoggedOutState) state;

                    outState.setUsername(_fieldUsername.getText());
                    outState.setPassword(charArrayToString(_fieldPassword.getPassword()));

                    try{
                        _controller.login();
                        System.out.println(
                                String.format("%s logged in.", ((LoggedInState)_controller.getState()).getUser().getId())
                        );

                    }catch (LoginException e){
                        ViewController.getInstance().createPopUp(e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public JPanel getMainPanel() {
        return _panelMain;
    }

    /**
     * Converts an array of characters to a strings.
     * @param chars input array of characters.
     * @return string.
     */
    private String charArrayToString(char[] chars){
        StringBuilder stringBuilder = new StringBuilder();

        for (char character : chars) stringBuilder.append(character);

        return stringBuilder.toString();
    }
}
