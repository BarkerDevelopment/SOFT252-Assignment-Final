package project.views;

import javax.swing.*;

/**
 * Annotates an object as being a form.
 */
public interface I_Form {
    /**
     * @return the main panel of the form.
     */
    public abstract JPanel getMainPanel();

    /**
     * Update the contents of the form.
     */
    public abstract void update();
}
