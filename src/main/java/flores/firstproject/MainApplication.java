package flores.firstproject;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.fxml.FXMLLoader.load;


/** This class creates an app for an inventory management system. */
public class MainApplication extends Application
{

    /** FUTURE ENHANCEMENT. An enhancement that could be made to make the app more user-friendly and interactive would be to include animated pictures and the ability to
     drag items, like parts, into products. */
    @Override
    public void start(Stage mainStage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/flores.firstproject/mainScreen.fxml"));
        mainStage.setTitle("Inventory System");
        mainStage.setScene(new Scene(root, 1000, 600));
        mainStage.show();
    }

    /** JavaDocs directory: /FirstProject/Javadocs/flores.firstproject/index.html */

    /** This is the main method that gets called when you run your java program. */
    public static void main(String[] args)
    {


        /** This is test-data. */
        Part engine = new InHouse(Inventory.generateNewID(), "inline6", 1000.00, 4, 1, 10, 5);
        Inventory.addPart(engine);

        Part engine2 = new Outsourced(Inventory.generateNewID(), "rotary", 800, 3, 1, 10, "mazda");
        Inventory.addPart(engine2);

        Product lexus = new Product(Inventory.generateProductID(), "rcf", 50000, 4, 1, 20);
        Inventory.addProduct(lexus);

        InHouse engine3 = new InHouse(Inventory.generateNewID(), "boxer", 2000, 5, 1, 10, 1234);
        Inventory.addPart(engine3);

        Outsourced wheel = new Outsourced(Inventory.generateNewID(), "rpf1", 1200, 12, 4, 20, "enkei");
        Inventory.addPart(wheel);

        Product car = new Product(Inventory.generateProductID(), "frs", 28000, 4, 1, 14);
        Inventory.addProduct(car);

        launch();
    }



}