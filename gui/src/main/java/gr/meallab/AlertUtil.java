package gr.meallab;

import javafx.scene.control.Alert;

/*
    Utility class for showing alert dialogs in the application.
    This class provides static methods to show error and success messages to the user.
*/

public class AlertUtil {

    // Show an error alert with the given message
    public static void showAlert( String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Show a success alert with the given message
    public static void showSuccess( String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
