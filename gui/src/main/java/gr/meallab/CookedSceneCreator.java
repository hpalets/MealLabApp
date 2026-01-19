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

public class CookedSceneCreator {

    public static Scene createScene() {
        // Κεντρικό Layout
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #2c3e50;"); // Ίδιο φόντο με τα Favorites

        // Title
        Label titleLabel = new Label("Cooking History");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Card Layout for recipe
        VBox listContainer = new VBox(0);
        listContainer.setMaxWidth(500); 
        listContainer.setStyle("-fx-background-color: white; -fx-background-radius: 15;");

        // IDs of cooked meals
        Set<String> cookedSet = MealStatusManager.getCooked();
        List<String> cookedList = new ArrayList<>(cookedSet);

        //We check if the list is empty
        if (cookedList.isEmpty()) {
            Label empty = new Label("You haven't cooked anything yet.");
            empty.setPadding(new Insets(20));
            empty.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
            listContainer.getChildren().add(empty);
        } else {
            for (String mealId : cookedList) {
                // Χρησιμοποιούμε τη μέθοδο δημιουργίας γραμμής (false γιατί είναι για Cooked)
                listContainer.getChildren().add(createRecipeRow(mealId, listContainer, false));
            }
        }

        // ScrollPane to be able to scroll
        ScrollPane scrollPane = new ScrollPane(listContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxWidth(520); 
        scrollPane.setPrefHeight(450);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        // Back Button
        Button backBtn = new Button("Back to Menu");
        backBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> App.changeScene(MainSceneCreator.createScene()));

        root.getChildren().addAll(titleLabel, scrollPane, backBtn);
        return new Scene(root, 900, 700);
    }

    // Method to create each recipe row
    private static HBox createRecipeRow(String mealId, VBox parent, boolean isFavorite) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setStyle("-fx-border-color: #eee; -fx-border-width: 0 0 1 0; -fx-cursor: hand;");

        // Label που θα δείχνει το όνομα
        Label lbl = new Label("✔ Loading " + mealId + "...");
        lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        // Thread για να φέρει το όνομα από το API for eace mealId
        new Thread(() -> {
            try {
                MealDBClient client = new MealDBClient();
                Meal meal = client.getMealById(mealId);
                if (meal != null) {
                    Platform.runLater(() -> lbl.setText("✔  " + meal.getName()));
                }
            } catch (Exception e) {
                Platform.runLater(() -> lbl.setText("✔  ID: " + mealId));
            }
        }).start();

        // When clicking the row, go to details scene
        row.setOnMouseClicked(e -> {
            if (e.getTarget() instanceof Label || e.getTarget() instanceof HBox) {
                App.changeScene(DetailsSceneCreator.createScene(mealId));
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // X delete button on row
        Button deleteBtn = new Button("✖");
        deleteBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 16px; -fx-cursor: hand;");
        
        //When clicking delete, remove from list and update storage
        deleteBtn.setOnAction(e -> {
            if (isFavorite) {
                MealStatusManager.removeFavorite(mealId);
            } else {
                MealStatusManager.removeCooked(mealId);
            }
            parent.getChildren().remove(row); // Σβήνει ακαριαία από την οθόνη
        });

        row.getChildren().addAll(lbl, spacer, deleteBtn);

        // Mouse Hover Effects
        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #eee; -fx-border-width: 0 0 1 0; -fx-cursor: hand;"));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: white; -fx-border-color: #eee; -fx-border-width: 0 0 1 0;"));

        return row;
    }
}