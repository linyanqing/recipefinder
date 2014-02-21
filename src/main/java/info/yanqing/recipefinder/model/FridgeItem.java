package info.yanqing.recipefinder.model;

import java.util.Date;

/**
 * The food item in fridge
 */
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

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof FridgeItem)) return false;
        if (!super.equals(o)) return false;

        FridgeItem that = (FridgeItem) o;

        if (!useBy.equals(that.useBy)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + useBy.hashCode();
        return result;
    }
}
