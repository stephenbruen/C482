package model;

/** This creates the Inhouse class*/
public class InHouse extends Part{
    private int machineID;

    /** This is the constructor. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id, name, price, stock, min, max );
        this.machineID=machineID;
    }

    /** This is the MachineID getter.
     * @return  the machine ID
     */
    public int getMachineID() {
        return machineID;
    }
    /** This is the MachineID setter*/
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
