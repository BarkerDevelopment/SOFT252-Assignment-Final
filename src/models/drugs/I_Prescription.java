package models.drugs;

import java.time.LocalDate;

/**
 * Defines the functions for a prescription object.
 */
public interface I_Prescription {
    /**
     * @return the _startDate variable. Represents the day which the treatment starts.
     */
    public LocalDate getStartDate();

    /**
     * @return the _treatment variable. Represents the actual treatment prescribed.
     */
    public I_Treatment getTreatment();

    /**
     * @return the _qty variable. Represents the number of individual treatments given.
     */
    public int getQty();

    /**
     * @return the _course variable. Represents the number of days over which the treatment should be completed.
     */
    public int getCourse();

    /**
     * @param startDate the new contents of the _startDate variable.
     */
    public void setStartDate(LocalDate startDate);
}
