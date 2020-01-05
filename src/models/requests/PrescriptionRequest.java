package models.requests;

import controllers.auxiliary.MessageController;
import controllers.repository.DrugRepositoryController;
import exceptions.ObjectNotFoundException;
import exceptions.StockLevelException;
import models.I_Observer;
import models.drugs.I_Prescription;
import models.messaging.Message;
import models.users.Patient;

/**
 * A class that encapsulates a request for a prescription to be given to a patient.
 */
public class PrescriptionRequest extends Request
        implements I_Observer< Integer > {

    private final Patient _patient;
    private final I_Prescription _prescription;

    private int _drugStock;

    /**
     * Default constructor.
     * Additionally, this constructor adds the resultant object to its corresponding repository: RequestRepository.
     *
     * @param patient the patient the prescription needs to be delivered to.
     * @param prescription the prescription the patient needs.
     */
    public PrescriptionRequest(Patient patient, I_Prescription prescription) throws ObjectNotFoundException {
        super(RequestType.PRESCRIPTION);
        _patient = patient;
        _prescription = prescription;
        _drugStock = 0;

        DrugRepositoryController.getInstance().get(_prescription.getTreatment()).subscribe(this);
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
     * @return the _drugStock variable. Represents the stock of the drug in the repository.
     */
    public int getDrugStock() {
        return _drugStock;
    }

    /**
     * Updates a variable within the Observer.
     *
     * @param item the variable to update.
     */
    @Override
    public void update(Integer item) {
        _drugStock = item;
    }

    /**
     * The action following request approval.
     */
    @Override
    public void approveAction() throws StockLevelException, ObjectNotFoundException {
        if(_prescription.getQty() <= _drugStock){
            DrugRepositoryController.getInstance().updateStock(_prescription.getTreatment(), -(_drugStock));
            _patient.getPrescriptions().add( _prescription );

            MessageController.send(_patient, new Message(this,
                    String.format("You may now collect your prescription of %d %s from a member of staff at reception.",
                            _prescription.getQty(), _prescription.getTreatment().getName())
            ));

        }else{
            throw new StockLevelException();
        }
    }

    /**
     * The action following request denial.
     */
    @Override
    public void denyAction() {
        MessageController.send(_patient, new Message(this,
                String.format("Your prescription of %d %s has been cancelled.",
                        _prescription.getQty(), _prescription.getTreatment().getName())
        ));
    }
}
