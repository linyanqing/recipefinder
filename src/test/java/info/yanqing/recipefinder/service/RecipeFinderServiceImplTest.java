package info.yanqing.recipefinder.service;

import info.yanqing.recipefinder.model.FridgeItem;
import info.yanqing.recipefinder.model.MeasureUnit;
import info.yanqing.recipefinder.model.Recipe;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yanqing
 * Date: 21/02/14
 * Time: 9:26 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class RecipeFinderServiceImplTest {


    private final SimpleDateFormat DDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private RecipeFinderService recipeFinderService;

    @Test
    public void testFindRecipe() throws Exception {
        

    }


    @Test
    public void testParseFileItemToString() throws Exception {

    }

    @Test
    public void testGetFridgeItemsFromString() throws Exception {
        //construct content
        String content = "bread,10,slices,25/12/2014\n" +
                "cheese,10,slices,25/12/2014\n" +
                "butter,250,grams,25/12/2014\n" +
                "peanut butter,250,grams,2/12/2014\n" +
                "mixed salad,150,grams,26/12/2014";

        //execute method
        List<FridgeItem> fridgeItemList =  recipeFinderService.getFridgeItemsFromString(content);

        //assertion for list size
        Assert.assertEquals(5,fridgeItemList.size());

        //assertion for list element
        Date useBy = DDMMYYYY.parse("26/12/2014");
        FridgeItem expectedFridgeItem = new FridgeItem("mixed salad", 150, MeasureUnit.grams, useBy);
        Assert.assertEquals(expectedFridgeItem, fridgeItemList.get(4));

    }

    @Test
    public void testRecipeItemsFromString() throws IOException {
        String content = "[    {        \"name\":\"grilled cheese on toast\",        \"ingredients\":[            {                \"item\":\"bread\",                \"amount\":\"2\",                \"unit\":\"slices\"            },            {                \"item\":\"cheese\",                \"amount\":\"2\",                \"unit\":\"slices\"            }        ]    }    ,    {        \"name\":\"salad sandwich\",        \"ingredients\":[            {                \"item\":\"bread\",                \"amount\":\"2\",                \"unit\":\"slices\"            },            {                \"item\":\"mixed salad\",                \"amount\":\"100\",                \"unit\":\"grams\"            }        ]    }]";
    
        //execute method
        List<Recipe> recipeList = recipeFinderService.getRecipesFromString(content);

         //assertion for list size
        Assert.assertEquals(2, recipeList.size());
        //assertion for list element
    }

    @Test
    public void testGetValidRecipe() throws Exception {
        //getValidRecipe(List<Recipe> recipes, List<FridgeItem> ingredients)

        String content = "[    {        \"name\":\"grilled cheese on toast\",        \"ingredients\":[            {                \"item\":\"bread\",                \"amount\":\"2\",                \"unit\":\"slices\"            },            {                \"item\":\"cheese\",                \"amount\":\"2\",                \"unit\":\"slices\"            }        ]    }    ,    {        \"name\":\"salad sandwich\",        \"ingredients\":[            {                \"item\":\"bread\",                \"amount\":\"2\",                \"unit\":\"slices\"            },            {                \"item\":\"mixed salad\",                \"amount\":\"100\",                \"unit\":\"grams\"            }        ]    }]";

        //execute method
        List<Recipe> recipes = recipeFinderService.getRecipesFromString(content);

        content = "bread,10,slices,25/12/2014\n" +
                        "cheese,10,slices,25/12/2014\n" +
                        "butter,250,grams,25/12/2014\n" +
                        "peanut butter,250,grams,2/12/2014\n" +
                        "mixed salad,150,grams,26/12/2014";

        //execute method
        List<FridgeItem> fridgeItemList =  recipeFinderService.getFridgeItemsFromString(content);

        String recipe = recipeFinderService.getValidRecipe(recipes, fridgeItemList);

       Assert.assertEquals("grilled cheese on toast", recipe);
    }

    @Test
    public void testGetValidFridgeItems() throws ParseException {

        //construct fridge items
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        Date useBy = new Date(c.getTimeInMillis());

        c.add(Calendar.DATE, -5);
        Date useByExpired = new Date(c.getTimeInMillis());

        FridgeItem expiredFridgeItem = new FridgeItem("mixed salad", 150, MeasureUnit.grams, useByExpired);
        FridgeItem fridgeItem = new FridgeItem("bread", 10, MeasureUnit.grams, useBy);

        List<FridgeItem> fridgeItemList = new ArrayList<FridgeItem>();
        fridgeItemList.add(expiredFridgeItem);

        //assertion for expired fridge items
        List<FridgeItem> validFridgeItemList = recipeFinderService.getValidFridgeItems(fridgeItemList);
        Assert.assertEquals(0, validFridgeItemList.size());

        //assertion for valid fridge items
        fridgeItemList.add(fridgeItem);
        validFridgeItemList = recipeFinderService.getValidFridgeItems(fridgeItemList);
        Assert.assertEquals(1, validFridgeItemList.size());



    }

}
