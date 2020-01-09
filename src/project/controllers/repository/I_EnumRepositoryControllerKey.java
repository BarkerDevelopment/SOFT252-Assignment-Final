package project.controllers.repository;

/**
 * Defines a Enum as a key for a EnumRepositoryController.
 */
public interface I_EnumRepositoryControllerKey {
    /**
     * @return the _fileName variable. Represents the file at which the serialised repository is stored.
     */
    public abstract String getFileName();
}
