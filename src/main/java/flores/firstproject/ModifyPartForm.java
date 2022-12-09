package flores.firstproject;

import Model.Inventory;
import Model.InHouse;
import Model.Outsourced;
import Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** This class is the modify-part window controller*/
public class ModifyPartForm implements Initializable
{

    @FXML private Label modifyMachineOrCompanyLabel;
    @FXML private TextField modifyPartId;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartInv;
    @FXML private TextField modifyPartPrice;
    @FXML private TextField modifyPartMax;
    @FXML private TextField modifyPartMin;
    @FXML private TextField modifyMachineOrCompany;
    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourceRadio;
    @FXML private ToggleGroup modGroup;


    private Stage stage;
    private Object scene;
    public Part selectedPart;
    private int partId;



    /** This method changes the text of a label.
     This method changes the machine ID or company label text to Machine ID if the in house radio button is selected.
     @param actionEvent The in house radio button is selected
     */
    @FXML
    public void onModInHouse(ActionEvent actionEvent)
    {
        modifyMachineOrCompanyLabel.setText("Machine ID");
    }



    /** This method changes the text of a label.
     This method changes the machine ID or company label text to Company if outsourced radio button is selected.
     @param actionEvent The outsourced radio button is selected
     */
    @FXML
    public void onModOutsourced(ActionEvent actionEvent)
    {
        modifyMachineOrCompanyLabel.setText("Company Name");
    }



    /** This method will update the data of an existing part.
     This method will update existing data of a part, verify correct value ranges, verify data types, and save the new data.
     @param event When the save button gets clicked.
     @throws IOException
     */
    @FXML
    public void onModSaveButton(ActionEvent event) throws IOException {
        String name = modifyPartName.getText();
        int stock = Integer.parseInt(modifyPartInv.getText());
        double price = Double.parseDouble(modifyPartPrice.getText());
        int min = Integer.parseInt(modifyPartMin.getText());
        int max = Integer.parseInt(modifyPartMax.getText());

        if (max < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The Max value must be greater than the min value.");
            alert.showAndWait();
            return;
        }
        if (stock > max || stock < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The inventory value must be within the max and min values.");
            alert.showAndWait();
            return;
        }

        if (modGroup.getSelectedToggle().equals(inHouseRadio))
        {
            Part modifyPart = new InHouse(Integer.parseInt(modifyPartId.getText()),
                    modifyPartName.getText(),
                    Double.parseDouble(modifyPartPrice.getText()),
                    Integer.parseInt(modifyPartInv.getText()),
                    Integer.parseInt(modifyPartMax.getText()),
                    Integer.parseInt(modifyPartMin.getText()),
                    Integer.parseInt(modifyMachineOrCompany.getText()));

            Inventory.addPart(modifyPart);
                Inventory.deletePart(selectedPart);

        }
        if(modGroup.getSelectedToggle().equals(outsourceRadio))
        {
            Part modifyPart = new Outsourced(Integer.parseInt(modifyPartId.getText()),
                    modifyPartName.getText(),
                    Double.parseDouble(modifyPartPrice.getText()),
                    Integer.parseInt(modifyPartInv.getText()),
                    Integer.parseInt(modifyPartMax.getText()),
                    Integer.parseInt(modifyPartMin.getText()),
                    modifyMachineOrCompany.getText());

                Inventory.addPart(modifyPart);
                Inventory.deletePart(selectedPart);

        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/flores.firstproject/mainScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();

    }


    /**This method gets the selected part data.
     This method gets the selected part data and checks if the in-house radio button or outsourced radio button is selected.
    @param modPart The selected part to be modified.
     */
    public void getSelectedPart(Part modPart)
    {
        selectedPart = modPart;

        modifyPartId.setText(String.valueOf(selectedPart.getId()));
        modifyPartInv.setText(Integer.toString(selectedPart.getStock()));
        modifyPartName.setText(selectedPart.getName());
        modifyPartPrice.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMax.setText(String.valueOf(selectedPart.getMax()));
        modifyPartMin.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHouse)
        {
            inHouseRadio.setSelected(true);
            modifyMachineOrCompanyLabel.setText("Machine ID");
            modifyMachineOrCompany.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));

        }
        if (selectedPart instanceof Outsourced)
        {
            outsourceRadio.setSelected(true);
            modifyMachineOrCompanyLabel.setText("Company Name");
            modifyMachineOrCompany.setText(((Outsourced) selectedPart).getCompanyName());
        }


    }


    /**This cancels the modification of A part and returns to the Main Screen.*/
    public void onModCancelButton(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/flores.firstproject/mainScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
