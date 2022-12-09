package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This Class Contains All Product Details.*/
public class Product
{

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Product Constructor. */
    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**@param id The id to set. */
    public void setId(int id)
    {
        this.id = id;
    }

    /**@return the id .*/
    public int getId()
    {
        return id;
    }

    /** @param  name The name to set. */
    public void setName(String name)
    {
        this.name = name;
    }

    /** @return the name. */
    public String getName()
    {
        return name;
    }

    /** @param price The price to set. */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /** @return  the price. */
    public double getPrice()
    {
        return price;
    }

    /** @param stock The stock value to set. */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /** @return The stock value. */
    public int getStock()
    {
        return stock;
    }

    /** @param min The minimum value to set. */
    public void setMin(int min)
    {
        this.min = min;
    }

    /** @return The minimum value. */
    public int getMin()
    {
        return min;
    }

    /** @param max The maximum value to set. */
    public void setMax(int max)
    {
        this.max = max;
    }

    /** @return max Returns the maximum value. */
    public int getMax()
    {
        return max;
    }

    /** @param part The associated part.*/
    public void addAssociatedPart(Part part)
    {
        this.associatedParts.add(part);
    }


    /** @return Returns all associated parts.*/
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }




}
