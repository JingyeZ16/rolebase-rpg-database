package servlet;

import dal.CharacterCurrencyDAO;
import dal.CharacterDAO;
import dal.ConnectionManager;
import model.CharacterCurrency;
import model.Characters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/charactercurrency")
public class PlayerCharacterCurrency extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String TITLE_MESSAGE = "title";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        List<CharacterCurrency> characterCurrency = new ArrayList<CharacterCurrency>();
        String characterId = req.getParameter("characterid");

        try (Connection cxn = ConnectionManager.getConnection()) {
            if (characterId != null && !characterId.trim().isEmpty()) {
                Characters character = CharacterDAO.getCharacterByID(cxn, Integer.parseInt(characterId));

                if (character == null) {
                    messages.put(TITLE_MESSAGE, "Character not found");
                } else {
                    characterCurrency = CharacterCurrencyDAO.getCharacterCurrencies(cxn, character);
                    messages.put(TITLE_MESSAGE, "Currencies for Character " + character.getFirstName() + " " + character.getLastName());
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("charactercurrency", characterCurrency);
        req.getRequestDispatcher("PlayerCharacterCurrency.jsp").forward(req, resp);
    }

}
