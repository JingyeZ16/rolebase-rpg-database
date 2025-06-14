package servlet;

import dal.CharacterDAO;
import dal.CharacterStatsDAO;
import dal.ConnectionManager;
import model.CharacterStats;
import model.Characters;
import model.Statistics;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/updatecharacter")
public class UpdateCharacterStats extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String RESPONSE_MESSAGE = "response";

    @Override
    public void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        handleRequest(req, resp);
    }


    @Override
    public void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        handleRequest(req, resp);
    }


    private void handleRequest(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String characterID = req.getParameter("characterid");
        System.out.println(characterID);
        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            messages.put(RESPONSE_MESSAGE, "Invalid UserName");
        } else {
            // Delete the BlogUser.
            try (Connection cxn = ConnectionManager.getConnection()) {

                Characters character = CharacterDAO.getCharacterByID(cxn, Integer.parseInt(characterID));
                for (Statistics stat : Statistics.values()) {
                    String valueStr = req.getParameter(stat.getName());

                    if (valueStr != null && !valueStr.trim().isEmpty()) {
                        try {
                            int value = valueStr != null && !valueStr.trim().isEmpty()
                                    ? Integer.parseInt(valueStr.trim()) : 0;
                            CharacterStats cs = new CharacterStats(stat, character, value);
                            CharacterStatsDAO.updateStat(cxn, cs);
                            messages.put(RESPONSE_MESSAGE, "Successfully updated");

                        } catch (NumberFormatException e) {
                            messages.put(RESPONSE_MESSAGE, "Invalid number for " + stat.getName());

                        }
                    }
                }

                List<CharacterStats> characterStats = CharacterStatsDAO.getCharacterStatsById(cxn, character);
                req.setAttribute("characterstats", characterStats);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        req.getRequestDispatcher("/UpdateCharacterStats.jsp").forward(req, resp);
    }
}
