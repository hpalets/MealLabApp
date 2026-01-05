package gr.meallab;

import gr.meallab.model.Meal;
import gr.meallab.model.MealDBClient;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class DetailsSceneCreator {

    public static Scene createScene(String mealId) {

        MealDBClient client = new MealDBClient();

        Label nameLabel = new Label();
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Εικόνα
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        // Υλικά
        TextArea ingredientsArea = new TextArea();
        ingredientsArea.setEditable(false);
        ingredientsArea.setPrefRowCount(6);

        // Οδηγίες
        TextArea instructionsArea = new TextArea();
        instructionsArea.setEditable(false);
        instructionsArea.setWrapText(true);

        try {
            Meal meal = client.getMealById(mealId);

            // Όνομα
            nameLabel.setText(meal.getName());

            // Εικόνα
            if (meal.getThumbnail() != null && !meal.getThumbnail().isEmpty()) {
                imageView.setImage(new Image(meal.getThumbnail(), true));
            }

            // Υλικά & δοσολογίες
            StringBuilder ingredientsText = new StringBuilder();

            for (int i = 1; i <= 20; i++) {
                String ingredient = meal.getClass()
                                        .getDeclaredField("strIngredient" + i)
                                        .get(meal).toString();
                String measure = meal.getClass()
                                        .getDeclaredField("strMeasure" + i)
                                        .get(meal).toString();

                if (ingredient != null && !ingredient.isBlank()) {
                    ingredientsText.append("- ")
                                .append(ingredient);

                    if (measure != null && !measure.isBlank()) {
                        ingredientsText.append(" (").append(measure).append(")");
                    }

                    ingredientsText.append("\n");
                }
            }


            ingredientsArea.setText(ingredientsText.toString());

            // Οδηγίες
            instructionsArea.setText(meal.getInstructions());

        } catch (Exception e) {
            instructionsArea.setText("Σφάλμα φόρτωσης λεπτομερειών.");
        }

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e ->
            App.changeScene(MainSceneCreator.createScene())
        );

        VBox content = new VBox(10,
                imageView,
                nameLabel,
                new Label("Υλικά & Δοσολογίες"),
                ingredientsArea,
                new Label("Οδηγίες"),
                instructionsArea,
                backBtn
        );

        content.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);

        return new Scene(scrollPane, 1200, 800);
    }
}
