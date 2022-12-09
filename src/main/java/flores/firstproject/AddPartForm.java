package flores.firstproject;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import static Model.Inventory.getAllParts;


/** This class is the add part window controller. */
public class AddPartForm implements Initializable
{

    public ToggleGroup tgroup;
    Parent scene;
    Stage stage;


    @FXML
    private RadioButton addPartInHouseForm;

    @FXML
    private RadioButton addPartOutsourcedForm;

    @FXML
    private Label machineOrCompanyLabel;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartPrice;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMin;

    @FXML
    private TextField addPartMachineId;



    /** This method changes the text of a label.
     This method changes the machine ID or company label text to Machine ID if the in house radio button is selected.
     @param actionEvent The in house radio button is selected
     */
    @FXML
    public void onInHouseSelected(ActionEvent actionEvent)
    {
        machineOrCompanyLabel.setText("Machine ID");
    }



    /** This method changes the text of a label.
     This method changes the machine ID or company label text to Company if outsourced radio button is selected.
     @param actionEvent The outsourced radio button is selected
     */
    @FXML
    public void onOutsourcedSelected(ActionEvent actionEvent)
    {
        machineOrCompanyLabel.setText("Company Name");
    }



    /** This method cancels and exits out of a window.
     This method cancels/exits out of the add part window and returns you to the main screen.
     @param event The cancel button in the add part form is clicked
     @throws IOException
     */
    @FXML
    public void onCancelPartButton(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/flores.firstproject/mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /** This method saves and adds a new part into the inventory.
     This method saves/adds a new part into the inventory and returns you to the main screen.
     @param event The save button in the add part form is clicked
     */
    @FXML
    public void onSavePartButton(ActionEvent event)
    {
        try
        {
            String partName = addPartName.getText();
            int partInv = Integer.parseInt(addPartInv.getText());
            double partPrice = Double.parseDouble(addPartPrice.getText());
            int partMax = Integer.parseInt(addPartMax.getText());
            int partMin = Integer.parseInt(addPartMin.getText());

            if (partMax < partMin)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Minimum must have a value less than the Max value.");
                alert.showAndWait();
                return;
            }

            if (partInv > partMax || partInv < partMin)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory value must be in between the min and max values.");
                alert.showAndWait();
                return;
            }

            Part newPart;
            if(addPartInHouseForm.isSelected())
            {
                newPart = new InHouse(Inventory.generateNewID(), "", 0.0, 0, 0, 0, 0);
                newPart.setName(addPartName.getText());
                newPart.setPrice(Double.parseDouble(addPartPrice.getText()));
                newPart.setStock(Integer.parseInt(addPartInv.getText()));
                newPart.setMax(Integer.parseInt(addPartMax.getText()));
                newPart.setMin(Integer.parseInt(addPartMin.getText()));
                ((InHouse) newPart).setMachineId(Integer.parseInt(addPartMachineId.getText()));

                Inventory.addPart(newPart);
                System.out.println("In-House");

            }

            else if (addPartOutsourcedForm.isSelected())
            {
                newPart = new Outsourced(Inventory.generateNewID(), "", 0.0, 0, 0, 0, "");
                newPart.setName(addPartName.getText());
                newPart.setPrice(Double.parseDouble(addPartPrice.getText()));
                newPart.setStock(Integer.parseInt(addPartInv.getText()));
                newPart.setMax(Integer.parseInt(addPartMax.getText()));
                newPart.setMin(Integer.parseInt(addPartMin.getText()));
                ((Outsourced) newPart).setCompanyName(addPartMachineId.getText());

                Inventory.addPart(newPart);
                System.out.println("Outsourced");

            }

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/flores.firstproject/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Alert");
            alert.setContentText("Please enter correct values");
            alert.showAndWait();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {


    }


}
