package gr.meallab.model;

import java.util.List;

public class SearchFunctions {            
    
    // 1. Create Object of MealDb API
    MealDBClient client = new MealDBClient();

    //Fuction for searching using name
    public List<Meal> SearchUsingName(String name) {
        try {
            System.out.println("Searching Recice with name: " + name +"\n");
            //We add the Recipe we found in the Meal list
            List<Meal> MealsName = client.searchMealsByName(name);
            
            //Meal existance check
            if (MealsName != null) {
                System.out.println("Found " + MealsName.size() + " recipe(s)!\n");     

                //Printing names
                for (Meal m : MealsName) {
                    System.out.println("--- Recipe: " + m.getName()+ " ---");  
                    System.out.println("Category: " + m.getCategory());
                    System.out.println("ID: " + m.getIdMeal());
                    System.out.println("--- END ---\n");
                }

                
            } else {
                System.out.println("No recipes found.");
                MealsName = null;
            }
            return MealsName;
        }catch (Exception e) {
            //Error handling
            e.printStackTrace();
            return null;
        }
        
    }

    //Search using category
    public void SearchUsingCategory(String category) {
        try {
            
            System.out.println("Searching the category: "+category);
            List<Meal> MealsCategory = client.searchMealsByCategory(category);
            
            if (MealsCategory != null) {
                System.out.println("Found " + MealsCategory.size() + " recipes!");
                System.out.println("Take an idea: " + MealsCategory.get(0).getName());
            }
        } catch (Exception e) {
            //Error handling
            e.printStackTrace();
        }
        
        System.out.println("--- END PROCESS ---");
    }

   //Search using Ingredient
    public List<Meal> SearchUsingIngredient(String ingredient) {
        try {
            System.out.println("Searching recipes with ingredient: " + ingredient +"\n");
            //We add the Recipe we found in the Meal list
            List<Meal> MealsIngredient = client.searchMealsByIngredient(ingredient);
            
            //Meal existance check
            if (MealsIngredient != null) {
                System.out.println("Found " + MealsIngredient.size() + " recipe(s)!\n");     

                //Printing names
                for (Meal m : MealsIngredient) {
                    System.out.println("--- Recipe: " + m.getName()+ " ---");  
                    System.out.println("Category: " + m.getCategory());
                    System.out.println("ID: " + m.getIdMeal());
                    System.out.println("--- END ---\n");
                }
                return MealsIngredient;
            } else {
                System.out.println("No recipes found.");
                
            }

        }catch (Exception e) {
            //Error handling
            e.printStackTrace();
        }

        return null;
    }
}
