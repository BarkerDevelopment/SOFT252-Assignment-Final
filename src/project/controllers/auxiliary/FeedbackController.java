package project.controllers.auxiliary;

import project.controllers.repository.UserRepositoryController;
import project.models.feedback.I_Feedback;
import project.models.feedback.I_FeedbackRecipient;

import java.util.ArrayList;

public class FeedbackController {
    public static ArrayList< I_Feedback > get(I_FeedbackRecipient recipient){
        return recipient.getFeedback();
    }

    public static void send(I_FeedbackRecipient recipient, I_Feedback feedback){
        recipient.getFeedback().add(feedback);
        UserRepositoryController.getInstance().save();
    }
}
