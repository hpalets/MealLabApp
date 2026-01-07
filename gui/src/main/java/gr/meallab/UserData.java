package gr.meallab;

import gr.meallab.Recipe;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    // Οι λίστες που θέλουμε να θυμάται το app
    public List<Recipe> favorites = new ArrayList<>();
    public List<Recipe> cooked = new ArrayList<>();

    // Απαραίτητο για τη βιβλιοθήκη Jackson
    public UserData() {}

    public UserData(List<Recipe> favorites, List<Recipe> cooked) {
        this.favorites = favorites;
        this.cooked = cooked;
    }
}