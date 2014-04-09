package car;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * ObjectInputStream example
 * Reading Java objects from file
 */
public class ObjectInputStreamExample {

    public static void main(String[] args) {
        FileInputStream fs = null;
        ObjectInputStream os = null;
        
        try {
            fs = new FileInputStream("car.ser");
            os = new ObjectInputStream(fs); 
            Car car1 = (Car) os.readObject();
            Car car2 = (Car) os.readObject();
            System.out.println(car1 + "\n" + car2);
        
        } catch (ClassNotFoundException e) {
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