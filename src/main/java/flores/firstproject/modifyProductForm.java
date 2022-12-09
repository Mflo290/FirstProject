package flores.firstproject;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;


/** This class is the modify-product window controller. */
public class modifyProductForm implements Initializable {

        private Product selectedProduct;

        private ObservableList<Part> associatedParts = observableArrayList();

        @FXML
        private TextField modifyProductId;

        @FXML
        private TextField modifyProductName;

        @FXML
        private TextField modifyProductInv;

        @FXML
        private TextField modifyProductPrice;

        @FXML
        private TextField modifyProductMax;

        @FXML
        private TextField modifyProductMin;

        @FXML
        private TableView modifyProductPart;

        @FXML
        private TableColumn modPartID;

        @FXML
        private TableColumn modPartName;

        @FXML
        private TableColumn modInvLevel;

        @FXML
        private TableColumn modPartPrice;

        @FXML
        private TableView modifyProductAssociatedPart;

        @FXML
        private TableColumn modAssociateID;

        @FXML
        private TableColumn modAssociateName;

        @FXML
        private TableColumn modAssociateInv;

        @FXML
        private TableColumn modAssociatePrice;

        @FXML
        private TextField modPartSearchTextfield;

    private Stage stage;
    private Object scene;



    /** This method allows you to search for a part.
     This method will search and highlight a part in the parts inventory by inputting a part id in the text-field.
     @param actionEvent When the part ID is specified in the search text-field
     */
    public void onModPartSearch(ActionEvent actionEvent)
    {
        String s = modPartSearchTextfield.getText();
        try {

            int partId = Integer.parseInt(s);

            Part foundP = Inventory.lookupPart(partId);

            if (foundP != null) {
                modifyProductPart.getSelectionModel().select(foundP);
            }
        }
        catch (NumberFormatException e){
            ObservableList<Part> parts = searchParts(s);

            modifyProductPart.setItems(parts);
            modPartSearchTextfield.setText("");
        }

    }


    /** This method allows you to search for a part.
     This method will search for a part in the inventory by inputting either full part name or partial name in the text-field.
     @param partialPart The partial or full name typed into the search text-field
     @return Returns the found part or parts
     */
    private ObservableList<Part> searchParts(String partialPart)
    {
        ObservableList<Part> nameParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        String s = modPartSearchTextfield.getText();

        for (Part p : allParts)
        {
            if(String.valueOf(p.getId()).contains(s) || p.getName().contains(partialPart))
            {
                nameParts.add(p);
            }
        }
        if(nameParts.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }

        return nameParts;
    }


    /** This method removes a part from a table.
     This method removes a part from the associated-parts table of a product.
     @param event When the remove button gets clicked
     @throws IOException
     */
    public void modProductRemoveButton(ActionEvent event) throws IOException
    {
        Part selectedAssociatedPart = (Part) modifyProductAssociatedPart.getSelectionModel().getSelectedItem();

        if(selectedAssociatedPart == null)
        {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select an associated part.");
            alert.showAndWait();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to remove the associated part?");
        alert.showAndWait();

        associatedParts.remove(selectedAssociatedPart);

    }

    /** LOGICAL ERROR. If an incorrect value gets input into the min or max fields, such as "five" the logical error will occur and an exception will be thrown. */

    /**This method updates a product and returns to the main window.
     This method updates a product's data in the inventory by checking value ranges and data type inputs. Then it returns you to the main window.
     @param event When the save button gets clicked
     */
    @FXML
    public void onModifySaveButton(ActionEvent event) throws IOException
    {
        try {

            String name = modifyProductName.getText();
            int stock = Integer.parseInt(modifyProductInv.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int min = Integer.parseInt(modifyProductMin.getText());
            int max = Integer.parseInt(modifyProductMax.getText());

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Minimum must have a value less than maximum");
                alert.showAndWait();
                return;
            }

            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inv value must be between min and max");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(Inventory.generateProductID(), " ", 0.0, 0, 0, 0);
            newProduct.setName(modifyProductName.getText());
            newProduct.setPrice(Double.parseDouble(modifyProductPrice.getText()));
            newProduct.setStock(Integer.parseInt(modifyProductInv.getText()));
            newProduct.setMax(Integer.parseInt(modifyProductMax.getText()));
            newProduct.setMin(Integer.parseInt(modifyProductMin.getText()));

            Inventory.addProduct(newProduct);
            Inventory.deleteProduct(selectedProduct);

            for(Part part : associatedParts)
            {
                newProduct.addAssociatedPart(part);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/flores.firstproject/mainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (Exception e) {


            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Alert");
            alert.setContentText("Please enter correct values");
            alert.showAndWait();
        }

    }

    /**This method gets the selected product data.
     This method gets the selected product data and checks if the in-house radio button or outsourced radio button is selected.
     @param product The selected product to be modified.
     */
    public void getSelectedProduct(Product product)
    {
        selectedProduct = product;

        modifyProductId.setText(Integer.toString(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductPrice.setText(Double.toString(product.getPrice()));
        modifyProductInv.setText(Integer.toString(product.getStock()));
        modifyProductMax.setText(Integer.toString(product.getMax()));
        modifyProductMin.setText(Integer.toString(product.getMin()));
        associatedParts = product.getAllAssociatedParts();
        modifyProductAssociatedPart.setItems(associatedParts);


    }
    /** This method cancels and returns to the main window.
     This method cancels the modification product and returns you to the main window.
     @param event When the cancel button gets clicked
     @throws IOException
     */
    public void onModifyCancelButton(javafx.event.ActionEvent event) throws IOException
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

    /** This method copies a part from a table into a different table.
     This method copies a part from the parts table and into the associated-parts table of the product.
     @param event When the add button gets clicked
     */
    public void onModProdAddButton(ActionEvent event)
    {
        Part part = (Part) modifyProductPart.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        modifyProductAssociatedPart.setItems(associatedParts);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyProductPart.setItems(Inventory.getAllParts());
        modPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modPartID.setCellValueFactory(new PropertyValueFactory<>("id"));


        modifyProductAssociatedPart.setItems(Inventory.getAllParts());
        modAssociateName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modAssociateInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modAssociatePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modAssociateID.setCellValueFactory(new PropertyValueFactory<>("id"));

    }




}
