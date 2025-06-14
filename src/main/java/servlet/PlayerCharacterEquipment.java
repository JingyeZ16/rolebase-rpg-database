package servlet;

import dal.CharacterDAO;
import dal.CharacterEquipmentDAO;
import dal.ConnectionManager;
import model.CharacterEquipment;
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

@WebServlet("/characterequipment")
public class PlayerCharacterEquipment extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String TITLE_MESSAGE = "title";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        List<CharacterEquipment> characterEquipments = new ArrayList<CharacterEquipment>();
        String characterId = req.getParameter("characterid");

        try (Connection cxn = ConnectionManager.getConnection()) {
            if (characterId != null && !characterId.trim().isEmpty()) {
                Characters character = CharacterDAO.getCharacterByID(cxn, Integer.parseInt(characterId));

                if (character == null) {
                    messages.put(TITLE_MESSAGE, "Character not found");
                } else {
                    characterEquipments = CharacterEquipmentDAO.getAllEquipmentById(cxn, character);
                    messages.put(TITLE_MESSAGE, "Equipments for Character " + character.getFirstName() + " " + character.getLastName());
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("characterequipments", characterEquipments);
        req.getRequestDispatcher("PlayerCharacterEquipment.jsp").forward(req, resp);
    }

}
