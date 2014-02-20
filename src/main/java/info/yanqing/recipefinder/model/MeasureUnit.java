package info.yanqing.recipefinder.model;

public enum MeasureUnit
{
	UNKNOWN, slices, grams, ml;
	
	public static MeasureUnit fromString(String s)
	{
		if (s.equalsIgnoreCase("grams"))
			return MeasureUnit.grams;
		else if (s.equalsIgnoreCase("ml"))
			return MeasureUnit.ml;
		else if (s.equalsIgnoreCase("slices"))
			return MeasureUnit.slices;
		else
			return MeasureUnit.UNKNOWN;
	}
}
