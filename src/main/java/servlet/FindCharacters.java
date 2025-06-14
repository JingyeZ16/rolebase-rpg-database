package servlet;

import dal.*;
import model.Characters;
import model.Player;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/findcharacters")
public class FindCharacters extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // Message label for response messages
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
        // Map for storing messages
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String sortBy = req.getParameter("sortBy");
        if ((email == null || email.trim().isEmpty()) && (name == null || name.trim().isEmpty())) {
            messages.put(RESPONSE_MESSAGE, "Please enter a valid email or name.");
        } else {
            try (Connection cxn = ConnectionManager.getConnection()) {
                List<Characters> characters = new ArrayList<>();

                if (email != null && !email.trim().isEmpty()) {
                    Player player = PlayerDAO.getPlayerByEmail(cxn, email);
                    if (player != null) {
                        characters = CharacterDAO.getCharactersByPlayerId(cxn, player);
                        messages.put(RESPONSE_MESSAGE, "Displaying characters for " + email);
                    } else {
                        messages.put(RESPONSE_MESSAGE, "No player found with email: " + email);
                    }
                } else if (name != null && !name.trim().isEmpty()) {
                    characters = CharacterDAO.searchCharactersByPartialName(cxn, name);
                    messages.put(RESPONSE_MESSAGE, "Displaying characters matching name: " + name);
                }


                if (sortBy != null) {
                    Comparator<Characters> comparator = null;
                    switch (sortBy) {
                        case "firstName":
                            comparator = Comparator.comparing(c -> c.getFirstName(), String.CASE_INSENSITIVE_ORDER);
                            break;
                        case "lastName":
                            comparator = Comparator.comparing(c -> c.getLastName(), String.CASE_INSENSITIVE_ORDER);
                            break;
                        case "race":
                            comparator = Comparator.comparing(c -> c.getRace().getName(), String.CASE_INSENSITIVE_ORDER);
                            break;
                        case "clan":
                            comparator = Comparator.comparing(c -> c.getClan().getClan(), String.CASE_INSENSITIVE_ORDER);
                            break;
                    }
                    if (comparator != null) {
                        characters.sort(comparator);
                    }
                }

                req.setAttribute(
                        "Characters",
                        characters
                );
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        req.getRequestDispatcher("/FindCharacters.jsp").forward(req, resp);
    }
}
