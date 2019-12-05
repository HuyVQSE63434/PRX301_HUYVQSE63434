/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.controller;

import huyvq.drawl.XMLUtils;
import huyvq.object.Products;
import huyvq.registration.MixColor;
import huyvq.registration.MixColorBLO;
import huyvq.registration.Product;
import huyvq.registration.ProductBLO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
public class DetailController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            String productId = request.getParameter("productid");
            ProductBLO problo = new ProductBLO();
            Product pro = problo.getProduct(productId);

            //find mix clothes
            MixColorBLO mixBlo = new MixColorBLO();
            List<MixColor> suitColors = mixBlo.findMixColor(pro.getColorId().getId());
            boolean upper = pro.getTypeId().getUpper();
            List<Product> mixProducts = new ArrayList<>();
            for (MixColor m : suitColors) {
                List<Product> p = problo.findMostPopularProductByColor(m.getColor1().getId(), !upper);
                mixProducts.addAll(p);
            }
            String xmlString = XMLUtils.marshallToString(new Products(mixProducts));
            request.setAttribute("mixclothes", xmlString);

            //find suggest clothes
            List<Product> suggestProducts = problo.findSuggestProduct(productId);
            xmlString = XMLUtils.marshallToString(new Products(suggestProducts));
            request.setAttribute("suggestclothes", xmlString);
            
            System.out.println("done load mix and suggest product:\n");
            System.out.println("Suggest product:"+ suggestProducts.size() + "product\n");
            System.out.println("mix product:" + mixProducts.size() +"product\n");
        } catch (Exception e) {
            e.printStackTrace();
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

}
