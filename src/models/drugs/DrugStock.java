package models.drugs;

import models.I_Builder;
import models.repositories.I_RepositoryItem;

import java.util.ArrayList;

public class DrugStock
        implements I_RepositoryItem {
    /**
     * Builder design pattern.
     */
    public static class Builder
            implements I_Builder< DrugStock > {
        public String name;
        public String description;
        public ArrayList< String > sideEffects;
        public int stock;

        /**
         * Default builder constructor.
         *
         * @param name the DrugStock object's Drug object's name.
         * @param description the DrugStock object's Drug object's description.
         */
        public Builder (String name, String description){
            this.name = name;
            this.description = description;
            this.sideEffects = new ArrayList<>();
            this.stock = 0;
        }

        /**
         * @param sideEffects the DrugStock object's Drug object's side effects.
         * @return the Builder object.
         */
        public Builder sideEffects(ArrayList< String > sideEffects){
            this.sideEffects = sideEffects;
            return this;
        }

        /**
         * @param stock the DrugStock object's stock.
         * @return the Builder object.
         */
        public Builder stock(int stock){
            this.stock = stock;
            return this;
        }

        /**
         * @return the object based on the builder.
         */
        @Override
        public DrugStock build() {
            return new DrugStock(this);
        }
    }

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

    /**
     * Creates a DrugStock object.
     *
     * @param builder the DrugStock builder object.
     */
    public DrugStock(Builder builder) {
        _drug = new Drug(builder.name, builder.description, builder.sideEffects);
        _stock = builder.stock;
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
