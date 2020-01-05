package models.requests;

import controllers.auxiliary.MessageController;
import controllers.repository.UserRepositoryController;

import exceptions.ObjectNotFoundException;
import models.messaging.Message;
import models.users.User;

/**
 * A class that encapsulates a request to delete an existing Patient object from the repository.
 */
public class AccountTerminationRequest extends Request {

    private final User _requester;

    /**
     * Default constructor.
     * Additionally, this constructor adds the resultant object to its corresponding repository: RequestRepository.
     *
     * @param requester the Patient that requested the account termination.
     */
    public AccountTerminationRequest(User requester) {
        super(RequestType.ACCOUNT_TERMINATION);

        _requester = requester;
    }

    /**
     * @return the _requester variable.
     */
    public User getRequester() {
        return _requester;
    }

    /**
     * The action following request approval.
     */
    @Override
    public void approveAction() {
        try {
            UserRepositoryController.getInstance().remove(_requester);

        } catch (ObjectNotFoundException e) {
            //TODO handle this error properly.
            e.printStackTrace();
        }

    }

    /**
     * The action following request denial.
     */
    @Override
    public void denyAction() {
        MessageController.send(_requester,
                new Message(this, "Your request to terminate your account has been denied."))
        ;
    }
}
