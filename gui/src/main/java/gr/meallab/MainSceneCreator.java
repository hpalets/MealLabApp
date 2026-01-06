package gr.meallab;

import java.util.Random;

import gr.meallab.MealDBClient;
import gr.meallab.Meal;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class MainSceneCreator {
    
     public static Scene createScene() {
        MealDBClient ml = new MealDBClient();
        String ID;

        Button searchBtn = new Button("Search Recipes");
        Button randomBtn = new Button("Random Recipe");
        searchBtn.setOnAction(e ->
                App.changeScene(SearchSceneCreator.createScene())
        );
               
       randomBtn.setOnAction(e -> {
            try {

                //Define MealDBclient
                MealDBClient client = new MealDBClient();
                //Get Random Meal
                Meal randomMeal = client.getRandomMeal();

                if (randomMeal != null) {
                    App.changeScene(
                        //With the Meal Id set the Details Scene
                        DetailsSceneCreator.createScene(randomMeal.getIdMeal())
                    );
                }

            } catch (Exception ex) {
                AlertUtil.showError("Failed to load random recipe");
            }
        });

        
       
        VBox root = new VBox(20, searchBtn, randomBtn);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, 600, 400);
    }
        
}
