package gr.meallab;

import java.util.ArrayList;
import java.util.List;

/*

    We need this class to store user data (favorites and cooked lists) in a structured way.

    This class will be serialized to JSON and deserialized back using the Jackson library.

    Because we have both favorites and cooked lists, we encapsulate them in this UserData class.



*/

public class UserData {
    // Αλλάζουμε το Recipe σε String για να συμβαδίζει με τον MealStatusManager
    public List<String> favorites = new ArrayList<>();
    public List<String> cooked = new ArrayList<>();

    public UserData() {}

    public UserData(List<String> favorites, List<String> cooked) {
        this.favorites = favorites;
        this.cooked = cooked;
    }
}