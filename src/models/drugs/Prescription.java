package models.drugs;

import java.time.LocalDate;

/**
 * An object that encapsulates a prescription of drugs.
 */
public class Prescription
        implements I_Prescription {
    private I_Treatment _treatment;
    private LocalDate _startDate;
    private int _qty;
    private int _course;

    /**
     * Default constructor.
     *
     * @param treatment the type of drug.
     * @param qty the quantity prescribed.
     * @param course how often to take the drug.
     */
    public Prescription(I_Treatment treatment, int qty, int course) {
        _treatment = treatment;
        _startDate = LocalDate.now();
        _qty = qty;
        _course = course;
    }

    /**
     * @return the _startDate variable. This describes the day which the treatment starts..
     */
    @Override
    public LocalDate getStartDate() {
        return _startDate;
    }

    /**
     * @return the _treatment variable. This represents the actual treatment prescribed.
     */
    @Override
    public I_Treatment getTreatment() {
        return _treatment;
    }

    /**
     * @return the _qty variable. This represents the number of individual treatments given.
     */
    @Override
    public int getQty() {
        return _qty;
    }

    /**
     * @return the _course variable. This describes how the quantity of the treatment should be take.
     */
    @Override
    public int getCourse() {
        return _course;
    }

    /**
     * @param startDate the new contents to set the _startDate to.
     */
    @Override
    public void setStartDate(LocalDate startDate) {
        _startDate = startDate;
    }

    /**
     * @param drug the new contents to set _drug to.
     */
    public void setTreatment(I_Treatment drug) {
        _treatment = drug;
    }

    /**
     * @param qty the new contents to set _qty to.
     */
    public void setQty(int qty) {
        _qty = qty;
    }

    /**
     * @param course the new contents to set _course to.
     */
    public void setCourse(int course) {
        _course = course;
    }
}
