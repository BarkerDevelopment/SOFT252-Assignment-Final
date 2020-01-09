package project.views.secretary;

import project.controllers.primary.SecretaryController;
import project.controllers.primary.ViewController;
import project.controllers.repository.DrugRepositoryController;
import project.exceptions.ObjectNotFoundException;
import project.exceptions.StockLevelException;
import project.models.drugs.DrugStock;
import project.models.drugs.I_Treatment;
import project.views.I_Form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Bound class to ViewDrugs view.
 */
public class ViewDrugs implements I_Form {
    private ViewController _viewController;
    private SecretaryController _controller;
    private DrugRepositoryController _repositoryController;
    private ArrayList< DrugStock > _drugs;

    private JPanel _panelMain;
    private JTable _tableDrugs;
    private JSpinner _spinnerNewStock;
    private JButton _buttonOrder;
    private JButton _buttonReturn;

    /**
     * Default constructor.
     *
     * @param viewController the view controller.
     * @param controller the view's controller.
     * @param repositoryController the drug repository.
     */
    public ViewDrugs(ViewController viewController, SecretaryController controller, DrugRepositoryController repositoryController) {
        _viewController = viewController;
        _controller = controller;
        _repositoryController = repositoryController;

        _spinnerNewStock.setModel(new SpinnerNumberModel(1, 0, 500, 1));

        _buttonOrder.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = _tableDrugs.getSelectedRow();

                if(index > -1){
                    I_Treatment drug = _drugs.get(index).getDrug();

                    try {
                        int newStock = (Integer) _spinnerNewStock.getValue();
                        _repositoryController.updateStock(drug, newStock);
                        update();
                        _viewController.createPopUp(String.format("New %s stock ordered: %d units", drug.getName(), newStock));

                    } catch (StockLevelException | ObjectNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        _buttonReturn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                _controller.back();
            }
        });
    }

    /**
     * @return the main panel of the form.
     */
    @Override
    public JPanel getMainPanel() {
        this.update();
        return _panelMain;
    }

    /**
     * Update the contents of the form.
     */
    @Override
    public void update() {
        _drugs = _repositoryController.get();
        _tableDrugs.setModel(getTableDrugModel(_drugs));
    }

    /**
     * @param drugStocks the drugs to display.
     * @return the TableDrugModel object.
     */
    private DefaultTableModel getTableDrugModel(ArrayList< DrugStock > drugStocks) {
        String[] columns = {"Drug Name", "Stock", "In Stock"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (DrugStock drugStock : drugStocks){
            String[] row = new String[columns.length];

            row[0] = drugStock.getDrug().getName();
            row[1] = String.valueOf(drugStock.getStock());
            row[2] = drugStock.getStock() > 0 ? "Yes" : "No";

            model.addRow(row);
        }

        return model;
    }
}
