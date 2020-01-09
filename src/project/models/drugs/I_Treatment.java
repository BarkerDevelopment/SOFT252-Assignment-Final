package project.models.drugs;

import java.util.ArrayList;

/**
 * Defines the functions for a Treatment object.
 */
public interface I_Treatment {
    /**
     * @return the _name variable. Represents the name of the drug.
     */
    public abstract String getName();

    /**
     * @return the _description variable. Represents the description of the drug.
     */
    public abstract String getDescription();

    /**
     * @return the _sideEffects variable. Represents the known side effects of the drug.
     */
    public abstract ArrayList< String > getSideEffects();

    /**
     * @param description the new contents of the _description variable.
     */
    public abstract void setDescription(String description);

    /**
     * @param sideEffects the new contents of the _sideEffects variable.
     */
    public abstract void setSideEffects(ArrayList< String > sideEffects);
}
