package distributed.systems.lab2.utils.searchables.components;

public class Vehicle {
    private String name;
    private String manufacturer;
    private int passengers;
    private String vehicle_class;
    private boolean mode;

    public boolean getMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getVehicleClass() {
        return vehicle_class;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicle_class = vehicleClass;
    }

    @Override
    public String toString() {
        if (mode) {
            return "";
        }
        return name + " which was produced by " + manufacturer +
                ". It is a " + vehicle_class + ", capable of carrying " + passengers + " passengers";
    }
}
