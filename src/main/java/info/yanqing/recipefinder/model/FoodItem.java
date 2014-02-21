package info.yanqing.recipefinder.model;

import java.util.Date;

/**
 * The class to represent food item
 */
public class FoodItem
{
	private String item;
	private Integer amount;
	private MeasureUnit unit;

	public FoodItem(String item, Integer amount, MeasureUnit unit, Date useBy)
	{
		this.item = item;
		this.amount = amount;
		this.unit = unit;
	}

    public FoodItem() {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FoodItem)) return false;

        FoodItem foodItem = (FoodItem) o;

        if (!amount.equals(foodItem.amount)) return false;
        if (!item.equals(foodItem.item)) return false;
        if (unit != foodItem.unit) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = item.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + unit.hashCode();
        return result;
    }
}
