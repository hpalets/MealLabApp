package gr.meallab;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CookedSceneCreator {

    public static Scene createScene() {

        ListView<Meal> listView = new ListView<>();
        List<Meal> cookedMeals = new ArrayList<>();

        MealDBClient client = new MealDBClient();
        Set<String> cookedIds = MealStatusManager.getCooked();

        // Μετατροπή ID -> Meal
        for (String id : cookedIds) {
            try {
                Meal meal = client.getMealById(id);
                if (meal != null) {
                    cookedMeals.add(meal);
                }
            } catch (Exception e) {
                // αγνοούμε αποτυχημένο load
            }
        }

        listView.setItems(FXCollections.observableArrayList(cookedMeals));

        // Εμφάνιση ονόματος
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Meal meal, boolean empty) {
                super.updateItem(meal, empty);
                setText(empty || meal == null ? null : "✔ " + meal.getName());
            }
        });

        // Click → Details
        listView.setOnMouseClicked(e -> {
            Meal selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                App.changeScene(
                        DetailsSceneCreator.createScene(selected.getIdMeal())
                );
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e ->
                App.changeScene(MainSceneCreator.createScene())
        );

        BorderPane root = new BorderPane();
        root.setCenter(listView);
        root.setBottom(backBtn);

        return new Scene(root, 800, 600);
    }
}
