package ecommerce.servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecommerce.daos.ArquivoDAO;
import ecommerce.daos.ProdutoDAO;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestArquivo = request.getPathInfo().substring(1);
		
		boolean arquivoValido = false;
		
		if (!requestArquivo.contains("\\") && !requestArquivo.contains("/")) {
			Path diretorio = ArquivoDAO.construirCaminho(ArquivoDAO.DIRETORIO.toString(), ProdutoDAO.DIRETORIO_IMAGENS_PRODUTOS, request.getPathInfo().substring(1));
			
			if (Files.exists(diretorio) && !Files.isDirectory(diretorio)) {
				arquivoValido = true;
				
				response.setHeader("Content-Type", Files.probeContentType(diretorio));
				response.setHeader("Content-Length", String.valueOf(Files.size(diretorio)));
				response.setHeader("Content-Disposition", "inline; filename=\"" + requestArquivo + "\"");
				Files.copy(diretorio, response.getOutputStream());
			}
		}
		
		if (!arquivoValido) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			outStream.write(getClass().getResourceAsStream("nao_encontrado.png").readAllBytes());
			
			response.setHeader("Content-Type", URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(outStream.toByteArray())));
			response.setHeader("Content-Length", "" + outStream.size());
			response.setHeader("Content-Disposition", "inline; filename=\"" + "nao_encontrado.png" + "\"");
			response.getOutputStream().write(outStream.toByteArray());
		}
	}
}