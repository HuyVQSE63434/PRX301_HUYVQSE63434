/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.controller;

import huyvq.drawl.XMLUtils;
import huyvq.object.Categories;
import huyvq.object.Products;
import huyvq.registration.CategoryBLO;
import huyvq.registration.Product;
import huyvq.registration.ProductBLO;
import huyvq.registration.TracingBLO;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author Dell
 */
public class FirstController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        try {
            String id = request.getParameter("id");
            PrintWriter out = response.getWriter();
            String search = request.getParameter("txtsearch");
            String action = request.getParameter("action");
            switch (action) {
                case "category":
                    getAllCategory(out);
                    break;
                case "accessCategory":
                    accessCategory(id.trim(), out, search);
                    break;
                case "accessNextCategory":
                    String lastCounter = request.getParameter("lastCounter");
                    accessNextCategory(id.trim(), lastCounter, out, search);
                    break;
                case "accessPreCategory":
                    String firstCounter = request.getParameter("firstCounter");
                    accessPreCategory(id.trim(), firstCounter, out, search);
                    break;
                case "accessProduct":
                    String productString = accessProduct(id, out);
                    HttpSession session = request.getSession();
                    countingViewTime(id, session.getAttribute("USERID"));
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(new InputSource(new StringReader(productString)));
                    request.setAttribute("DOC", doc);
                    request.getRequestDispatcher("detail.jsp").forward(request, response);
                    break;
            }
            //load most see product
//            ProductBLO proBlo = new ProductBLO();
//            Products pros = new Products();
//            pros.setProduct(proBlo.getMostSeeProduct(24));
//            String prosString = XMLUtils.marshallToString(pros);
//            request.setAttribute("mostseeproducts", prosString);
//            
//            //load user histories
//            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            response.getWriter().close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void getAllCategory(PrintWriter out) {
        //load category
        CategoryBLO cateBlo = new CategoryBLO();
        Categories categories = new Categories();
        categories.setCategory(cateBlo.getAllCategories());
        String cateString = XMLUtils.marshallToString(categories);
        //request.setAttribute("category", cateString);
        out.print(cateString);
    }

    private void accessCategory(String id, PrintWriter out, String search) {
        if (id.equalsIgnoreCase("main")) {
            accessMostSeeProduct(out, search);
        } else {
            ProductBLO blo = new ProductBLO();
            Products products = new Products();
            if (search.equals("null")) {
                search = "";
            }
            products.setProduct(blo.getProductByCategory(24, id, search));
            String productsString = XMLUtils.marshallToString(products);
            out.print(productsString);
        }
    }

    private void accessNextCategory(String id, String lastCounter, PrintWriter out, String search) {
        ProductBLO blo = new ProductBLO();
        Products products = new Products();
        int count = Integer.parseInt(lastCounter);
        if (search.equals("null")) {
            search = "";
        }
        products.setProduct(blo.getNextProductByCategory(count, 24, id, search));
        String productsString = XMLUtils.marshallToString(products);
        out.print(productsString);
    }

    private void accessPreCategory(String id, String firstCounter, PrintWriter out, String search) {
        ProductBLO blo = new ProductBLO();
        Products products = new Products();
        int count = Integer.parseInt(firstCounter);
        if (search.equals("null")) {
            search = "";
        }
        products.setProduct(blo.getPreProductByCategory(count, 24, id, search));
        String productsString = XMLUtils.marshallToString(products);
        out.print(productsString);
    }

    private String accessProduct(String id, PrintWriter out) {
        ProductBLO blo = new ProductBLO();
        List<Product> list = new ArrayList<>();
        list.add(blo.getProduct(id));
        Products products = new Products(list);
        String productString = XMLUtils.marshallToString(products);
        //out.print(productString);
        return productString;
    }

    private void countingViewTime(String id, Object userid) {
        TracingBLO blo = new TracingBLO();
        try {
            blo.countingViewTime(id, (int) userid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void accessMostSeeProduct(PrintWriter out, String search) {
        ProductBLO blo = new ProductBLO();
        Products products = new Products();
        if (search.equals("null")) {
            search = "";
        }
        products.setProduct(blo.getMostSeeProduct(24, search));
        String productsString = XMLUtils.marshallToString(products);
        out.print(productsString);
    }
}
