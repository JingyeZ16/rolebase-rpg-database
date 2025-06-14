
import dal.*;
import model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Driver {
    public static void main(String[] args) {
        try {
            resetSchema();
            insertRecords();
        } catch (SQLException e) {
            System.out.print("SQL Exception: ");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void insertRecords() throws SQLException {
        try (
                Connection cxn = ConnectionManager.getConnection()
        ) {
            List<Player> players = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Player p = PlayerDAO.create(cxn, "TestPlayer" + i, "player" + i + "@mail.com");
                players.add(p);
            }

// === 2. Insert Clans ===
            List<Clan> clans = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Clan c = ClanDAO.create(cxn, Race.ELF, "Clan" + i);
                clans.add(c);
            }

// === 3. Insert Characters ===
            List<Characters> characters = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Characters c = CharacterDAO.create(cxn, players.get(i), "First" + i, "Last" + i, Race.ELF, clans.get(i % clans.size()));
                characters.add(c);
            }

// === 4. Insert CharacterJobs ===
            for (Characters c : characters) {
                CharacterJobDAO.createCharacterJob(cxn, Job.WARRIOR, c, 10, 1000, false);
            }

// === 5. Insert Currency ===
            List<Currency> currencies = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                currencies.add(CurrencyDAO.create(cxn, "Coin" + i, 100000 + i * 1000, 1000 + i * 50));
            }

// === 6. Insert CharacterCurrency ===
            for (Characters c : characters) {
                for (int i = 0; i < 3; i++) {
                    Currency currency = currencies.get(i);
                    CharacterCurrencyDAO.create(cxn, currency, c, 5000 + i * 100, 100 + i * 10);
                }
            }

// === 7. Insert CharacterStats ===
            Statistics[] stats = Statistics.values();
            for (Characters c : characters) {
                for (Statistics stat : stats) {
                    int value = switch (stat) {
                        case HP -> 60 + new Random().nextInt(50);
                        case MP -> 30 + new Random().nextInt(50);
                        default -> 2 + new Random().nextInt(6);
                    };
                    CharacterStatsDAO.create(cxn, stat, c, value);
                }
            }

// === 8. Insert Items ===
            List<Item> items = new ArrayList<>();
            for (int i = 1; i <= 15; i++) {
                items.add(ItemDAO.create(cxn, "Item" + i, 1, 10, 100 + i * 5, 1));
            }

// === 9. Insert Gear ===
            List<Gear> gears = new ArrayList<>();
            Slot[] gearSlots = {Slot.HELMET, Slot.BOOTS, Slot.ARMOR};
            for (int i = 0; i < 10; i++) {
                Gear g = GearDAO.create(cxn, "Gear" + i, 5, 1, 1000 + i * 10, 1, gearSlots[i % gearSlots.length]);
                gears.add(g);
                GearBonusDAO.create(cxn, g, Statistics.HP, 40 + i);
                GearBonusDAO.create(cxn, g, Statistics.VITALITY, 2 + i % 5);
            }

// === 10. Insert Weapons ===
            List<Weapon> weapons = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Weapon w = WeaponDAO.create(cxn, "Sword" + i, 10, 1, 1200 + i * 20, 2, Job.WARRIOR, 40 + i * 2);
                weapons.add(w);
                GearBonusDAO.create(cxn, w, Statistics.STRENGTH, 3 + i % 3);
                GearBonusDAO.create(cxn, w, Statistics.CRITICALRATE, 1 + i % 5);
            }

// === 11. Insert Consumables ===
            for (int i = 1; i <= 10; i++) {
                Item base = ItemDAO.create(cxn, "Potion" + i, 1, 5, 50 + i * 2, 1);
                Consumable con = ConsumableDAO.create(cxn, base, "Heals for " + (50 + i * 5));
                ConsumableBonusDAO.create(cxn, con, Statistics.HP, 10 + i, 20, 0.10f + i * 0.01f);
            }

// === 12. Insert CharacterEquipment ===
            for (int i = 0; i < characters.size(); i++) {
                Characters c = characters.get(i);
                CharacterEquipmentDAO.create(cxn, c, Slot.HELMET, gears.get(i % gears.size()));
                CharacterEquipmentDAO.create(cxn, c, Slot.WEAPON, weapons.get(i % weapons.size()));
            }

// === 13. Insert CharacterInventory ===
            for (Characters c : characters) {
                for (int i = 1; i <= 3; i++) {
                    CharacterInventoryDAO.create(cxn, i, c, items.get(i), 5 + i);
                }
            }

