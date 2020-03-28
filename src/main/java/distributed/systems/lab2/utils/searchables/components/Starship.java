package distributed.systems.lab2.utils.searchables.components;

public class Starship {
    private String name;
    private String manufacturer;
    private int crew;
    private int passengers;
    private String starship_class;
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

    public int getCrew() {
        return crew;
    }

    public void setCrew(int crew) {
        this.crew = crew;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getStarshipClass() {
        return starship_class;
    }

    public void setStarshipClass(String starshipClass) {
        this.starship_class = starshipClass;
    }

    @Override
    public String toString() {
        if (mode) {
            return "";
        }
        String nounForm = " passengers";
        if (passengers + crew == 1)
            nounForm = " passenger";
        return name + " which was produced by " + manufacturer +
                ". It is a " + starship_class + ", capable of carrying " + (passengers+crew) + nounForm;
    }
}
