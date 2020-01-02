package models.drugs;

import models.I_Builder;
import models.I_Unique;
import models.repositories.I_RepositoryItem;

import java.util.ArrayList;

public class DrugStock
        implements I_RepositoryItem {

    /**
     * A class that encapsulates a drug treatment.
     */
    @SuppressWarnings("InnerClassMayBeStatic")
    private class Drug
            implements I_Treatment {

        private String _name;
        private String _description;
        private ArrayList<String> _sideEffects;

        /**
         * Alternative drug constructor. Only vital attributes are assigned.
         *
         * @param name the name of the drug.
         * @param description the description of the drug.
         */
        public Drug(String name, String description) {
            _name = name;
            _description = description;
            _sideEffects = new ArrayList<>();
        }

        /**
         * Default drug constructor. Assigns all attributes to passed parameters.
         *
         * @param name the name of the drug.
         * @param description the description of the drug.
         * @param sideEffects the side effects of the drug.
         */
        public Drug(String name, String description, ArrayList< String > sideEffects) {
            _name = name;
            _description = description;
            _sideEffects = sideEffects;
        }

        /**
         * @return the _name variable.
         */
        @Override
        public String getName() {
            return _name;
        }

        /**
         * @return the _description variable.
         */
        @Override
        public String getDescription() {
            return _description;
        }

        /**
         * @return the _sideEffects variable. This represents the known side effects of the drugs.
         */
        @Override
        public ArrayList< String > getSideEffects() {
            return _sideEffects;
        }

        /**
         * @param name the new contents to set _name to.
         */
        public void setName(String name){
            _name = name;
        }

        /**
         * @param description the new contents to set _description to.
         */
        public void setDescription(String description) {
            _description = description;
        }

        /**
         * @param sideEffects the new contents to set _sideEffects to.
         */
        public void setSideEffects(ArrayList< String > sideEffects) {
            _sideEffects = sideEffects;
        }
    }

    private final Drug _drug;
    private int _stock;

    private ArrayList< I_Observer< Integer > > _observers;

    /**
     * Default constructor.
     *
     * @param name the drug's name.
     * @param description the drug's description.
     * @param sideEffects the drug's sideEffects.
     * @param stock the drug's stock.
     */
    public DrugStock(String name, String description, ArrayList< String > sideEffects, int stock) {
        _drug = new Drug(name, description, sideEffects);
        _stock = stock;

        _observers = new ArrayList<>();
    }

    /**
     * Creates a DrugStock item from a request.
     *
     * @param builder the DrugStock builder object.
     */
    public DrugStock(Builder builder) {
        _drug = new Drug(builder.name, builder.description, builder.sideEffects);
        _stock = builder.stock;

        _observers = new ArrayList<>();
    }

    /**
     * @return T the objects unique string.
     */
    @Override
    public I_Treatment getUnique() {
        return _drug;
    }

    /**
     * @return the _drug variable.
     */
    public I_Treatment getDrug() {
        return _drug;
    }

    /**
     * @return the _stock variable.
     */
    public int getStock() {
        return _stock;
    }

    /**
     * @param stock the new stock level.
     */
    public void setStock(int stock) {
        _stock = stock;
    }
}
