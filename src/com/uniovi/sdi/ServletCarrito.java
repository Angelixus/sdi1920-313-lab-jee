package com.uniovi.sdi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletExample
 */
@WebServlet("/incluirEnCarrito")
public class ServletCarrito extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int contador = 0;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletCarrito() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ConcurrentMap<String, Integer> concurrentCarrito = (ConcurrentHashMap<String, Integer>) request.getSession().getAttribute("carrito");
		// No hay carrito, creamos uno y lo insertamos en sesión
		if (concurrentCarrito == null) {
			concurrentCarrito = new ConcurrentHashMap<String, Integer>();
			request.getSession().setAttribute("carrito", concurrentCarrito);
		}
		String producto = request.getParameter("producto");
		if (producto != null) {
			insertarEnCarrito(concurrentCarrito, producto);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Tienda SDI: carrito</TITLE></HEAD>");
		out.println("<BODY>");
		out.println(carritoEnHTML(concurrentCarrito) + "<br>");
		out.println("<a href=\"index.jsp\">Volver</a></BODY></HTML>");
	}

	private void insertarEnCarrito(ConcurrentMap<String, Integer> carrito, String claveProducto) {
		if (carrito.get(claveProducto) == null)
			carrito.put(claveProducto, new Integer(1));
		else {
			int numeroArticulos = carrito.get(claveProducto).intValue();
			carrito.put(claveProducto, numeroArticulos + 1);
		}

	}
	
	private String carritoEnHTML(ConcurrentMap<String, Integer> carrito) {
		StringBuffer carritoEnHTML = new StringBuffer();
		for(String key : carrito.keySet()) {
			carritoEnHTML.append("<p>["+key+"], " + carrito.get(key)+" unidades </p>");
		}
		return carritoEnHTML.toString();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
