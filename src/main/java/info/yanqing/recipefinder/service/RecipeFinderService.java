package info.yanqing.recipefinder.service;

import info.yanqing.recipefinder.model.FridgeItem;
import info.yanqing.recipefinder.model.Recipe;
import org.apache.commons.fileupload.FileItem;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface RecipeFinderService
{
    /**
     * Fin the recipe from the input files. One is fridge item list file; Another is recipe list file
     * @param list
     * @return recipe. the recommended recipe based on items in fridge
     * @throws IOException
     * @throws ParseException
     */
	String findRecipe(List<FileItem> list) throws IOException, ParseException;

    /**
     * get a list of recipes from input file content
     * @param content a string containing a list of recipes
     * @return recipeList
     * @throws IOException
     */
    List<Recipe> getRecipesFromString(String content) throws IOException;

    /**
     * get a list of fridge items from input file content
     * @param content a string containing a list of fridge items
     * @return fridgeItemList
     * @throws ParseException
     */
    List<FridgeItem> getFridgeItemsFromString(String content) throws ParseException;

    List<FridgeItem> getValidFridgeItems(List<FridgeItem> ingredients);
}
