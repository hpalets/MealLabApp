package gr.meallab;

import gr.meallab.Meal;
import gr.meallab.SearchFunctions;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class DetailsSceneCreator {

    public static Scene createScene(String mealId) {
        

        TextArea detailsArea = new TextArea();
        detailsArea.setEditable(false);

        try {
            Meal meal = SearchFunctions.getMealDetails(mealId);

            detailsArea.setText(
                    "Όνομα: " + meal.getStrMeal() + "\n\n" +
                    "Οδηγίες:\n" + meal.getStrInstructions()
            );

        } catch (Exception e) {
            detailsArea.setText("Σφάλμα φόρτωσης συνταγής");
        }

        Button backBtn = new Button("Πίσω");
        backBtn.setOnAction(e ->
                App.changeScene(MainSceneCreator.createScene())
        );

        BorderPane root = new BorderPane();
        root.setCenter(detailsArea);
        root.setBottom(backBtn);

        return new Scene(root, 800, 500);
    }
}
