package gr.meallab;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class App extends Application {
    
        private static Stage primaryStage;
        static Scene mainScene, searchScene, mealDetailScene;

        @Override
        public void start(Stage stage) {
            try{
                this.primaryStage = stage;
                stage.setTitle("Meal Lab App");


                MainSceneCreator mainSceneCreator=  new MainSceneCreator();
                mainScene = mainSceneCreator.createScene();
            

                primaryStage.setScene(mainScene);
                primaryStage.show();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        //Method for changing scenes
        public static void changeScene(Scene scene) {
            primaryStage.setScene(scene);
        }
        

        //Main method
        public static void main(String[] args) {
            launch(args);
        }

    } 
