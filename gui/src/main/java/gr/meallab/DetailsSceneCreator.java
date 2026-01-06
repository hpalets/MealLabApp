package gr.meallab;

import gr.meallab.Meal;
import gr.meallab.MealDBClient;

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

        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        TextArea ingredientsArea = new TextArea();
        ingredientsArea.setEditable(false);
        ingredientsArea.setPrefRowCount(10); // Αυξήσαμε το ύψος για να φαίνονται περισσότερα

        TextArea instructionsArea = new TextArea();
        instructionsArea.setEditable(false);
        instructionsArea.setWrapText(true);
        instructionsArea.setPrefRowCount(15);

        try {
            Meal meal = client.getMealById(mealId);

            nameLabel.setText(meal.getName());

            if (meal.getThumbnail() != null && !meal.getThumbnail().isEmpty()) {
                imageView.setImage(new Image(meal.getThumbnail(), true));
            }

            // Διορθωμένο τμήμα για Υλικά & δοσολογίες
            StringBuilder ingredientsText = new StringBuilder();

            // Διαβάζουμε μέχρι το 11 που έχουμε ορίσει στο Meal.java
            for (int i = 1; i <= 11; i++) {
                String ingredient = meal.getIngredient(i);
                String measure = meal.getMeasure(i);

                if (ingredient != null && !ingredient.isBlank()) {
                    ingredientsText.append("- ").append(ingredient);
                    if (measure != null && !measure.isBlank()) {
                        ingredientsText.append(" (").append(measure).append(")");
                    }
                    ingredientsText.append("\n");
                }
            }

            ingredientsArea.setText(ingredientsText.toString());
            instructionsArea.setText(meal.getInstructions());

        } catch (Exception e) {
            e.printStackTrace(); // Εκτύπωση για να βλέπουμε το σφάλμα στην κονσόλα
            instructionsArea.setText("Σφάλμα φόρτωσης λεπτομερειών: " + e.getMessage());
        }

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> App.changeScene(MainSceneCreator.createScene()));

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

        return new Scene(scrollPane, 1600, 950);
    }
}