<html>
<body>
<h2>Recipe Finder</h2>

<form action="findrecipe" method="post" enctype="multipart/form-data">
    fridge csv list (csv file):&nbsp;<input type="file" name="ingredient"/>&nbsp; <a href="./upload/items.csv">Download
    CSV file sample</a>
    <br/>
    json recipe data (json file)&nbsp;<input type="file" name="recipe"/>&nbsp; <a href="./upload/recipe.json">Download
    JSON file sample</a>
    <br/>
    <button type="submit">Submit</button>
</form>

<%
    String result = (String) request.getAttribute("result");
    if (result != null) {
%>
Here is the recipe we found:<br/>
<%=result %>
<%
    }%>

</body>
</html>
