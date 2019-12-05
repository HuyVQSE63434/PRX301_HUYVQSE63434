/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.controller;

import huyvq.registration.UserBLO;
import huyvq.registration.UserInformation;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dell
 */
public class RegisterController extends HttpServlet {

    private static final String SUCCESS = "index.jsp";
    private static final String ERROR = "register.jsp";

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
        String url = ERROR;
        try {
            String username = request.getParameter("uname");
            String password = request.getParameter("psw");
            String confirmPassword = request.getParameter("cpsw");
            String address = request.getParameter("add");
            String phonenumber = request.getParameter("phonenum");
            String fullname = request.getParameter("fullname");
            UserBLO blo = new UserBLO();
            UserInformation user = blo.checkUserName(username);
            if (user == null) {
                if (confirmPassword.equalsIgnoreCase(password)) {
                    try {
                        int i = Integer.parseInt(phonenumber);
                        if (phonenumber.length() != 10) {
                            request.setAttribute("username", username);
                            request.setAttribute("passsword", password);
                            request.setAttribute("fullname", fullname);
                            request.setAttribute("address", address);
                            request.setAttribute("phonenumber", phonenumber);
                            request.setAttribute("phoneerror", "Phone number must be 10 number");
                        } else {
                            UserInformation newUser = new UserInformation();
                            newUser.setUserName(username);
                            newUser.setPassword(password);
                            newUser.setFullName(fullname);
                            newUser.setAddress(address);
                            newUser.setPhoneNumber(phonenumber);
                            boolean add = blo.AddNewUser(newUser);
                            if (add) {
                                url = SUCCESS;
                                HttpSession session = request.getSession();
                                session.setAttribute("USERNAME", username);
                                session.setAttribute("FULLNAME", fullname);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute("username", username);
                        request.setAttribute("passsword", password);
                        request.setAttribute("fullname", fullname);
                        request.setAttribute("address", address);
                        request.setAttribute("phonenumber", phonenumber);
                        request.setAttribute("phoneerror", "Phone number must be number");
                    }
                } else {

                    request.setAttribute("username", username);
                    request.setAttribute("passsword", password);
                    request.setAttribute("fullname", fullname);
                    request.setAttribute("address", address);
                    request.setAttribute("phonenumber", phonenumber);
                    request.setAttribute("repassworderror", "confirm password must be like password");
                }
            } else {

                request.setAttribute("username", username);
                request.setAttribute("passsword", password);
                request.setAttribute("fullname", fullname);
                request.setAttribute("address", address);
                request.setAttribute("phonenumber", phonenumber);
                request.setAttribute("nameerror", "Username has used");
            }
        } catch (Exception ex) {
            System.out.println("Error at LoginController : " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
