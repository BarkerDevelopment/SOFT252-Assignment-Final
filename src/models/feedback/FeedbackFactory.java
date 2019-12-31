package models.feedback;


import exceptions.OutOfRangeException;

/**
 * A class that creates I_Feedback objects.
 */
public class FeedbackFactory {

    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 10;

    /**
     * Creates a feedback object.
     *
     * @param string the feedback string.
     * @return the feedback object. This is of type Feedback.
     */
    public I_Feedback create(String string) {
        return new Feedback(string);
    }

    /**
     * Creates a feedback object. If the rating is null, it creates a Feedback object where as if the rating is an
     * positive integer, it creates a FeedbackWithRating object.
     *
     * @param string the feedback string.
     * @param rating the feedback rating.
     * @return the feedback object.
     * @throws OutOfRangeException if rating is out of the specified range.
     */
    public I_Feedback create(String string, Integer rating) throws OutOfRangeException {

        if(rating == null){
            return new Feedback(string);

        }else{
            if(rating >= MIN_RATING && rating <= MAX_RATING){
                return new FeedbackWithRating(string, rating);

            }else{
                throw new OutOfRangeException(String.format("Feedback rating must be between %d and %d (inclusive).", MIN_RATING, MAX_RATING));
            }
        }
    }
}

