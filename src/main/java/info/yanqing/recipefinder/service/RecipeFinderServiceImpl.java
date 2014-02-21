package info.yanqing.recipefinder.service;

import info.yanqing.recipefinder.model.FridgeItem;
import info.yanqing.recipefinder.model.MeasureUnit;
import info.yanqing.recipefinder.model.Recipe;
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

    public String getValidRecipe(List<Recipe> recipes, List<FridgeItem> ingredients)
	{
		List<FridgeItem> validIngredients = new ArrayList<FridgeItem>();

        validIngredients = getValidFridgeItems(ingredients);
        if (validIngredients.size() == 0)
			return "Order Takeout";
		Recipe canCookRecipe = null;
		Date recipeUseBy = null;
		for (Recipe recipe : recipes)
		{
			List<FridgeItem> neededIngredients = recipe.getIngredients();
			Date useBy = checkCanCookAndReturnUsedBy(neededIngredients, validIngredients);
			if (useBy != null)
			{
				if (recipeUseBy == null)
				{
					canCookRecipe = recipe;
					recipeUseBy = useBy;
				}
				else
				{
					if (useBy.getTime() < recipeUseBy.getTime())
						recipeUseBy = useBy;
					canCookRecipe = recipe;
				}
			}
		}
		if (canCookRecipe != null)
			return canCookRecipe.getName();
		return "Order Takeout";
	}

    public List<FridgeItem> getValidFridgeItems(List<FridgeItem> ingredients) {
        List<FridgeItem> validIngredients = new ArrayList<FridgeItem>();
        for (FridgeItem ingredient : ingredients)
        {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -1);
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
	
	public Date checkCanCookAndReturnUsedBy(List<FridgeItem> neededIngredients, List<FridgeItem> ingredients)
	{
		int i = 0;
		Date usedBy = null;
		for (FridgeItem ingredient : neededIngredients)
		{
			
			for (FridgeItem hasIngredient : ingredients)
			{
				if (ingredient.getItem().equals(hasIngredient.getItem())
						&& hasIngredient.getAmount() > ingredient.getAmount())
				{
					i++;
					if (usedBy == null || usedBy.getTime() > hasIngredient.getUseBy().getTime())
						usedBy = hasIngredient.getUseBy();
					break;
				}
			}
		}
		if (i == neededIngredients.size())
			return usedBy;
		return null;
	}
	
}
