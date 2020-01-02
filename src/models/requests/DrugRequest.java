package models.requests;

import exceptions.DuplicateDrugException;
import models.users.Doctor;

import java.util.ArrayList;

/**
 * A class that encapsulates a request for a new drug to be added to the system.
 */
public class DrugRequest extends Request {

    private final Doctor _doctor;
    private final String _name;
    private String _description;
    private ArrayList<String> _sideEffects;
    private int _startingQty;

    /**
     * A constructor that does not specify the quantity of the drug required.
     * Additionally, this constructor adds the resultant object to its corresponding repository: RequestRepository.
     *
     * @param requester the doctor that requested the drug.
     * @param name the name of the drug.
     */
    public DrugRequest(Doctor requester, String name) {
        super(RequestType.NEW_DRUG);

        _doctor = requester;
        _name = name;
        _sideEffects = new ArrayList<>();
    }

    /**
     * A constructor that does not specify the quantity of the drug required.
     * Additionally, this constructor adds the resultant object to its corresponding repository: RequestRepository.
     *
     * @param requester the doctor that requested the drug.
     * @param name the name of the drug.
     * @param qty the quantity required.
     */
    public DrugRequest(Doctor requester, String name, int qty) {
        super(RequestType.NEW_DRUG);

        _doctor = requester;
        _name = name;
        _sideEffects = new ArrayList<>();
        _startingQty = qty;
    }

    /**
     * @return the _doctor variable. This is the user that requested the new drug.
     */
    public Doctor getDoctor() {
        return _doctor;
    }

    /**
     * @return the _name variable. This is the name of the new drug.
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the _description variable.
     */
    public String getDescription() {
        return _description;
    }

    /**
     * @return the _sideEffects variable. This represents the known side effects of the drugs.
     */
    public ArrayList< String > getSideEffects() {
        return _sideEffects;
    }

    /**
     * @return the _startingQty variable.
     */
    public int getStartingQty() {
        return _startingQty;
    }

    /**
     * @param description the new drug description.
     */
    public void setDescription(String description) {
        _description = description;
    }

    /**
     * @param sideEffects the drugs side effects.
     */
    public void setSideEffects(ArrayList< String > sideEffects) {
        _sideEffects = sideEffects;
    }

    /**
     * @param startingQty the drug's starting stock level.
     */
    public void setStartingQty(int startingQty) {
        _startingQty = startingQty;
    }

    /**
     * The action following request approval.
     */
    @Override
    protected void approveAction() {

    }

    /**
     * The action following request denial.
     */
    @Override
    protected void denyAction() {

    }
}
