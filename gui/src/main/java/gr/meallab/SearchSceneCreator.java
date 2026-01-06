package gr.meallab;

import gr.meallab.Meal;
import gr.meallab.SearchFunctions;
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

        //Text Field for search input
        TextField searchField = new TextField();
        searchField.setPromptText("GIVE INPUT (Ingredient or Name)");

        Button searchBtn = new Button("Search");
        Button backBtn = new Button("Back");

        backBtn.setOnAction(e ->
                App.changeScene(MainSceneCreator.createScene())
        );

        //List to show results
        ListView<Meal> listView = new ListView<>();

        //When we click the search button
        searchBtn.setOnAction(e -> {
                try {
                //Takes input from text field
                String query = searchField.getText();

                //Takes selected search type
                String selectedType = searchTypeChoice.getValue();

                if (query == null || query.isBlank()) {
                showAlert("Warning", "Fill the search field!!!");
                return;
                }

                List<Meal> results;
                //Set custom cell factory to display meal info
                listView.setCellFactory(lv -> new ListCell<>() {
                    @Override
                    protected void updateItem(Meal meal, boolean empty) {
                        super.updateItem(meal, empty);
                        if (empty || meal == null) {
                            setText(null);
                        } else {
                            setText("Name: " +meal.getName() + " ||  Category: " + meal.getCategory()
                                    + " || Area: " + meal.getArea());
                        }
                    }
                });

                //Search based on selected type
                if (selectedType.equals("1 - Αναζήτηση με υλικό")) {
                    results = SF.SearchUsingIngredient(query);
                } else {
                    results = SF.SearchUsingName(query);
                }

                listView.setItems(
                    FXCollections.observableArrayList(results)
                );

            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        //When we click a meal from the list
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

        HBox top = new HBox(20, searchTypeChoice, searchField, searchBtn, backBtn);
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
