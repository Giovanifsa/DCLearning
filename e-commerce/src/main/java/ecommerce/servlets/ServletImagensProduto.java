package ecommerce.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 * Este servlet controla requisições na url <contexto>/imagensProdutos/arquivo.extensão
 * Isso permite o carregamento de imagens de qualquer lugar em disco, e não apenas da pasta resources do servidor.
 * 
 * A url <contexto>/imagensProdutos deve apenas ser utilizada para imagens de produtos!
 * Arquivos de imagens devem ser salvos no diretório System.getProperty("user.home")\\imagensProdutos\\arquivo.extensão.
 * 
 * @author Giovani
 *
 */
@WebServlet("/imagensProdutos/*")
public class ServletImagensProduto extends HttpServlet {
	public static final String DIRETORIO_IMAGENS_PRODUTOS = "imagensProdutos";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String filename = request.getPathInfo().substring(1);
	    
	    File file = new File(System.getProperty("user.home") + File.separator + "arquivosWeb" + File.separator, filename);
	    
	    response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
	    Files.copy(file.toPath(), response.getOutputStream());
	    
	}
}