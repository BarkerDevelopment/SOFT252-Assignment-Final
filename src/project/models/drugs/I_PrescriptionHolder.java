package project.models.drugs;

import java.util.ArrayList;

/**
 * Defines the functions of an object that has prescriptions.
 */
public interface I_PrescriptionHolder {
    /**
     * @return the _prescriptions variable. Represents all the prescriptions the I_PrescriptionHolder has.
     */
    public ArrayList< I_Prescription > getPrescriptions();

    /**
     * @param prescriptions the new contents to set _prescriptions to.
     */
    public void setPrescriptions(ArrayList< I_Prescription > prescriptions);
}
