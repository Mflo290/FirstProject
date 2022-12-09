package Model;

/**This class contains outsourced parts and inherits the parts class.*/
public class Outsourced extends Part
{

    /**Declaring The Company Name As A String.*/
    private String companyName;

    /** This method is the outsourced constructor. */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method sets a company Name. */
    public void setCompanyName(String text)
    {
        this.companyName = companyName;
    }

    /** This method gets a company name.
     * @return Returns the company name. */
    public String getCompanyName()
    {
        return companyName;
    }


}
