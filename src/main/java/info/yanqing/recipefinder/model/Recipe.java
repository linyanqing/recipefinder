package info.yanqing.recipefinder.model;

import java.util.Date;
import java.util.List;

public class Recipe implements Comparable
{
	private String name;
	private List<IngredientItem> ingredients;
	private long totalUsedByTime;
    private Date closestUsedBy;

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

    public long getTotalUsedByTime() {
        return totalUsedByTime;
    }

    public void setTotalUsedByTime(long totalUsedByTime) {
        this.totalUsedByTime = totalUsedByTime;
    }

    public Date getClosestUsedBy() {
        return closestUsedBy;
    }

    public void setClosestUsedBy(Date closestUsedBy) {
        this.closestUsedBy = closestUsedBy;
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

        if( !(o instanceof Recipe)){
            return 1;
        } else{
            Recipe otherRecipe = (Recipe)o;
           int result = this.getClosestUsedBy().compareTo( otherRecipe.getClosestUsedBy());
           if(result ==0){
               return (this.getTotalUsedByTime() < otherRecipe.getTotalUsedByTime() ? -1 : (this.getTotalUsedByTime()==otherRecipe.getTotalUsedByTime() ? 0 : 1));
           } else{
               return result;
           }


        }


    }
}
