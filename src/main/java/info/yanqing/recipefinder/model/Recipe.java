package info.yanqing.recipefinder.model;

import java.util.List;

public class Recipe
{
	private String name;
	private List<FridgeItem> ingredients;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public List<FridgeItem> getIngredients()
	{
		return ingredients;
	}
	
	public void setIngredients(List<FridgeItem> ingredients)
	{
		this.ingredients = ingredients;
	}
}
