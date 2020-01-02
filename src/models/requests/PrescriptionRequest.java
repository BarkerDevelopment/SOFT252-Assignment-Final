package models.requests;

import exceptions.StockLevelException;
import models.drugs.I_Prescription;
import models.users.Patient;

import java.time.LocalDate;

/**
 * A class that encapsulates a request for a prescription to be given to a patient.
 */
public class PrescriptionRequest extends Request {

    private final Patient _patient;
    private final I_Prescription _prescription;

    /**
     * Default constructor.
     * Additionally, this constructor adds the resultant object to its corresponding repository: RequestRepository.
     *
     * @param patient the patient the prescription needs to be delivered to.
     * @param prescription the prescription the patient needs.
     */
    public PrescriptionRequest(Patient patient, I_Prescription prescription) {
        super(RequestType.PRESCRIPTION);
        _patient = patient;
        _prescription = prescription;
    }

    /**
     * @return the patient that the prescription is prescribed to.
     */
    public Patient getPatient() {
        return _patient;
    }

    /**
     * @return the patient's prescription.
     */
    public I_Prescription getPrescription() {
        return _prescription;
    }

    /**
     * The action following request approval.
     */
    @Override
    protected void approveAction() throws StockLevelException {

    }

    /**
     * The action following request denial.
     */
    @Override
    protected void denyAction() {

    }
}
