package servlet;

import dal.*;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/detail")
public class CharacterDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String RESPONSE_MESSAGE = "response";

    @Override
    public void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String characterid = req.getParameter("characterid");
        if (characterid == null || characterid.isEmpty()) {
            messages.put(RESPONSE_MESSAGE, "characterid is empty");
        } else {

            try (Connection cxn = ConnectionManager.getConnection()) {
                Characters character = CharacterDAO.getCharacterByID(cxn, Integer.parseInt(characterid));
                List<CharacterStats> stats = CharacterStatsDAO.getCharacterStatsById(cxn, character);
                List<CharacterEquipment> characterEquipments = CharacterEquipmentDAO.getAllEquipmentById(cxn, character);
                List<String> allStats = Arrays.stream(Statistics.values()).map(Statistics::getName).collect(Collectors.toList());
                List<EquipmentStatRow> rows = new ArrayList<>();

                for (CharacterEquipment equip : characterEquipments) {

                    List<GearBonus> bonuses = GearBonusDAO.getGearBonusById(cxn, equip.getItem());
                    Map<String, Integer> statMap = new LinkedHashMap<>();

                    for (String stat : allStats) {
                        statMap.put(stat, 0);
                    }
                    for (GearBonus bonus : bonuses) {
                        statMap.put(bonus.getStatistics().getName(), bonus.getValue());
                    }

                    EquipmentStatRow row = new EquipmentStatRow();
                    row.setItemName(equip.getItem().getItemName());
                    row.setSlot(equip.getSlot().getName());
                    row.setStatValues(statMap);
                    rows.add(row);

                }

                req.setAttribute("stats", stats);
                req.setAttribute("character", character);
                req.setAttribute("equipmentStatRows", rows);
                req.setAttribute("allstats", allStats);
                messages.put(RESPONSE_MESSAGE, "Displaying results for " + character.getFirstName());


            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }

        }
        req.getRequestDispatcher("/CharacterDetail.jsp").forward(req, resp);
    }
}
