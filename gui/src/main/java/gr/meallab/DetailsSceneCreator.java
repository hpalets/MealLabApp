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

        //Create marks for Favorites and Cooked
        TextField FavorFieldText = new TextField();
        if (!MealStatusManager.isFavorite(mealId)) {
            FavorFieldText.setText("NotFavorite ✘");
        }else{
            FavorFieldText.setText("IsFavorite ✔");
        }

        TextField CookedFieldText = new TextField();
        if (!MealStatusManager.isCooked(mealId)) {
            CookedFieldText.setText("NotCooked ✘");
        }else{
            CookedFieldText.setText("IsCooked ✔");
        }

        Label nameLabel = new Label();
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        TextArea ingredientsArea = new TextArea();
        ingredientsArea.setEditable(false);
        ingredientsArea.setPrefRowCount(10); 

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

            //Τμήμα για Υλικά & δοσολογίες
            StringBuilder ingredientsText = new StringBuilder();

            // Getting ingredients and measures
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

        //Adding to Favorites 
        Button FavoriteBtn = new Button("⭐ Favorite");
        FavoriteBtn.setOnAction(e -> {
        try {
            if (!MealStatusManager.isFavorite(mealId)) {
                FavorFieldText.setText("IsFavorite ✔");
                MealStatusManager.addFavorite(mealId);
                AlertUtil.showSuccess("Meal added to favorites!");
            }else{
                MealStatusManager.removeFavorite(mealId);
                FavorFieldText.setText("NotFavorite ✘");
                AlertUtil.showSuccess("Meal removed from favorites!");
            }
            
        } catch (Exception ex) {
            AlertUtil.showAlert("Failed to add meal to favorites: " + ex.getMessage());
        }
        });

        //Adding to Cooked
        Button cookedBtn = new Button("✔ Cooked");
        cookedBtn.setOnAction(e -> {
            try {
                if(MealStatusManager.isCooked(mealId)){
                    MealStatusManager.removeCooked(mealId);
                    CookedFieldText.setText("NotCooked ✘");
                    AlertUtil.showSuccess("Meal removed from cooked!");
                    return;
                }else{
                    MealStatusManager.addCooked(mealId);
                    CookedFieldText.setText("IsCooked ✔");
                    AlertUtil.showSuccess("Meal added to cooked!");
                }
            } catch (Exception ex) {
                AlertUtil.showAlert("Failed to add meal to cooked: " + ex.getMessage());
            }         
        });

        HBox Choices = new HBox(20, backBtn,FavoriteBtn, FavorFieldText, cookedBtn, CookedFieldText);

        VBox content = new VBox(10,
                imageView,
                nameLabel,
                new Label("Υλικά & Δοσολογίες"),
                ingredientsArea,
                new Label("Οδηγίες"),
                instructionsArea,
                Choices
        );

        content.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);

        return new Scene(scrollPane, 1600, 950);
    }
}