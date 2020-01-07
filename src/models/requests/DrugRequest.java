package models.requests;

import controllers.auxiliary.MessageController;
import controllers.repository.DrugRepositoryController;
import controllers.repository.UserRepositoryController;
import exceptions.DuplicateObjectException;
import models.drugs.DrugStock;
import models.messaging.Message;
import models.repositories.Repository;
import models.users.Doctor;
import models.users.info.UserRole;

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
        _description = "";
        _startingQty = 0;
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
    public DrugRequest(Doctor requester, String name, String description, ArrayList<String> sideEffects, int qty) {
        super(RequestType.NEW_DRUG);

        _doctor = requester;
        _name = name;
        _description = description;
        _startingQty = qty;
        _sideEffects = sideEffects;
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
        save();
    }

    /**
     * @param sideEffects the drugs side effects.
     */
    public void setSideEffects(ArrayList< String > sideEffects) {
        _sideEffects = sideEffects;
        save();
    }

    /**
     * @param startingQty the drug's starting stock level.
     */
    public void setStartingQty(int startingQty) {
        _startingQty = startingQty;
        save();
    }

    /**
     * The action following request approval.
     */
    @Override
    public void approveAction() {
        try{
            DrugRepositoryController.getInstance().add(new DrugStock(this));

            MessageController.send(_doctor, new Message(this,
                    String.format("Your request for a new drug - %s - has been approved.", _name)
            ));

            Repository doctorRepository = UserRepositoryController.getInstance().getRepository(UserRole.DOCTOR);
            doctorRepository.get().forEach(doctor ->
                    MessageController.send(_doctor, new Message(this,
                            String.format("%s is now available for prescription.", _name)
                    ))
            );

        }catch (DuplicateObjectException e){
            denyAction();
        }
    }

    /**
     * The action following request denial.
     */
    @Override
    public void denyAction() {
        MessageController.send(_doctor, new Message(this,
                String.format("Your request for a new drug - %s - has been denied.", _name)
        ));
    }
}
