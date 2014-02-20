package info.yanqing.recipefinder.model;

import java.util.Date;

public class FoodItem
{
	private String item;
	private Integer amount;
	private MeasureUnit unit;
	
	public FoodItem()
	{
		
	}
	
	public FoodItem(String item, Integer amount, MeasureUnit unit, Date useBy)
	{
		this.item = item;
		this.amount = amount;
		this.unit = unit;
	}
	
	public String getItem()
	{
		return item;
	}
	
	public void setItem(String item)
	{
		this.item = item;
	}
	
	public Integer getAmount()
	{
		return amount;
	}
	
	public void setAmount(Integer amount)
	{
		this.amount = amount;
	}
	
	public MeasureUnit getUnit()
	{
		return unit;
	}
	
	public void setUnit(MeasureUnit unit)
	{
		this.unit = unit;
	}
	
}
