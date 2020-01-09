package project.models.requests;

import project.controllers.auxiliary.MessageController;
import project.controllers.repository.UserRepositoryController;

import project.exceptions.ObjectNotFoundException;
import project.models.messaging.Message;
import project.models.users.User;

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
    public void approveAction() throws ObjectNotFoundException {
        UserRepositoryController.getInstance().remove(_requester);
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
