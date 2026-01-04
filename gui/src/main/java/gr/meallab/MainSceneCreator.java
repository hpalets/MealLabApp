package gr.meallab.GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class MainSceneCreator {
    
     public static Scene createScene() {

        Button searchBtn = new Button("Αναζήτηση Συνταγών");
        searchBtn.setOnAction(e ->
                App.changeScene(SearchSceneCreator.createScene())
        );

        VBox root = new VBox(20, searchBtn);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, 600, 400);
    }
        
}
