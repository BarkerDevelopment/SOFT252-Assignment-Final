package models.requests;

import controllers.repository.I_EnumRepositoryControllerKey;

public enum RequestType implements I_EnumRepositoryControllerKey {
    ACCOUNT_CREATION,
    ACCOUNT_TERMINATION,
    APPOINTMENT,
    PRESCRIPTION,
    NEW_DRUG;
}
