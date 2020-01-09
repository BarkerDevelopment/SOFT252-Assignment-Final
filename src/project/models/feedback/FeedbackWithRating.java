package project.models.feedback;

/**
 * Feedback subclass that includes a rating.
 */
public class FeedbackWithRating extends Feedback{
    private final int _rating;

    /**
     * Creates a feedback object with a rating.
     *
     * @param feedback the feedback string.
     * @param rating the feedback rating.
     */
    public FeedbackWithRating(String feedback, int rating){
        super(feedback);
        _rating = rating;
    }

    /**
     * @return the _rating variable. This represents the rating associated with the feedback.
     */
    public int getRating() {
        return _rating;
    }
}
