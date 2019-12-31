package models.feedback;

import java.time.LocalDateTime;

/**
 * Defines the functions for an object that is used for feedback.
 */
public interface I_Feedback{
    /**
     * @return the _dateTime variable. Represents the date and time the feedback was sent.
     */
    public abstract LocalDateTime getDateTime();

    /**
     * @return the _feedback variable. Represents the contents of the feedback that was sent.
     */
    public abstract String getFeedback();

    /**
     * @param feedback the new contents of the _feedback variable.
     */
    public abstract void setFeedback(String feedback);
}
