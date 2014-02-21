package info.yanqing.recipefinder.model;

import java.util.List;

public class Recipe
{
	private String name;
	private List<IngredientItem> ingredients;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

    public List<IngredientItem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientItem> ingredients) {
        this.ingredients = ingredients;
    }
}