//            System.out.println("Testing PlayerDAO...");
//            Player player1 = PlayerDAO.create(cxn, "TestUser1", "test1@example.com");
//            Player player2 = PlayerDAO.create(cxn, "TestUser2", "test2@example.com");
//            Player player3 = PlayerDAO.create(cxn, "TestUser3", "test3@example.com");
//
//            Player fetchedPlayer = PlayerDAO.getPlayerByID(cxn, player1.getPlayerID());
//            System.out.println("Fetched Player: " + fetchedPlayer.getFullName());
//
//            System.out.println("Testing ClanDAO...");
//            Clan clan1 = ClanDAO.create(cxn, Race.ELF, "TestClan1");
//            Clan clan2 = ClanDAO.create(cxn, Race.ELF, "TestClan2");
//            Clan clan3 = ClanDAO.create(cxn, Race.HUMAN, "TestClan3");
//            Clan fetchedClan = ClanDAO.getClanByName(cxn, "TestClan1", Race.ELF);
//            System.out.println("Fetched Clan: " + fetchedClan.getClan());
//            List<Clan> clansByRace = ClanDAO.getAllClansByRaceName(cxn, Race.ELF);
//            System.out.println("Total ELF clans: " + clansByRace.size());
//
//            System.out.println("Testing CharacterDAO...");
//            Characters character1 = CharacterDAO.create(cxn, player1, "Alice", "Light", Race.ELF, clan1);
//            Characters character2 = CharacterDAO.create(cxn, player1, "Bob", "Shadow", Race.ELF, clan2);
//            Characters character3 = CharacterDAO.create(cxn, player2, "Cleo", "Storm", Race.HUMAN, clan3);
//            Characters character4 = CharacterDAO.create(cxn, player2, "Drake", "Frost", Race.ELF, clan1);
//            Characters character5 = CharacterDAO.create(cxn, player3, "Eve", "Blaze", Race.HUMAN, clan3);
//            Characters character6 = CharacterDAO.create(cxn, player3, "Finn", "Stone", Race.ELF, clan2);
//            Characters character7 = CharacterDAO.create(cxn, player3, "Gwen", "Ash", Race.ELF, clan1);
//            Characters fetchedCharacter = CharacterDAO.getCharacterByID(cxn, character1.getCharacterID());
//            System.out.println("Fetched Character: " + fetchedCharacter.getFirstName());
//            List<Characters> charactersByPlayer = CharacterDAO.getCharactersByPlayerId(cxn, player1);
//            System.out.println("Characters under player1: " + charactersByPlayer.size());
//
//            System.out.println("Testing CharacterJobDAO...");
//            CharacterJob job1 = CharacterJobDAO.createCharacterJob(cxn, Job.WARRIOR, character1, 10, 1000, false);
//            CharacterJob job2 = CharacterJobDAO.createCharacterJob(cxn, Job.MAGE, character2, 15, 2000, true);
//            CharacterJob fetchedJob = CharacterJobDAO.getCharacterJobByIdAndJobName(cxn, character1, Job.WARRIOR);
//            System.out.println("Fetched Job: " + fetchedJob.getJob().name());
//            List<CharacterJob> jobs = CharacterJobDAO.getAllCharacterJobsById(cxn, character1);
//            System.out.println("Total Jobs for character1: " + jobs.size());
//
//            CurrencyDAO.create(cxn, "Gold", 100000, 5000);
//            CurrencyDAO.create(cxn, "Silver", 5000000, 100000);
//            CurrencyDAO.create(cxn, "Platinum", 5000, 200);
//            CurrencyDAO.create(cxn, "MagicCoins", 100000, 3000);
//            CurrencyDAO.create(cxn, "DivineCoins", 500, 50);
//            CurrencyDAO.create(cxn, "ChaosCoins", 500, 50);
//            CurrencyDAO.create(cxn, "ExaltedCoins", 500, 50);
//
//            System.out.println("Testing CharacterCurrencyDAO...");
//            for (int i = 1; i <= 7; i++) {
//                Characters c = CharacterDAO.getCharacterByID(cxn, i);
//                CharacterCurrencyDAO.create(cxn, CurrencyDAO.getCurrencyByName(cxn, "Gold"), c, 9000 + i * 100, 500 + i);
//                CharacterCurrencyDAO.create(cxn, CurrencyDAO.getCurrencyByName(cxn, "Silver"), c, 400000 + i * 1000, 70000 + i * 100);
//                CharacterCurrencyDAO.create(cxn, CurrencyDAO.getCurrencyByName(cxn, "Platinum"), c, 2000 + i * 50, 180 + i);
//                CharacterCurrencyDAO.create(cxn, CurrencyDAO.getCurrencyByName(cxn, "MagicCoins"), c, 7000 + i * 100, 2000 + i * 100);
//                CharacterCurrencyDAO.create(cxn, CurrencyDAO.getCurrencyByName(cxn, "DivineCoins"), c, 400 + i * 5, 40 + i);
//                CharacterCurrencyDAO.create(cxn, CurrencyDAO.getCurrencyByName(cxn, "ChaosCoins"), c, 200 + i * 10, 45 + i);
//                CharacterCurrencyDAO.create(cxn, CurrencyDAO.getCurrencyByName(cxn, "ExaltedCoins"), c, 300 + i * 20, 48 + i);
//                CharacterCurrencyDAO.getCharacterCurrencyByIdAndName(cxn, c, "Gold");
//                CharacterCurrencyDAO.getCharacterCurrencies(cxn, c);
//            }
//
//            System.out.println("Testing CharacterStatsDAO...");
//            Statistics[] stats = Statistics.values();
//            int[][] values = {
//                    {12, 8, 10, 7, 120, 90, 3, 2},
//                    {9, 14, 7, 10, 100, 110, 4, 3},
//                    {8, 10, 15, 6, 140, 70, 2, 4},
//                    {6, 7, 8, 18, 80, 160, 5, 3},
//                    {14, 10, 9, 6, 130, 60, 3, 4},
//                    {7, 9, 12, 15, 100, 140, 4, 5},
//                    {11, 12, 10, 9, 110, 95, 6, 2}
//            };
//            for (int i = 0; i < 7; i++) {
//                Characters c = CharacterDAO.getCharacterByID(cxn, i + 1);
//                for (int j = 0; j < stats.length; j++) {
//                    CharacterStatsDAO.create(cxn, stats[j], c, values[i][j]);
//                    CharacterStatsDAO.getCharacterStatsByIDAndStatsName(cxn, c, stats[j]);
//                }
//                CharacterStatsDAO.getCharacterStatsById(cxn, c);
//            }
//
//            System.out.println("Testing CharacterInventoryDAO...");
//            Item potion = ItemDAO.create(cxn, "Health Potion", 1, 10, 100, 1);
//            for (int i = 1; i <= 3; i++) {
//                Characters c = CharacterDAO.getCharacterByID(cxn, i);
//                CharacterInventoryDAO.create(cxn, i, c, potion, 5 + i);
//                CharacterInventoryDAO.getInventoryByIdAndSlotNumber(cxn, c, i);
//            }
//            CharacterInventoryDAO.getAllInventoryById(cxn, CharacterDAO.getCharacterByID(cxn, 1));
//
//            System.out.println("Testing GearDAO and GearBonusDAO...");
//            Slot helmet = Slot.HELMET;
//            Gear g1 = GearDAO.create(cxn, "Iron Helm", 5, 1, 1200, 1, helmet);
//            Gear g2 = GearDAO.create(cxn, "Steel Visor", 10, 1, 2500, 5, helmet);
//            Gear g3 = GearDAO.create(cxn, "Mystic Hood", 15, 1, 4000, 8, helmet);
//            GearBonusDAO.create(cxn, g1, Statistics.STRENGTH, 2);
//            GearBonusDAO.create(cxn, g2, Statistics.INTELLIGENCE, 3);
//            GearBonusDAO.create(cxn, g3, Statistics.HP, 50);
//            GearBonusDAO.getGearBonusById(cxn, g1);
//            GearBonusDAO.getGearBonusByIdAndStatsName(cxn, g1, Statistics.STRENGTH.name());
//
//            System.out.println("Testing CharacterEquipmentDAO with Gear...");
////            CharacterEquipmentDAO.create(cxn, CharacterDAO.getCharacterByID(cxn, 1), helmet, g1);
////            CharacterEquipmentDAO.create(cxn, CharacterDAO.getCharacterByID(cxn, 2), helmet, g2);
////            CharacterEquipmentDAO.create(cxn, CharacterDAO.getCharacterByID(cxn, 3), helmet, g3);
////            CharacterEquipmentDAO.getEquipmentByIdAndSlot(cxn, CharacterDAO.getCharacterByID(cxn, 1), helmet);
////            CharacterEquipmentDAO.getAllEquipmentById(cxn, CharacterDAO.getCharacterByID(cxn, 1));
//
//            System.out.println("Testing WeaponDAO...");
//            Weapon w1 = WeaponDAO.create(cxn, "Iron Sword", 10, 1, 1200, 2, Job.WARRIOR, 40);
//            Weapon w2 = WeaponDAO.create(cxn, "Mage Wand", 12, 1, 1500, 4, Job.MAGE, 55);
//            Weapon fetchedW1 = WeaponDAO.getWeaponById(cxn, w1.getItemID());
//
//            WeaponDAO.updateWeaponDamage(cxn, w1, 45);
////            CharacterEquipmentDAO.create(cxn, CharacterDAO.getCharacterByID(cxn, 1), Slot.WEAPON, w1);
////            CharacterEquipmentDAO.create(cxn, CharacterDAO.getCharacterByID(cxn, 2), Slot.WEAPON, w2);
//
//            System.out.println("Testing ConsumableDAO and ConsumableBonusDAO...");
//            Item boostItem = ItemDAO.create(cxn, "Boost Elixir", 5, 1, 200, 3);
//            Consumable boost = ConsumableDAO.create(cxn, boostItem, "Boost strength");
//            ConsumableDAO.getConsumableById(cxn, boostItem);
//            ConsumableDAO.updateDescription(cxn, boost, "Boosts strength for 5 minutes");
//            ConsumableBonusDAO.create(cxn, boost, Statistics.STRENGTH, 5, 10, 0.2f);
//            ConsumableBonusDAO.getConsumableBonusByItemIDAndStatsName(cxn, boost, Statistics.STRENGTH);
//            ConsumableBonusDAO.getConsumableBonusByItemID(cxn, boostItem);
//
//            System.out.println("Extended DAO testing done based on SQL inserts.");
//
//            Slot boot = Slot.BOOTS;
//            Slot weapon = Slot.WEAPON;
//
//            List<Characters> allCharacters = CharacterDAO.getAllCharacters(cxn); // 建议你添加这个 DAO 方法
//
//            int gearCounter = 1000; // 用于避免 ItemID 冲突（如果你用手动主键）
//
//            for (Characters character : allCharacters) {
//                // Helmet
//                Gear helm = GearDAO.create(cxn, "Iron Helmet", 5, 1, 1200, 2, Slot.HELMET);
//                GearBonusDAO.create(cxn, helm, Statistics.HP, 40);
//                GearBonusDAO.create(cxn, helm, Statistics.VITALITY, 3);
//                GearBonusDAO.create(cxn, helm, Statistics.EVASIONRATE, 2);
//                CharacterEquipmentDAO.create(cxn, character, helmet, helm);
//
//                Gear bt = GearDAO.create(cxn, "Iron Chestplate", 8, 1, 1800, 3, Slot.BOOTS);
//                GearBonusDAO.create(cxn, bt, Statistics.STRENGTH, 4);
//                GearBonusDAO.create(cxn, bt, Statistics.VITALITY, 4);
//                GearBonusDAO.create(cxn, bt, Statistics.HP, 50);
//                CharacterEquipmentDAO.create(cxn, character, boot, bt);
//
//
//                Weapon sword = WeaponDAO.create(cxn, "Bronze Sword", 10, 1, 1500, 4, Job.WARRIOR, 60);
//                GearBonusDAO.create(cxn, sword, Statistics.STRENGTH, 6);
//                GearBonusDAO.create(cxn, sword, Statistics.DEXTERITY, 3);
//                GearBonusDAO.create(cxn, sword, Statistics.CRITICALRATE, 5);
//                CharacterEquipmentDAO.create(cxn, character, weapon, sword);

