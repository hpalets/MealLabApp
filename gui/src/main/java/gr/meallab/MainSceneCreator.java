package gr.meallab;

import gr.meallab.MealDBClient;
import gr.meallab.Meal;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainSceneCreator {

    public static Scene createScene() {

        // --- 1. ΡΥΘΜΙΣΗ ΦΟΝΤΟΥ (GIF) ---
        ImageView backgroundView = new ImageView();
        try {
            // Φόρτωση του GIF από τον φάκελο icons
            // Αν το αρχείο σου λέγεται αλλιώς, άλλαξε το όνομα εδώ
            Image bgImage = new Image(MainSceneCreator.class.getResourceAsStream("/icons/background.jpg"));
            backgroundView.setImage(bgImage);

            // Ρυθμίσεις για να πιάνει όλο το παράθυρο (800x600 είναι καλό μέγεθος)
            backgroundView.setFitWidth(800);
            backgroundView.setFitHeight(600);
            
            // Αν θες να παραμορφώνεται ελαφρώς για να γεμίζει όλο το κενό:
            backgroundView.setPreserveRatio(false); 
        } catch (Exception e) {
            System.out.println("Σφάλμα: Το GIF δεν βρέθηκε στο /icons/cooking_bg.gif");
        }

        // --- 2. ΤΙΤΛΟΣ ---
        Label titleLabel = new Label("Meal Lab App");
        // Λευκά γράμματα με μαύρη σκιά για να διαβάζονται παντού
        titleLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);");

        // --- 3. ΔΗΜΙΟΥΡΓΙΑ ΚΟΥΜΠΙΩΝ (Με στυλ) ---
        Button searchBtn = createStyledButton("🔍 Search Recipes");
        Button randomBtn = createStyledButton("🎲 Random Recipe");
        Button cookedBtn = createStyledButton("✔ Cooked Recipes");
        Button favoriteBtn = createStyledButton("⭐ Favorite Recipes");

        // --- 4. ΛΕΙΤΟΥΡΓΙΕΣ ΚΟΥΜΠΙΩΝ ---

        searchBtn.setOnAction(e -> 
            App.changeScene(SearchSceneCreator.createScene())
        );

        randomBtn.setOnAction(e -> {
            try {
                MealDBClient client = new MealDBClient();
                Meal randomMeal = client.getRandomMeal();
                if (randomMeal != null) {
                    App.changeScene(DetailsSceneCreator.createScene(randomMeal.getIdMeal()));
                }
            } catch (Exception ex) {
                AlertUtil.showAlert("Failed to load random recipe");
            }
        });

        favoriteBtn.setOnAction(e -> 
            App.changeScene(FavoriteSceneCreator.createScene())
        );

        cookedBtn.setOnAction(e -> {    
    App.changeScene(CookedSceneCreator.createScene()); 
});

        // --- 5. ΤΟΠΟΘΕΤΗΣΗ (LAYOUT) ---
        
        // VBox για τα κουμπιά και τον τίτλο
        VBox menuBox = new VBox(20, titleLabel, searchBtn, randomBtn, cookedBtn, favoriteBtn);
        menuBox.setAlignment(Pos.CENTER);

        // StackPane: Βάζει το menuBox ΠΑΝΩ από το backgroundView
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, menuBox);

        // --- ΤΟ ΜΥΣΤΙΚΟ ΓΙΑ FULL SCREEN ---
        // Συνδέουμε το μέγεθος της εικόνας με το μέγεθος του παραθύρου (root)
        backgroundView.fitWidthProperty().bind(root.widthProperty());
        backgroundView.fitHeightProperty().bind(root.heightProperty());
        
        // Σιγουρευόμαστε ότι δεν κρατάει αναλογίες για να γεμίζει παντού
        backgroundView.setPreserveRatio(false); 

        return new Scene(root, 800, 600);
    }

    // --- ΒΟΗΘΗΤΙΚΗ ΜΕΘΟΔΟΣ ΓΙΑ ΟΜΟΡΦΑ ΚΟΥΜΠΙΑ ---
    private static Button createStyledButton(String text) {
        Button btn = new Button(text);
        
        // CSS για ημιδιάφανο λευκό φόντο, στρογγυλεμένες γωνίες και bold γράμματα
        String defaultStyle = "-fx-background-color: rgba(255, 255, 255, 0.85); " +
                              "-fx-text-fill: #333; " +
                              "-fx-font-size: 16px; " +
                              "-fx-font-weight: bold; " +
                              "-fx-background-radius: 20; " +
                              "-fx-min-width: 220px; " +
                              "-fx-cursor: hand;";
        
        String hoverStyle = "-fx-background-color: white; " + // Όταν περνάει το ποντίκι γίνεται τελείως λευκό
                            "-fx-text-fill: #000; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-background-radius: 20; " +
                            "-fx-min-width: 220px; " +
                            "-fx-cursor: hand; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 0, 0);"; // Σκιά στο hover

        btn.setStyle(defaultStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(defaultStyle));

        return btn;
    }
}