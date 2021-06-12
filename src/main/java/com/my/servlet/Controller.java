package com.my.servlet;

import com.my.command.commandContainer.Command;
import com.my.command.commandContainer.CommandContainer;
import com.my.jdbc.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = {"/controller"})
//@WebServlet
public class Controller extends HttpServlet {
    private static final long serialVersionUID = -201467326342620315L;

    private Logger logger = LogManager.getLogger(Controller.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("controller#doGet");

        String address = "error.jsp";
        String commandName = req.getParameter("command");
        Command command = CommandContainer.getCommand(commandName);

        try {
            address = command.execute(req, resp);
        } catch (DBException ex) {
            req.setAttribute("error", ex);
        }
        logger.info("address ==> " + address);
        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("controller#doPost");
        String address = "error.jsp";
        String commandName = req.getParameter("command");
        Command command = CommandContainer.getCommand(commandName);

        try {
            address = command.execute(req, resp);
        } catch (DBException ex) {
            req.getSession().setAttribute("error", ex);
        }

        logger.info("address ==> " + address);

        resp.sendRedirect(address);
    }
}