//        }
        cxn.close();
    }
}

private static void resetSchema() throws SQLException {
    try (
            Connection cxn = ConnectionManager.getSchemalessConnection()
    ) {
        cxn.createStatement().executeUpdate(
                "drop schema if exists PM3;"
        );
        cxn.createStatement().executeUpdate("create schema PM3;");
        cxn.createStatement().executeUpdate("use PM3;");
    }

    try (
            Connection cxn = ConnectionManager.getConnection()
    ) {
        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Player (
                                        PlayerID INT AUTO_INCREMENT PRIMARY KEY,
                                        FullName VARCHAR(255) NOT NULL,
                                        Email VARCHAR(255) UNIQUE NOT NULL,
                                        AccountCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
                                    );              
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Race (
                                        RaceName VARCHAR(100) PRIMARY KEY
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Clan (
                                          RaceName VARCHAR(100) NOT NULL,
                                          ClanName VARCHAR(100) NOT NULL,
                                          PRIMARY KEY (RaceName, ClanName),
                                          FOREIGN KEY (RaceName) REFERENCES Race(RaceName)
                                            ON DELETE CASCADE ON UPDATE CASCADE,
                                      	CONSTRAINT unique_race_clan UNIQUE (ClanName, RaceName)
                                      );
                """
        );

        cxn.createStatement().executeUpdate("""
                                                       
                                     CREATE TABLE Job (
                                        JobName VARCHAR(100) PRIMARY KEY
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Characters (
                                        CharacterID INT AUTO_INCREMENT PRIMARY KEY,
                                        PlayerID INT NOT NULL,
                                        FirstName VARCHAR(100) NOT NULL,
                                        LastName VARCHAR(100) NOT NULL,
                                        RaceName VARCHAR(100),
                                        ClanName VARCHAR(100),
                                    -- ON DELETE CASCADE (Cascade Delete): Deleting a Player should delete all their Characters
                                        FOREIGN KEY (PlayerID) REFERENCES Player(PlayerID)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (RaceName) REFERENCES Race(RaceName)
                                          ON DELETE SET NULL ON UPDATE CASCADE,
                                        FOREIGN KEY (RaceName, ClanName) REFERENCES Clan(RaceName, ClanName)
                                          ON DELETE SET NULL ON UPDATE CASCADE
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE CharacterJob (
                                        JobName VARCHAR(100) NOT NULL,
                                        CharacterID INT NOT NULL,
                                        `Level` INT NOT NULL DEFAULT 1,
                                        XP INT NOT NULL DEFAULT 0,
                                        `Lock` BOOLEAN NOT NULL DEFAULT FALSE,
                                        PRIMARY KEY (JobName, CharacterID),
                                        FOREIGN KEY (JobName) REFERENCES Job(JobName)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                    -- Deleting a Character should delete its related data
                                        FOREIGN KEY (CharacterID) REFERENCES Characters(CharacterID)
                                          ON DELETE CASCADE ON UPDATE CASCADE
                                    );
                """
        );
        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Currency (
                                          CurrencyName VARCHAR(100) PRIMARY KEY,
                                          Cap INT,
                                          WeeklyCap INT
                                      );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE CharacterCurrency (
                                            CurrencyName VARCHAR(100) NOT NULL,
                                            CharacterID INT NOT NULL,
                                            TotalAmount INT NOT NULL,
                                            WeeklyAmount INT NOT NULL,
                                            PRIMARY KEY (CurrencyName, CharacterID),
                                            FOREIGN KEY (CurrencyName) REFERENCES Currency(CurrencyName)
                                              ON DELETE CASCADE ON UPDATE CASCADE,
                                            FOREIGN KEY (CharacterID) REFERENCES Characters(CharacterID)
                                            ON DELETE CASCADE ON UPDATE CASCADE
                                        );
                """
        );

        cxn.createStatement().executeUpdate("""           
                                    CREATE TABLE Statistics (
                                     StatsName VARCHAR(100) PRIMARY KEY
                                                   );          
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE CharacterStats (
                                        StatsName VARCHAR(100) NOT NULL,
                                        CharacterID INT NOT NULL,
                                        `Value` INT NOT NULL DEFAULT 0,
                                        PRIMARY KEY (StatsName, CharacterID),
                                        FOREIGN KEY (StatsName) REFERENCES Statistics(StatsName)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (CharacterID) REFERENCES Characters(CharacterID)
                                          ON DELETE CASCADE ON UPDATE CASCADE
                                    );
                """
        );


        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Slot (
                                        SlotName VARCHAR(100) PRIMARY KEY
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Item (
                                        ItemID INT AUTO_INCREMENT PRIMARY KEY,
                                        ItemName VARCHAR(255) NOT NULL,
                                        `Level` INT NOT NULL DEFAULT 1,
                                        MaxStackSize INT NOT NULL,
                                        Price DECIMAL(10,2),
                                        RequiredLevel INT
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Gear (
                                        ItemID INT PRIMARY KEY,
                                        SlotName VARCHAR(100) NOT NULL,
                                    -- Deleting an Item should delete its related bonuses
                                    -- When an Item is deleted, any associated bonuses (GearBonus, ConsumableBonus)\s
                                    -- should also be deleted since they are directly linked to that item.
                                        FOREIGN KEY (ItemID) REFERENCES Item(ItemID)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (SlotName) REFERENCES Slot(SlotName)
                                          ON DELETE CASCADE ON UPDATE CASCADE
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Weapon (
                                        ItemID INT PRIMARY KEY,
                                        JobName VARCHAR(100) NOT NULL,
                                        Damage INT NOT NULL,
                                        FOREIGN KEY (ItemID) REFERENCES Item(ItemID)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (JobName) REFERENCES Job(JobName)
                                          ON DELETE CASCADE ON UPDATE CASCADE
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE Consumable (
                                        ItemID INT PRIMARY KEY,
                                        `Description` TEXT NOT NULL,
                                        FOREIGN KEY (ItemID) REFERENCES Item(ItemID)
                                          ON DELETE CASCADE ON UPDATE CASCADE
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE GearBonus (
                                        ItemID INT NOT NULL,
                                        StatsName VARCHAR(100) NOT NULL,
                                        `Value` INT NOT NULL,
                                        PRIMARY KEY (ItemID, StatsName),
                                        FOREIGN KEY (ItemID) REFERENCES Item(ItemID)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (StatsName) REFERENCES Statistics(StatsName)
                                          ON DELETE CASCADE ON UPDATE CASCADE
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE ConsumableBonus (
                                        ItemID INT NOT NULL,
                                        StatsName VARCHAR(100) NOT NULL,
                                        `Value` INT NOT NULL,
                                        BonusCap INT NOT NULL,
                                        BonusPercentage DECIMAL(5,2) NOT NULL,
                                        PRIMARY KEY (ItemID, StatsName),
                                        FOREIGN KEY (ItemID) REFERENCES Consumable(ItemID)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (StatsName) REFERENCES Statistics(StatsName)
                                          ON DELETE CASCADE ON UPDATE CASCADE
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""
                                    CREATE TABLE CharacterEquipment (
                                        CharacterID INT NOT NULL,
                                        SlotName VARCHAR(100) NOT NULL,
                                        ItemID INT,
                                        PRIMARY KEY (CharacterID, SlotName),
                                        FOREIGN KEY (CharacterID) REFERENCES Characters(CharacterID)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (SlotName) REFERENCES Slot(SlotName)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                    -- ON DELETE SET NULL (Set Foreign Key to NULL): If an equipped item is deleted, clear the equipment slot instead of deleting the entire row
                                    -- The CharacterEquipment table stores a character’s currently equipped items. If an Item is deleted,\s
                                    -- we should not delete the entire CharacterEquipment record (which contains important slot data).\s
                                    -- Instead, we just set ItemID to NULL, meaning that the equipment slot is now empty.
                                        FOREIGN KEY (ItemID) REFERENCES Item(ItemID)
                                          ON DELETE SET NULL ON UPDATE CASCADE
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""

                                    CREATE TABLE CharacterInventory (
                                        SlotNumber INT NOT NULL,
                                        CharacterID INT NOT NULL,
                                        ItemID INT,
                                        Quantity INT NOT NULL,
                                        PRIMARY KEY (SlotNumber, CharacterID),
                                        FOREIGN KEY (CharacterID) REFERENCES Characters(CharacterID)
                                          ON DELETE CASCADE ON UPDATE CASCADE,
                                    -- If an item is deleted, update CharacterInventory to reflect that the slot is now empty
                                        FOREIGN KEY (ItemID) REFERENCES Item(ItemID)
                                          ON DELETE SET NULL ON UPDATE CASCADE
                                    );
                """
        );

        cxn.createStatement().executeUpdate("""

                                   INSERT INTO Race (RaceName) VALUES
                                        ('ELF'),
                                        ('ORC'),
                                        ('HUMAN'),
                                        ('DWARF'),
                                        ('GOBLIN'),
                                        ('HALFLING');
                """
        );

        cxn.createStatement().executeUpdate("""
                                    INSERT INTO Job (JobName) VALUES
                                    ('Warrior'), ('Mage'), ('Thief'), ('Hunter'), ('Necromancer');
                """
        );

        cxn.createStatement().executeUpdate("""

                INSERT INTO Statistics (StatsName) VALUES
                ('Strength'), ('Dexterity'), ('Vitality'), ('Intelligence'), ('HP'), ('MP'), ('CriticalRate'), ('EvasionRate');
                """
        );

        cxn.createStatement().executeUpdate("""

                INSERT INTO Slot (SlotName) VALUES
                ('Helmet'), ('Armor'), ('Boots'), ('Gloves'), ('Weapon');
                """
        );


    }
}
}
