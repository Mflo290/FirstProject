package flores.firstproject;

import Model.*;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;


/** This class is the main screen controller.*/
public class MainController implements Initializable
{



    @FXML
    public TableView<Part> partsTable;

    @FXML
    public TableColumn partId;

    @FXML
    public TableColumn partName;

    @FXML
    public TableColumn partInvLevel;

    @FXML
    public TableColumn pricePerPart;




    @FXML
    public TableView<Product> productsTable;

    @FXML
    public TableColumn productId;

    @FXML
    public TableColumn productName;

    @FXML
    public TableColumn productInvLevel;

    @FXML
    public TableColumn pricePerProduct;


    @FXML
    public Button addPartButton;

    @FXML
    public Button addProductButton;

    @FXML
    public TextField partSearchTextField;

    @FXML
    public TextField productSearchTextField;

    private ObservableList<Part> allParts = FXCollections.observableArrayList(); //Parts List
    private ObservableList<Product> allProducts = FXCollections.observableArrayList(); //Products List
    private EventObject event;

    Stage stage;




     /** This method takes you to the add part window/form.
     This method takes you to the add part window/form which allows a new part to be added into the inventory.
     @param actionEvent When the add part button gets clicked
     @throws IOException
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException
    {
        System.out.println("Add Part Button Clicked.");

        Parent root = FXMLLoader.load(getClass().getResource("/flores.firstproject/addPartForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }



     /** This method takes you to the add product window/form.
     This method takes you to the add product window/form which allows a new part to be added into the inventory.
     @param actionEvent When the add product button gets clicked
     @throws IOException
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException
    {
        System.out.println("Add Product Button Clicked.");

        Parent root = FXMLLoader.load(getClass().getResource("/flores.firstproject/addProductForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();

    }



    /** This method allows you to search for a part.
     This method will search and highlight a part in the parts inventory by inputting a part id in the text-field.
     @param actionEvent When a part ID is specified in the search text-field
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
    private ObservableList<Part>  searchParts(String partialPart)
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



    /** This method allows you to search for a product.
     This method will search and highlight a product in the products inventory by inputting a product id in the text-field.
     @param actionEvent When a product ID is specified in the search text-field
     */
    public void onSearchProduct(ActionEvent actionEvent)
    {
        String s = productSearchTextField.getText();
        try {
            int productId = Integer.parseInt(s);
            Product foundProduct = Inventory.lookupProduct(productId);

            if(foundProduct != null)
            {
                productsTable.getSelectionModel().select(foundProduct);
            }
        }
        catch (NumberFormatException e)
        {
            ObservableList<Product> products= searchProducts(s);

            productsTable.setItems(products);
            productSearchTextField.setText("");
        }

    }


    /** This method allows you to search for a product.
     This method will search for a product in the inventory by inputting either full product name or partial name in the text-field.
     @param partialProduct The partial or full product name typed into the search text-field
     @return Returns the found part or parts
     */
    private ObservableList<Product> searchProducts(String partialProduct)
    {
        ObservableList<Product> productName = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        String s = productSearchTextField.getText();

        for (Product p : allProducts)
        {
            if (String.valueOf(p.getId()).contains(s) || p.getName().contains(partialProduct))
            {
                productName.add(p);
            }
        }
        if (productName.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Product not found");
            alert.showAndWait();
        }
        return productName;

    }



    /** This method takes you to the modify-part window/form.
     This method takes you to the modify-part window/form which allows a part to be modified in the inventory.
     @param actionEvent When the modify-part button gets clicked
     @throws IOException
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException
    {
        System.out.println("Modify Part Button Clicked.");

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A part must be selected.");
            alert.showAndWait();

            return;
        }

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/flores.firstproject/modifyPartForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Modify Part Form");
        stage.setScene(scene);
        ModifyPartForm controller = loader.getController();
        controller.getSelectedPart(partsTable.getSelectionModel().getSelectedItem());
        stage.show();

    }



    /** This method deletes a part.
     This method will delete the part that is selected from the inventory
     @param actionEvent When the delete-part button gets clicked
     */
    @FXML
    public void onDeletePart(ActionEvent actionEvent)
    {
        System.out.println("Delete Part Button Clicked");
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A part must be selected!)");
            alert.showAndWait();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to delete the selected part?");
        alert.showAndWait();

        Inventory.deletePart(selectedPart);

    }



    /** This method takes you to the modify-product window/form.
     This method takes you to the modify-product window/form which allows a product to be modified in the inventory.
     @param actionEvent When the modify-product button gets clicked
     @throws IOException
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException
    {
        System.out.println("Modify Product Button Clicked.");
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A Product must be selected!");
            alert.showAndWait();
            return;
        }
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/flores.firstproject/modifyProductForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Modify Product Form");
        stage.setScene(scene);
        modifyProductForm controller = loader.getController();
        controller.getSelectedProduct(productsTable.getSelectionModel().getSelectedItem());
        stage.show();



    }
    /** This method deletes a product.
     This method will delete the product that is selected from the inventory
     @param actionEvent When the delete-product button gets clicked
     */
    public void onDeleteProduct(ActionEvent actionEvent)
    {
      System.out.println("Delete Product Button Clicked");
      Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

      if(selectedProduct == null)
      {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setContentText("A Product Must Be Selected.");
          alert.showAndWait();
          return;
      }
      ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
      if(associatedParts.size() >= 1)
      {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("ERROR");
          alert.setContentText("This Product has an Associated Part. Unable to Delete!");
          alert.showAndWait();
          return;
      }
      else
      {
          Inventory.deleteProduct(selectedProduct);

          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Alert");
          alert.setContentText("Do you want to delete the selected Product?");
          alert.showAndWait();
          return;
      }



    }
    /** This method will exit/close the application.
     @param actionEvent When the exit button gets clicked
     */
    public void onExit(ActionEvent actionEvent)
    {
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");


        partsTable.setItems(Inventory.getAllParts());


        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerPart.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerProduct.setCellValueFactory(new PropertyValueFactory<>("price"));


    }



}