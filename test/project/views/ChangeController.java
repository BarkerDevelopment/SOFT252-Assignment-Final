package project.views;

import javax.swing.*;

public class ChangeController {
    private JFrame _frame;
    private JPanel _panel;

    public ChangeController() {
        _frame = new JFrame();
        _panel = new ViewChange(this).getMainPanel();
    }

    public ChangeController(JPanel panel) {
        _frame = new JFrame();
        _panel = panel;
    }

    public void open(){
        _frame.setTitle("");
        _frame.setContentPane(_panel);
        _frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _frame.pack();
        _frame.setVisible(true);

    }

    public void listTest(){
        changeView(new ListTest());
    }

    private void changeView(I_Form form){
        _frame.setContentPane(form.getMainPanel());
        _frame.pack();
    }
}
