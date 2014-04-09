package car;

import java.io.Serializable;

public class Car implements Serializable{
    String model;
    int topSpeed;
    
    Car(String model, int topSpeed) {
        this.model = model;
        this.topSpeed = topSpeed;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getTopSpeed() {
        return topSpeed;
    }
    public void setTopSpeed(int topSpeed) {
        this.topSpeed = topSpeed;
    }
    public String toString() {
        return "[" + model + " - " + topSpeed + "km/h" + "]";
    }
} 