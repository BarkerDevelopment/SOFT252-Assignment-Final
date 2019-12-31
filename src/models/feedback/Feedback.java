package models.feedback;

import java.time.LocalDateTime;

/**
 * Generic feedback class.
 */
public class Feedback
    implements I_Feedback{

    protected final LocalDateTime _dateTime;
    protected String _feedback;
    protected Boolean _isModerated;

    /**
     * Creates a feedback object.
     *
     * @param feedback the feedback string.
     */
    public Feedback(String feedback){
        _dateTime = LocalDateTime.now();
        _feedback = feedback;
        _isModerated = false;
    }

    /**
     * @return the _dateTime variable. This represents the DateTime the feedback was sent.
     */
    @Override
    public LocalDateTime getDateTime() {
        return _dateTime;
    }

    /**
     * @return the _feedback variable. This represents the contents of the feedback that was sent.
     */
    @Override
    public String getFeedback() {
        return _feedback;
    }

    /**
     * @param feedback the new contents of the _feedback variable.
     */
    @Override
    public void setFeedback(String feedback) {
        _feedback = feedback;
    }
}
