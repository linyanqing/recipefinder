package info.yanqing.recipefinder.controller;

import info.yanqing.recipefinder.service.RecipeFinderService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class RecipeFinderServlet
 */
public class RecipeFinderController implements HttpRequestHandler
{
	
	private RecipeFinderService recipeFinderService;
	
	/**
	 * Process the given request, generating a response.
	 * 
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @throws javax.servlet.ServletException in case of general errors
	 * @throws java.io.IOException in case of I/O errors
	 */
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		
		request.setCharacterEncoding("utf-8");
		// create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletContext servletContext = request.getSession().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try
		{
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			request.setAttribute("result", recipeFinderService.findRecipe(list));
		}
		catch (FileUploadException e)
		{
			request.setAttribute("result", e.toString());
			e.printStackTrace();
		}
		catch (Exception e)
		{
			request.setAttribute("result", e.toString());
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	public RecipeFinderService getRecipeFinderService()
	{
		return recipeFinderService;
	}
	
	public void setRecipeFinderService(RecipeFinderService recipeFinderService)
	{
		this.recipeFinderService = recipeFinderService;
	}
}
