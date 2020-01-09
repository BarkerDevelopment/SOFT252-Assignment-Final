package project.controllers.primary;

import project.views.I_Form;

import javax.swing.*;
import java.util.Stack;

/**
 * A controller that deals with the showing and hiding of the UIs.
 */
public class ViewController {
    private static ViewController INSTANCE;
    private JFrame _frame;
    private I_Form _current;
    private Stack< I_Form > _panelStack;

    /**
     * Singleton constructor.
     */
    private ViewController() {
        _frame = new JFrame();
        _panelStack = new Stack<>();
    }

    /**
     * @return the singleton instance of ViewController.
     */
    public static ViewController getInstance() {
        if(INSTANCE == null) INSTANCE = new ViewController();

        return INSTANCE;
    }

    /**
     * Initialise the frame with a panel and a title.
     *
     * @param form the initial form.
     * @param title the frame title.
     */
    public void initialise(I_Form form, String title){
        _frame.setTitle(title);
        _current = form;
        this.refresh();

        _frame.setVisible(true);
    }

    /**
     * Show a new panel and push the current one to the stack.
     *
     * @param form the new form to show.
     */
    public void show(I_Form form){
        _panelStack.push(_current);
        _current = form;

        this.refresh();
    }

    /**
     * Pop the previous panel from the stack and show it.
     */
    public void undo(){
        if(! _panelStack.empty()){
            _current = _panelStack.pop();

            this.refresh();
        }
    }

    /**
     * Refreshes the current frame.
     */
    public void refresh(){
        _frame.setContentPane(_current.getMainPanel());
        _frame.pack();
    }

    /**
     * Clear the panel stack.
     */
    public void clear(){
        _panelStack.clear();
    }

    /**
     *
     * @param message
     */
    public void createPopUp(String message){
        JOptionPane.showMessageDialog(_frame, message);
    }
}
