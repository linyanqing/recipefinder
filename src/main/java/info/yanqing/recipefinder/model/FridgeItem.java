package info.yanqing.recipefinder.model;

import java.util.Date;

public class FridgeItem extends FoodItem
{
	
	private Date useBy;
	
	public FridgeItem()
	{
	}
	
	public FridgeItem(String item, Integer amount, MeasureUnit unit, Date useBy)
	{
		super.setItem(item);
		super.setAmount(amount);
		super.setUnit(unit);
		this.useBy = useBy;
	}
	
	public Date getUseBy()
	{
		return useBy;
	}
	
	public void setUseBy(Date useBy)
	{
		this.useBy = useBy;
	}
	
}
