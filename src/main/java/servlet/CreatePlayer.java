package servlet;

import dal.ConnectionManager;
import dal.PlayerDAO;
import model.Player;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/createplayer")
public class CreatePlayer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String RESPONSE_MESSAGE = "response";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("messages", new HashMap<String, String>());
        //Just render the JSP.
        req.getRequestDispatcher("/CreatePlayer.jsp").forward(req, resp);
    }

    @Override
    public void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String fullName = req.getParameter("fullname");
        if (fullName == null || fullName.trim().isEmpty()) {
            messages.put(RESPONSE_MESSAGE, "Invalid full name");
        } else {

            String email = req.getParameter("email");

            try (Connection cxn = ConnectionManager.getConnection()) {

                Player player = PlayerDAO.getPlayerByEmail(cxn, email);
                if (player != null) {
                    messages.put(RESPONSE_MESSAGE, "Player already exists");

                } else {
                    // Exercise: parse the input for StatusLevel.
                    PlayerDAO.create(cxn, fullName, email);
                    messages.put(RESPONSE_MESSAGE, "Successfully created " + fullName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }

        req.getRequestDispatcher("/CreatePlayer.jsp").forward(req, resp);
    }
}
