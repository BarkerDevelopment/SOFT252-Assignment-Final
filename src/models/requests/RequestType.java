package models.requests;

import controllers.repository.I_EnumRepositoryControllerKey;

/**
 * An enumeration representing the possible request types.
 */
public enum RequestType implements I_EnumRepositoryControllerKey {
    ACCOUNT_CREATION("account_creation_requests"),
    ACCOUNT_TERMINATION("account_termination_requests"),
    APPOINTMENT("appointment_requests"),
    PRESCRIPTION("prescription_requests"),
    NEW_DRUG("drug_requests");

    private String _fileName;

    /**
     * Enum constructor assigning input variables.
     *
     * @param fileName the file destination of the repository content.
     */
    private RequestType(String fileName) {
        _fileName = fileName;
    }

    /**
     * @return the _fileName variable. Represents the file at which the serialised repository is stored.
     */
    @Override
    public String getFileName() {
        return _fileName;
    }
}
