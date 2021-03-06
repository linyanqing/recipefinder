package info.yanqing.recipefinder.service;

import info.yanqing.recipefinder.model.FridgeItem;
import info.yanqing.recipefinder.model.IngredientItem;
import info.yanqing.recipefinder.model.MeasureUnit;
import info.yanqing.recipefinder.model.Recipe;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The service class for recipe finder
 */
public class RecipeFinderServiceImpl implements RecipeFinderService
{
	
	private final SimpleDateFormat DDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");

	public String findRecipe(List<FileItem> list) throws IOException, ParseException
	{
		String result = "";
		
		// initialize fridgeItemList and recipes
		List<FridgeItem> fridgeItemList = new ArrayList<FridgeItem>();
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		// parse files
		for (FileItem item : list)
		{
			String name = item.getFieldName();
			String content = parseFileItemToString(item).trim();

			// parse fridge items csv file
			if (name.equals("ingredient"))
			{
				fridgeItemList = getFridgeItemsFromString(content);
			}
			// parse recipe json file
			else if (name.equals("recipe"))
			{
                recipes = getRecipesFromString(content);
			}

			// find the recipe
			if (fridgeItemList != null && recipes != null)
			{
				result = getValidRecipe(recipes, fridgeItemList);
			}
			else
			{
				result = "Can't parse CSV & JSON, check your file format";
			}
			
		}
		return result;
	}


    public List<Recipe> getRecipesFromString(String content) throws IOException {

        content = content.replaceAll("\n", "");
        ObjectMapper mapper = new ObjectMapper();
        List<Recipe> recipes = mapper.readValue(content, new TypeReference<List<Recipe>>()
        {
        });
        return recipes;
    }

    public String getValidRecipe(List<Recipe> recipes, List<FridgeItem> fridgeItems)
	{
        String recommendedRecipe = "Order Takeout";
        List<Recipe> recommendedRecipeList = new ArrayList<Recipe>();
        List<Recipe> invalidRecipeList = new ArrayList<Recipe>();

        // get a list of not expired fridge items
		List<FridgeItem> fridgeItemList = getValidFridgeItems(fridgeItems);
        for (Recipe recipe : recipes){
            List<IngredientItem> ingredientItems = recipe.getIngredients();

            boolean isValidRecipe = true;

            Date closetUsedBy = null;
            long totalUsedByTime = 0l;
            for(IngredientItem ingredientItem : ingredientItems){
                // check if the ingredientItem exists in fridgeItemList
                boolean isFoundInFridge = false;
                for(FridgeItem fridgeItem : fridgeItemList){
                    String fridgeItemName = fridgeItem.getItem();
                    String ingredientItemName = ingredientItem.getItem();
                   if (fridgeItemName != null && fridgeItemName.equalsIgnoreCase(ingredientItemName)){
                       isFoundInFridge = true;
                       if(closetUsedBy == null){
                           closetUsedBy = fridgeItem.getUseBy();
                       }
                       else if(closetUsedBy.after(fridgeItem.getUseBy())){
                            closetUsedBy = fridgeItem.getUseBy();
                       }
                       totalUsedByTime += fridgeItem.getUseBy().getTime();

                       break;
                   }
                }
                // if one of ingredients could not be found in fridge, the recipe is invalid
                if(!isFoundInFridge){
                    isValidRecipe = false;
                    break;
                }
            }
            // add the valid recipe into recommendedRecipeList
            if(isValidRecipe){
                recipe.setTotalUsedByTime(totalUsedByTime);
                recipe.setClosestUsedBy(closetUsedBy);

                recommendedRecipeList.add(recipe);
            }
        }

        //sort the result and get the the recommended recipe.
        if(CollectionUtils.isNotEmpty(recommendedRecipeList)){
            Collections.sort(recommendedRecipeList);
            recommendedRecipe = recommendedRecipeList.get(0).getName();
        }
        return recommendedRecipe;
	}

    public List<FridgeItem> getValidFridgeItems(List<FridgeItem> ingredients) {
        List<FridgeItem> validIngredients = new ArrayList<FridgeItem>();
        for (FridgeItem ingredient : ingredients)
        {
            Calendar c = Calendar.getInstance();
            // expired item
            if (c.getTimeInMillis() < ingredient.getUseBy().getTime())
            {
                validIngredients.add(ingredient);
            }
        }
        return validIngredients;
    }

    public String parseFileItemToString(FileItem item) throws IOException
	{
		InputStream in = item.getInputStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer, "UTF-8");
		String content = writer.toString();
		return content;
	}
	
	public List<FridgeItem> getFridgeItemsFromString(String content) throws ParseException
	{
		List<FridgeItem> fridgeItemList = new ArrayList<FridgeItem>();
		String[] lines = content.split("\n");
		for (String line : lines)
		{
			String[] arr = line.split(",");
			String itemName = arr[0];
			Integer amount = Integer.valueOf(arr[1]);
			MeasureUnit unit = MeasureUnit.fromString(arr[2]);
			Date useBy = DDMMYYYY.parse(arr[3]);
			FridgeItem fridgeItem = new FridgeItem(itemName, amount, unit, useBy);
			fridgeItemList.add(fridgeItem);
		}
		return fridgeItemList;
	}
}
