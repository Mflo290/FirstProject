package Model;

import Model.Part;

/**This class inherits the part class.*/
public class InHouse extends Part
{
    /** Variable declaration for machine ID. */
    private int machineId;

    /** This method is the InHouse constructor. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
    super(id, name, price, stock, min, max);
    this.machineId = machineId;
    }

    /** This method sets the MachineID. */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    /** This method gets the MachineID.
     @return  Returns the machine ID
     */
    public int getMachineId()
    {
        return machineId;
    }


}
