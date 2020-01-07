package controllers.primary;

import javax.swing.*;
import java.util.Stack;

/**
 * A controller that deals with the showing and hiding of the UIs.
 */
public class ViewController {
    private static ViewController INSTANCE;
    private JFrame _frame;
    private Stack< JPanel > _panelStack;

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
     * @param panel the initial panel.
     * @param title the frame title.
     */
    public void initialise(JPanel panel, String title){
        _frame.setTitle(title);
        _frame.setContentPane(panel);
        _frame.pack();

        _frame.setVisible(true);
    }

    /**
     * Show a new panel and push the current one to the stack.
     *
     * @param panel the new panel to show.
     */
    public void show(JPanel panel){
        _panelStack.push((JPanel) _frame.getContentPane());
        _frame.setContentPane(panel);
        _frame.pack();
    }

    /**
     * Pop the previous panel from the stack and show it.
     */
    public void undo(){
        if(! _panelStack.empty()){
            _frame.setContentPane(_panelStack.pop());
            _frame.pack();
        }
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
