package Model;

import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**This class contains all inventory details.*/
public class Inventory
{


    /** The Part ID Column. */
    public static TableColumn<Object, Object> partId;


    /** List of all the parts in the inventory. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();


    /** List of all the products in the inventory. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /** Variable declaration for Part ID. */
    private static int partID = 1;


    /** This method auto generates a part ID.
     This method automatically adds 1 to the variable partID.
     @return Returns the updated partID
     */
    public static int generateNewID()
    {
        return partID++;
    }


    /** Variable declaration for Product ID. */
    private static int productID = 1;


    /** This method auto generates a product ID.
     This method automatically adds 1 to the variable productID.
     @return Returns the updated productID
     */
    public static int generateProductID()
    {
        return productID++;
    }


    /** This method adds parts to inventory.
     This method gets the newPart data and saves it to all parts.
     @param newPart The new part that is added.*/
    public static void addPart(Part newPart)

    {
        allParts.add(newPart);
    }


    /** This method adds products to inventory.
     This method gets the newProduct data and saves it to all products list.
     @param newProduct The new product that is added.*/
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }


    /** This Method Searches Parts.
     This method searches for parts by ID.
     @param partId The part ID.
     @return The part that was found
     */
    public static Part lookupPart(int partId)
    {
        Part partIdFound = null;

        for (Part part : allParts)
        {
            if (part.getId() == partId)
            {
                partIdFound = part;
            }
        }
        return partIdFound;
    }


    /** This Method Searches Products.
     This method searches for the given input in the allProducts list.
     @param productId The given ID.
     @return  The product that was found in the all products list.
     */
    public static Product lookupProduct(int productId)
    {
        Product productIdFound = null;

        for (Product product : allProducts)
        {
            if (product.getId() == productId)
            {
                productIdFound = product;
            }
        }
        return productIdFound;
    }


    /** This method deletes a part from the inventory.
     This method deletes the selected part from the inventory.
     @param selectedPart The selected part.
     @return Returns true if a part is selected and false if no part is selected.
     */
    public static boolean deletePart(Part selectedPart)
    {
      if(allParts.contains(selectedPart))
      {
          allParts.remove(selectedPart);
          return true;
      }
      else
      {
          return false;
      }
    }


    /** This method deletes a product from the inventory.
     This method deletes the selected product from the inventory.
     @param selectedProduct The selected product.
     @return Returns true if a product is selected and false if no product is selected.
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        if(allProducts.contains(selectedProduct))
        {
            allProducts.remove(selectedProduct);
            return true;
        }
        else
        {
            return false;
        }
    }


    /**This method gets and returns a list.
     This method gets the all-parts list.
     @return Returns the all-parts list. */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }


    /**This method gets and returns a list.
     This method gets the all products list.
     @return Returns the all products list. */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }




}