package models.feedback;

import java.util.ArrayList;

/**
 * Defines the functions of an object that receives feedback.
 */
public interface I_FeedbackRecipient {
    /**
     * @return an ArrayList of all stored feedback.
     */
    public abstract ArrayList<I_Feedback> getFeedback();

    /**
     * @param feedback an ArrayList of feedback to be stored. This replaces the current ArrayList.
     */
    public abstract void setFeedback(ArrayList<I_Feedback> feedback);
}
