package gr.meallab;

import gr.meallab.Recipe;
import java.util.ArrayList;
import java.util.List;

/*
    We need this class to store user data (favorites and cooked lists) in a structured way.
    This class will be serialized to JSON and deserialized back using the Jackson library.
    Because we have both favorites and cooked lists, we encapsulate them in this UserData class.

*/

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