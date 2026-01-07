package gr.meallab;

import gr.meallab.Meal;
import gr.meallab.MealDBClient;
import gr.meallab.App;
import gr.meallab.Recipe;
import gr.meallab.StorageManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class DetailsSceneCreator {

    public static Scene createScene(String mealId) {

        MealDBClient client = new MealDBClient();
        Meal meal = null;
        
        // Φόρτωση Meal αντικειμένου
        try {
            meal = client.getMealById(mealId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Αν δεν βρεθεί, επιστροφή (safety check)
        if (meal == null) return MainSceneCreator.createScene();


        // --- UI Setup: Φόντο & Layout ---
        
        // 1. Background Image (Αχνή)
        ImageView bgView = new ImageView();
        try {
            Image bgImg = new Image(meal.getThumbnail(), true);
            bgView.setImage(bgImg);
            bgView.setOpacity(0.20); 
            bgView.setPreserveRatio(false);
        } catch (Exception e) {}

        // 2. Labels for Favorites and Cooked
        Label favorStatusLabel = new Label();
        favorStatusLabel.setStyle("-fx-font-weight: bold; -fx-padding: 0 10 0 0;");
        if (!MealStatusManager.isFavorite(mealId)) {
            favorStatusLabel.setText("NotFavorite ✘");
            favorStatusLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-weight: bold;");
        } else {
            favorStatusLabel.setText("IsFavorite ✔");
            favorStatusLabel.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
        }

        Label cookedStatusLabel = new Label();
        cookedStatusLabel.setStyle("-fx-font-weight: bold;");
        if (!MealStatusManager.isCooked(mealId)) {
            cookedStatusLabel.setText("NotCooked ✘");
            cookedStatusLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-weight: bold;");
        } else {
            cookedStatusLabel.setText("IsCooked ✔");
            cookedStatusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        }

        // 3. Name Label (Τίτλος)
        Label nameLabel = new Label();
        nameLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);

        // 4. Main Image (Κεντρική εικόνα)
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");

        // 5. Ingredients & Instructions Containers
        Label ingredientsLabel = new Label();
        ingredientsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e;");
        ingredientsLabel.setWrapText(true);

        Text instructionsTextObj = new Text();
        instructionsTextObj.setStyle("-fx-font-size: 16px; -fx-fill: #2c3e50;");
        TextFlow instructionsFlow = new TextFlow(instructionsTextObj);
        instructionsFlow.setTextAlignment(TextAlignment.JUSTIFY);

        try {
            nameLabel.setText(meal.getName());

            if (meal.getThumbnail() != null && !meal.getThumbnail().isEmpty()) {
                imageView.setImage(new Image(meal.getThumbnail(), true));
            }

            // Loop for ingredients and measures
            StringBuilder ingredientsText = new StringBuilder();
            for (int i = 1; i <= 20; i++) {
                String ingredient = meal.getIngredient(i);
                String measure = meal.getMeasure(i);

                if (ingredient != null && !ingredient.isBlank()) {
                    ingredientsText.append("• ").append(ingredient);
                    if (measure != null && !measure.isBlank()) {
                        ingredientsText.append(" (").append(measure).append(")");
                    }
                    ingredientsText.append("\n");
                }
            }
            ingredientsLabel.setText(ingredientsText.toString());
            instructionsTextObj.setText(meal.getInstructions());

        } catch (Exception e) {
            e.printStackTrace();
            instructionsTextObj.setText("Σφάλμα: " + e.getMessage());
        }

        // --- ΚΟΥΜΠΙΑ ---

        Button backBtn = createStyledButton("⬅ Back", "#7f8c8d");
        backBtn.setOnAction(e -> App.changeScene(MainSceneCreator.createScene()));

        
        /*
        
                In this section, we toggle the favorite and cooked status of the meal.
                    If the meal is not currently a favorite/cooked, we add it to the corresponding list,
                    update the status label, and show a success alert. If it is already a favorite,
                    we remove it from the  list, update the status label, and show a success alert.
                    Finally, we save the updated lists to storage.
                
                We use 2 different Lists because we have 2 different objects:
                    - MealStatusManager uses meal IDs (String) for quick status checks.
                    - App.favoritesList and App.cookedList use Recipe objects for detailed info and display. (for Json)

        */ 


        // Favorite Logic
        Button FavoriteBtn = createStyledButton("⭐ Favorite", "#e74c3c");
        FavoriteBtn.setOnAction(e -> {
            try {
                if (!MealStatusManager.isFavorite(mealId)) {
                    favorStatusLabel.setText("IsFavorite ✔");
                    favorStatusLabel.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
                    MealStatusManager.addFavorite(mealId);
                    AlertUtil.showSuccess("Meal added to favorites!");
                    boolean exists = App.favoritesList.stream().anyMatch(r -> r.getIdMeal().equals(mealId));
                    if (!exists) App.favoritesList.add(new Recipe(nameLabel.getText(), mealId));
                } else {
                    MealStatusManager.removeFavorite(mealId);
                    favorStatusLabel.setText("NotFavorite ✘");
                    favorStatusLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-weight: bold;");
                    AlertUtil.showSuccess("Meal removed from favorites!");
                    App.favoritesList.removeIf(r -> r.getIdMeal().equals(mealId));
                }
                //Here we overwrite the storage with updated lists
                StorageManager.save(App.favoritesList, App.cookedList);
            } catch (Exception ex) {
                AlertUtil.showAlert("Error: " + ex.getMessage());
            }
        });

        // Cooked Logic
        Button cookedBtn = createStyledButton("✔ Cooked", "#27ae60");
        cookedBtn.setOnAction(e -> {
            try {
                if (MealStatusManager.isCooked(mealId)) {
                    MealStatusManager.removeCooked(mealId);
                    cookedStatusLabel.setText("NotCooked ✘");
                    cookedStatusLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-weight: bold;");
                    AlertUtil.showSuccess("Meal removed from cooked!");
                    App.cookedList.removeIf(r -> r.getIdMeal().equals(mealId));
                } else {
                    MealStatusManager.addCooked(mealId);
                    cookedStatusLabel.setText("IsCooked ✔");
                    cookedStatusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    AlertUtil.showSuccess("Meal added to cooked!");
                    boolean exists = App.cookedList.stream().anyMatch(r -> r.getIdMeal().equals(mealId));
                    if (!exists) App.cookedList.add(new Recipe(nameLabel.getText(), mealId));
                }
                //Here we overwrite the storage with updated lists
                StorageManager.save(App.favoritesList, App.cookedList);
            } catch (Exception ex) {
                AlertUtil.showAlert("Error: " + ex.getMessage());
            }         
        });

        VBox statusBox = new VBox(5, favorStatusLabel, cookedStatusLabel);
        statusBox.setAlignment(Pos.CENTER);
        
        HBox buttonBox = new HBox(15, backBtn, FavoriteBtn, cookedBtn);
        buttonBox.setAlignment(Pos.CENTER);

        Label ingTitle = new Label("Ingredients");
        ingTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #e67e22; -fx-padding: 10 0 5 0;");
        
        Label instrTitle = new Label("Instructions");
        instrTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #e67e22; -fx-padding: 10 0 5 0;");

        // Η Λευκή Κάρτα
        VBox contentCard = new VBox(15,
                nameLabel,
                imageView,
                buttonBox,
                statusBox,
                ingTitle,
                ingredientsLabel,
                instrTitle,
                instructionsFlow
        );
        contentCard.setAlignment(Pos.TOP_CENTER);
        contentCard.setPadding(new Insets(30));
        contentCard.setMaxWidth(900);
        contentCard.setStyle("-fx-background-color: rgba(255, 255, 255, 0.92); -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 15, 0, 0, 0);");

        // ΣΩΣΤΟ ΚΕΝΤΡΑΡΙΣΜΑ: Βάζουμε την κάρτα μέσα σε StackPane
        StackPane centerWrapper = new StackPane(contentCard);
        centerWrapper.setPadding(new Insets(20));
        centerWrapper.setStyle("-fx-background-color: transparent;");

        // ScrollPane 
        ScrollPane scrollPane = new ScrollPane(centerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // StackPane Root
        StackPane root = new StackPane();
        root.getChildren().addAll(bgView, scrollPane);

        // Responsive Background
        bgView.fitWidthProperty().bind(root.widthProperty());
        bgView.fitHeightProperty().bind(root.heightProperty());

        return new Scene(root, 1000, 700);
    } 

    // Helper για κουμπιά 
    private static Button createStyledButton(String text, String colorHex) {
        Button btn = new Button(text);
        
        String defaultStyle = "-fx-background-color: " + colorHex + ";" +
                              "-fx-text-fill: white;" +
                              "-fx-font-weight: bold;" +
                              "-fx-font-size: 14px;" +
                              "-fx-background-radius: 30;" +
                              "-fx-padding: 10 20;" +
                              "-fx-cursor: hand;";

        String hoverStyle = "-fx-background-color: white;" +
                            "-fx-text-fill: " + colorHex + ";" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14px;" +
                            "-fx-background-radius: 30;" +
                            "-fx-padding: 10 20;" +
                            "-fx-border-color: " + colorHex + ";" +
                            "-fx-border-radius: 30;" +
                            "-fx-border-width: 2;";

        btn.setStyle(defaultStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(defaultStyle));
        
        return btn;
    } 

} 