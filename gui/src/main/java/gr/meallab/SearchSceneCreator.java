package gr.meallab.GUI;

import gr.meallab.model.Meal;
import gr.meallab.model.MealSummary;
import gr.meallab.model.SearchFunctions;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class SearchSceneCreator {

    

    public static Scene createScene() {
        SearchFunctions SF = new SearchFunctions();
        
        //Choice Box for search type
        ChoiceBox<String> searchTypeChoice = new ChoiceBox<>();
        searchTypeChoice.getItems().addAll(
                "1 - Αναζήτηση με υλικό",
                "2 - Αναζήτηση με όνομα"
        );
        searchTypeChoice.setValue("1 - Αναζήτηση με υλικό");

        TextField searchField = new TextField();
        searchField.setPromptText("GIVE INPUT (Ingredient or Name)");

        Button searchBtn = new Button("Search");

        ListView<Meal> listView = new ListView<>();

        searchBtn.setOnAction(e -> {
                try {
                String query = searchField.getText();
                String selectedType = searchTypeChoice.getValue();

                if (query == null || query.isBlank()) {
                showAlert("Warning", "Fill the search field!!!");
                return;
                }

                List<Meal> results;

                if (selectedType.equals("Αναζήτηση με υλικό")) {
                    results = SF.SearchUsingIngredient(query);
                } else {
                    results = SF.SearchUsingName(query);
                }

                listView.setItems(FXCollections.observableArrayList(results));

            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

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

        HBox top = new HBox(10, searchTypeChoice, searchField, searchBtn);
        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(listView);

        return new Scene(root, 800, 500);
    }

    private static void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
