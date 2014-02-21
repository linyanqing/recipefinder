package info.yanqing.recipefinder.model;

import java.util.Date;

/**
 * The food item in fridge
 */
public class FridgeItem extends FoodItem implements Comparable
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

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p/>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p/>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p/>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p/>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p/>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it
     *                            from being compared to this object.
     */
    public int compareTo(Object o) {
        if(!(o instanceof FridgeItem)){
            return 1;
        } else{
            FridgeItem fridgeItem = (FridgeItem)o;
            if(this.getUseBy() != null){
                return (this.getUseBy().compareTo(fridgeItem.getUseBy()));
            } else{
                return -1;
            }

        }
    }
}
