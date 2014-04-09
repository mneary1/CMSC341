package car;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Java ObjectOutputStream example
 * Writing Java objects to the file
 */
public class ObjectOutputStreamExample {
            
    public static void main(String[] args) {
        ObjectOutputStream os = null;
        try {
            FileOutputStream fs = new FileOutputStream("car.ser");
            os = new ObjectOutputStream(fs); 
            Car car1 = new Car("Porche 911 Carrera", 289);
            Car car2 = new Car("BMW Z4 M", 251);
            os.writeObject(car1);
            os.writeObject(car2);
            os.flush();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
          //close the stream to release system resources
            try {
                if (os != null) {
                    os.close();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
} 