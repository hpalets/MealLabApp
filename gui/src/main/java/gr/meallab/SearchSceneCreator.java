package gr.meallab;

import gr.meallab.Meal;
import gr.meallab.SearchFunctions;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class SearchSceneCreator {

    public static Scene createScene() {
        SearchFunctions SF = new SearchFunctions();

        // --- 1. SETUP ΦΟΝΤΟΥ ---
        ImageView backgroundView = new ImageView();
        try {
            Image bgImage = new Image(SearchSceneCreator.class.getResourceAsStream("/icons/background.jpg"));
            backgroundView.setImage(bgImage);
            backgroundView.setPreserveRatio(false);
        } catch (Exception e) {
            System.out.println("Background image not found");
        }

        // --- 2. UI ELEMENTS (Google Style) ---

        // Τίτλος (Meal Lab App)
        Label titleLabel = new Label("Meal Lab App");
        titleLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);");

        // Choice Box for search type (Διατηρούμε το δικό σου)
        ChoiceBox<String> searchTypeChoice = new ChoiceBox<>();
        searchTypeChoice.getItems().addAll(
                "1 - Αναζήτηση με υλικό",
                "2 - Αναζήτηση με όνομα"
        );
        searchTypeChoice.setValue("1 - Αναζήτηση με υλικό");
        searchTypeChoice.setStyle("-fx-background-radius: 20; -fx-font-size: 14px;");

        // Text Field for search input (Google Style Pill Shape)
        TextField searchField = new TextField();
        searchField.setPrefWidth(350);
        searchField.setMaxWidth(350);
        searchField.setPrefHeight(40);
        searchField.setPromptText("GIVE INPUT (Ingredient or Name)");
        searchField.setStyle("-fx-background-radius: 30; -fx-padding: 0 20; -fx-font-size: 16px;");

        Button searchBtn = createStyledButton("🔍 Search", "#3498db");
        Button backBtn = createStyledButton("⬅ Back", "#7f8c8d");

        // List to show results (Διατηρούμε τη δική σου ListView)
        ListView<Meal> listView = new ListView<>();
        listView.setMaxWidth(800);
        // Ημιδιάφανη λίστα για να φαίνεται ωραία στο background
        listView.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 10; -fx-control-inner-background: rgba(255, 255, 255, 0.9);");

        // --- 3. LOGIC (Η δική σου λογική encapsulated) ---
        
        // Φτιάχνουμε μια Runnable για να την καλούμε και στο Κουμπί και στο Enter
        Runnable executeSearch = () -> {
            try {
                // Takes input from text field
                String query = searchField.getText();

                // Takes selected search type
                String selectedType = searchTypeChoice.getValue();

                if (query == null || query.isBlank()) {
                    showAlert("Warning", "Fill the search field!!!");
                    return;
                }

                List<Meal> results;
                
                // Set custom cell factory για να γεμίσουμε τα Category και Area
listView.setCellFactory(lv -> new ListCell<Meal>() {
    @Override
    protected void updateItem(Meal meal, boolean empty) {
        super.updateItem(meal, empty);

        if (empty || meal == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Δημιουργούμε το container για το κείμενο
            VBox vbox = new VBox(5);
            Label nameLbl = new Label("Name: " + meal.getName());
            nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #2c3e50;");
            
            Label infoLbl = new Label();
            infoLbl.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13px;");

            // Έλεγχος αν λείπουν τα στοιχεία (για αναζήτηση με υλικό)
            if (meal.getCategory() == null || meal.getArea() == null) {
                infoLbl.setText("Category: Loading... | Area: Loading...");
                
                new Thread(() -> {
                    try {
                        Meal fullMeal = new MealDBClient().getMealById(meal.getIdMeal());
                        if (fullMeal != null) {
                            javafx.application.Platform.runLater(() -> {
                                infoLbl.setText("Category: " + fullMeal.getCategory() + " | Area: " + fullMeal.getArea());
                            });
                        }
                    } catch (Exception e) {
                        javafx.application.Platform.runLater(() -> infoLbl.setText("Details unavailable"));
                    }
                }).start();
            } else {
                infoLbl.setText("Category: " + meal.getCategory() + " | Area: " + meal.getArea());
            }

            vbox.getChildren().addAll(nameLbl, infoLbl);
            
            // ΣΗΜΑΝΤΙΚΟ: Σβήνουμε το παλιό setText και βάζουμε το Graphic
            setText(null); 
            setGraphic(vbox);
            
            // Styling για τη γραμμή
            setStyle("-fx-background-color: transparent; -fx-padding: 10;");
        }
    }
});

                // Search based on selected type (Ο ΚΩΔΙΚΑΣ ΣΟΥ ΑΚΡΙΒΩΣ)
                if (selectedType.equals("1 - Αναζήτηση με υλικό")) {
                    results = SF.SearchUsingIngredient(query);
                } else {
                    results = SF.SearchUsingName(query);
                }
                
                // Προστασία αν γυρίσει null η λίστα (για να μην σκάσει)
                if (results != null) {
                    listView.setItems(FXCollections.observableArrayList(results));
                } else {
                    listView.getItems().clear(); // Καθαρισμός αν δεν βρέθηκε τίποτα
                    showAlert("Info", "No recipes found.");
                }

            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        };

        // --- 4. EVENTS ---

        // When we click the search button
        searchBtn.setOnAction(e -> executeSearch.run());

        // Key Press Enter (ΤΟ ΝΕΟ FEATURE)
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                executeSearch.run();
            }
        });

        // Back Button
        backBtn.setOnAction(e -> App.changeScene(MainSceneCreator.createScene()));

        // List Click Event (Ο ΚΩΔΙΚΑΣ ΣΟΥ ΑΚΡΙΒΩΣ)
        listView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Meal selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    App.changeScene(
                            DetailsSceneCreator.createScene(selected.getIdMeal())
                    );
                }
            }
        });

        // --- 5. LAYOUT ---

        // HBox για τα κουμπιά και το choice box
        HBox controlsBox = new HBox(10, searchTypeChoice, searchBtn, backBtn);
        controlsBox.setAlignment(Pos.CENTER);

        // VBox για να τα βάλουμε όλα στη μέση (Google Style)
        VBox centerLayout = new VBox(20);
        centerLayout.getChildren().addAll(titleLabel, searchField, controlsBox, listView);
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setPadding(new Insets(30));
        
        // Η λίστα να πιάνει τον υπόλοιπο χώρο
        VBox.setVgrow(listView, Priority.ALWAYS);

        // StackPane Root (Background + Content)
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, centerLayout);

        // Responsive Background
        backgroundView.fitWidthProperty().bind(root.widthProperty());
        backgroundView.fitHeightProperty().bind(root.heightProperty());

        return new Scene(root, 1000, 700);
    }

    private static void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Άλλαξα σε Information γιατί το Error είναι τρομακτικό
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Helper για ομοιομορφία κουμπιών (ίδιο με τα άλλα Scenes)
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