package gr.meallab;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoriteSceneCreator {

    public static Scene createScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #2c3e50;"); 

        Label titleLabel = new Label("My Favorites");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox listContainer = new VBox(0);
        listContainer.setMaxWidth(500); 
        listContainer.setStyle("-fx-background-color: white; -fx-background-radius: 15;");

        Set<String> favoritesSet = MealStatusManager.getFavorites();
        List<String> favoritesList = new ArrayList<>(favoritesSet);

        if (favoritesList.isEmpty()) {
            Label empty = new Label("No favorites found.");
            empty.setPadding(new Insets(20));
            empty.setStyle("-fx-text-fill: #7f8c8d;");
            listContainer.getChildren().add(empty);
        } else {
            for (String mealId : favoritesList) {
                //Create row for each favorite meal
                listContainer.getChildren().add(createRecipeRow(mealId, listContainer, true));
            }
        }

        ScrollPane scrollPane = new ScrollPane(listContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxWidth(520); 
        scrollPane.setPrefHeight(450);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        Button backBtn = new Button("Back to Menu");
        backBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> App.changeScene(MainSceneCreator.createScene()));

        root.getChildren().addAll(titleLabel, scrollPane, backBtn);
        return new Scene(root, 900, 700);
    }

    public static HBox createRecipeRow(String mealId, VBox parent, boolean isFavorite) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setStyle("-fx-border-color: #eee; -fx-border-width: 0 0 1 0;");

        //Label με το ID ή μήνυμα φόρτωσης
        Label lbl = new Label("✔ Loading " + mealId + "...");
        lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");


        new Thread(() -> {
            try {
                MealDBClient client = new MealDBClient();
                Meal meal = client.getMealById(mealId);
                if (meal != null) {
                    // Επιστροφή στο UI Thread για την αλλαγή του κειμένου
                    Platform.runLater(() -> lbl.setText("✔  " + meal.getName()));
                }
            } catch (Exception e) {
                Platform.runLater(() -> lbl.setText("✔  ID: " + mealId));
            }
        }).start();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button deleteBtn = new Button("✖");
        deleteBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-cursor: hand;");
        
        deleteBtn.setOnAction(e -> {
            if (isFavorite) {
                MealStatusManager.removeFavorite(mealId);
            } else {
                MealStatusManager.removeCooked(mealId);
            }
            parent.getChildren().remove(row);
        });

        row.getChildren().addAll(lbl, spacer, deleteBtn);
        return row;
    }
}