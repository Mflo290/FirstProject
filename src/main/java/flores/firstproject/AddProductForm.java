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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import static Model.Inventory.partId;


/** This class is the add product window controller. */
public class AddProductForm implements Initializable {

    public Part addPartToProduct;



    @FXML
    private TextField addProductName;

    @FXML
    private TextField addProductInv;

    @FXML
    private TextField addProductPrice;

    @FXML
    private TextField addProductMax;

    @FXML
    private TextField addProductMin;




    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, Integer> partName;

    @FXML
    private TableColumn<Part, Integer> partInvLevel;

    @FXML
    private TableColumn<Part, Integer> pricePerPart;

    @FXML
    private TableView<Part> addProductAssociatedPart;

    @FXML
    private TableColumn<Part, Integer> addAssociatedPartId;

    @FXML
    private TableColumn<Part, String> addAssociatedPartName;

    @FXML
    private TableColumn<Part, Integer> addAssociatedPartInv;

    @FXML
    private TableColumn<Part, Double> addAssociatedPartPrice;

    @FXML
    public TextField partSearchTextField;


    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    Parent scene;
    Stage stage;
    private EventObject event;


    /** This method copies a part from a table into a different table.
     This method copies a part from the parts table and into the associated-parts table of the product.
     @param event When the add button gets clicked
     */
    public void addProductAddButton(ActionEvent event)
    {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        addProductAssociatedPart.setItems(associatedParts);
    }


    /** This method removes a part from a table.
     This method removes a part from the associated-parts table of a product.
     @param event When the remove button gets clicked
     @throws IOException
     */
    public void addProductRemoveButton(ActionEvent event) throws IOException
    {
        Part selectedAssociatedPart = addProductAssociatedPart.getSelectionModel().getSelectedItem();

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


    /** This method cancels and returns to the main window.
     This method cancels the addition of a product and returns you to the main window.
     @param event When the cancel button gets clicked
     @throws IOException
     */
    public void addProductCancelButton(ActionEvent event) throws IOException
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

    /**This method saves a new product and returns to the main window.
     This method saves/adds a new product into the inventory by checking value ranges and data type inputs. Then it returns you to the main window.
     @param event When the save button gets clicked
     */
    public void addProductSaveButton(ActionEvent event)
    {

        try {

            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInv.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int min = Integer.parseInt(addProductMin.getText());
            int max = Integer.parseInt(addProductMax.getText());

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
            newProduct.setName(addProductName.getText());
            newProduct.setPrice(Double.parseDouble(addProductPrice.getText()));
            newProduct.setStock(Integer.parseInt(addProductInv.getText()));
            newProduct.setMax(Integer.parseInt(addProductMax.getText()));
            newProduct.setMin(Integer.parseInt(addProductMin.getText()));

            Inventory.addProduct(newProduct);

            for(Part part : associatedParts)
            {
                newProduct.addAssociatedPart(part);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/flores.firstproject/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        } catch (Exception e) {


            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Alert");
            alert.setContentText("Please enter correct values");
            alert.showAndWait();
        }

    }

    /** This method allows you to search for a part.
     This method will search and highlight a part in the parts inventory by inputting a part id in the text-field.
     @param actionEvent When the part ID is specified in the search text-field
     */
    public void onSearchPart(ActionEvent actionEvent)
    {

        String s = partSearchTextField.getText();
        try {

            int partId = Integer.parseInt(s);

            Part foundP = Inventory.lookupPart(partId);

            if (foundP != null) {
                partsTable.getSelectionModel().select(foundP);
            }
        }
        catch (NumberFormatException e){
            ObservableList<Part> parts = searchParts(s);

            partsTable.setItems(parts);
            partSearchTextField.setText("");
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

        String s = partSearchTextField.getText();

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerPart.setCellValueFactory(new PropertyValueFactory<>("price"));
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));

        addProductAssociatedPart.setItems(associatedParts);
        addAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addAssociatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addAssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        addAssociatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));




    }

}
