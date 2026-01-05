package gr.meallab;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class MainSceneCreator {
    
     public static Scene createScene() {

        Button searchBtn = new Button("Search Recipes");
        Button randomBtn = new Button("Random Recipe");
        searchBtn.setOnAction(e ->
                App.changeScene(SearchSceneCreator.createScene())
        );
        
        /* 
        randomBtn.setOnAction(e ->
            App.changeScene(RandomMealSceneCreator.createScene())
        );

        */
       
        VBox root = new VBox(20, searchBtn, randomBtn);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, 600, 400);
    }
        
}
