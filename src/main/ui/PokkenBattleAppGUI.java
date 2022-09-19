package ui;

import model.Event;
import model.EventLog;
import model.Pokemon;
import model.PokemonBattle;
import model.PokemonTeam;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// CODE & GENERAL REFERENCE:
// JsonSerializationDemo - WorkRoomApp
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// SimpleDrawingPlayer-Complete
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
// JDialog/JOptionPane Oracle Documentation Examples
// https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
// Pokemon Animation GIFs and Main Menu Background
// https://play.pokemonshowdown.com/sprites/xyani-back/
// https://play.pokemonshowdown.com/sprites/ani/
// https://wall.alphacoders.com/big.php?i=1018072

// Represents the graphics user interface of the PokkenBattle application
public class PokkenBattleAppGUI extends JFrame {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    private static final String JSON_STORE = "./data/pokemonteam.json";
    private static final int USER_POKEMON_HEALTH = 100;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private PokemonTeam userTeam;
    private PokemonBattle battle;
    private ArrayList<Pokemon> usablePokemons;
    private ArrayList<Pokemon> enemies;
    private Pokemon charmander;
    private Pokemon bulbasaur;
    private Pokemon squirtle;
    private Pokemon chikorita;
    private Pokemon cyndaquil;
    private Pokemon totodile;
    private Pokemon pidgey;
    private Pokemon rattata;
    private Pokemon pikachu;
    private int charmanderCounter;
    private int bulbasaurCounter;
    private int squirtleCounter;
    private int chikoritaCounter;
    private int cyndaquilCounter;
    private int totodileCounter;
    private List<Button> buttons;
    private BattleWindow battleWindow;
    JLabel currentUserPokemon;
    JLabel currentEnemyPokemon;
    JLabel currentEnemyPokemonInfo;
    JLabel currentUserPokemonInfo;

    // MODIFIES: this
    // EFFECTS: runs the PokkenBattle application and initializes fields
    public PokkenBattleAppGUI() {
        super("Pokken Battle Game");
        initFields();
        initGraphics();
        initCloseButtonTopRight();
    }

    // EFFECTS: represents the handler or behaviour of the close button (x) on top right
    // of the app window
    public void initCloseButtonTopRight() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                EventLog eventLog = EventLog.getInstance();
                for (Event next : eventLog) {
                    System.out.println(next.toString());
                    System.out.println();
                }
                dispose();
            }
        });
    }

    // Represents a custom button with a JButton for interactive purposes
    public abstract class Button {

        protected JButton button;

        // EFFECTS: constructs a button with a listener and associates it with parent
        public Button(JComponent parent) {
            createButton(parent);
            addToParent(parent);
            addListener();
        }

        // EFFECTS: adds a listener for this button
        protected abstract void addListener();

        // MODIFIES: parent
        // EFFECTS: adds the given button to the parent component
        public void addToParent(JComponent parent) {
            parent.add(button);
        }

        // EFFECTS: creates the button to be used for interactions
        protected abstract void createButton(JComponent parent);
    }

    // MODIFIES: this
    // EFFECTS: draws JFrame window where this PokkenBattleAppGUI will function,
    // activates the buttons to be used and play the game
    private void initGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createButtonPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel menuBackground = new JLabel(new ImageIcon("./data/menubg.png"));
        add(menuBackground);
        menuBackground.setBorder(new EmptyBorder(50, 0, 0, 0));
        menuBackground.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: a helper method that declares and initializes all the main menu buttons
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.setSize(new Dimension(0, 0));
        add(buttonPanel, BorderLayout.SOUTH);

        Button battleButton = new BattleButton(buttonPanel);
        buttons.add(battleButton);

        Button viewTeamButton = new ViewTeamButton(buttonPanel);
        buttons.add(viewTeamButton);

        Button viewScoreButton = new ViewScoreButton(buttonPanel);
        buttons.add(viewScoreButton);

        Button addPokemonButton = new AddPokemonButton(buttonPanel);
        buttons.add(addPokemonButton);

        Button removePokemonButton = new RemovePokemonButton(buttonPanel);
        buttons.add(removePokemonButton);

        Button saveTeamButton = new SaveTeamButton(buttonPanel);
        buttons.add(saveTeamButton);

        Button loadTeamButton = new LoadTeamButton(buttonPanel);
        buttons.add(loadTeamButton);

        Button quitButton = new QuitButton(buttonPanel);
        buttons.add(quitButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up the button list, json reader and writer, the players team, and game assets
    // for the pokemons and battle system
    private void initFields() {
        buttons = new ArrayList<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        userTeam = new PokemonTeam("User's Team");
        initPokemons();
        initUsablePokemons();
        initEnemies();
        initDuplicates();
    }

    // MODIFIES: this
    // EFFECTS: sets up the number of a particular pokemon (based on name) that exists
    private void initDuplicates() {
        charmanderCounter = 1;
        bulbasaurCounter = 1;
        squirtleCounter = 1;
        chikoritaCounter = 1;
        cyndaquilCounter = 1;
        totodileCounter = 1;
    }

    // MODIFIES: this
    // EFFECTS: intializes the list of enemy pokemons
    private void initEnemies() {
        enemies = new ArrayList<>();
        enemies.add(pidgey);
        enemies.add(rattata);
        enemies.add(pikachu);
    }

    // MODIFIES: this
    // EFFECTS: initializes the list of usable pokemons
    private void initUsablePokemons() {
        usablePokemons = new ArrayList<>();
        usablePokemons.add(charmander);
        usablePokemons.add(bulbasaur);
        usablePokemons.add(squirtle);
        usablePokemons.add(chikorita);
        usablePokemons.add(cyndaquil);
        usablePokemons.add(totodile);
    }

    // MODIFIES: this
    // EFFECTS: initializes pokemons
    private void initPokemons() {
        charmander = new Pokemon("Charmander", USER_POKEMON_HEALTH);
        bulbasaur = new Pokemon("Bulbasaur", USER_POKEMON_HEALTH);
        squirtle = new Pokemon("Squirtle", USER_POKEMON_HEALTH);
        chikorita = new Pokemon("Chikorita", USER_POKEMON_HEALTH);
        cyndaquil = new Pokemon("Cyndaquil", USER_POKEMON_HEALTH);
        totodile = new Pokemon("Totodile", USER_POKEMON_HEALTH);
        pidgey = new Pokemon("Pidgey", 100);
        rattata = new Pokemon("Rattata", 200);
        pikachu = new Pokemon("Pikachu", 300);
    }

    // Represents a score viewing button with a JButton
    private class ViewScoreButton extends Button {

        // MODIFIES: this
        // EFFECTS: constructs a standard button by calling super and passing in its parent
        public ViewScoreButton(JComponent parent) {
            super(parent);
        }

        // MODIFIES: this
        // EFFECTS: constructs a new listener object with its handler which is added to the JButton
        @Override
        protected void addListener() {
            button.addActionListener(new ViewScoreButtonClickHandler());
        }

        // MODIFIES: this
        // EFFECTS: constructs a view score button which is added to the
        // JComponent (parent) which is passed in as a parameter
        @Override
        protected void createButton(JComponent parent) {
            button = new JButton("View Score");
            addToParent(parent);
        }

        // Represents the handler or behaviour of the ViewScoreButton
        private class ViewScoreButtonClickHandler implements ActionListener {

            // EFFECTS: displays a pane with number of wins, losses, and ties
            // when the button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, printScores(), "Score",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // EFFECTS: creates a string in the proper display format with the number
            // of wins, losses, and ties then returns it
            private String printScores() {
                String wins = "Wins: " + userTeam.getNumberOfWins();
                String losses = "Losses: " + userTeam.getNumberOfLosses();
                String ties = "Ties: " + userTeam.getNumberOfTies();
                return wins + "\n" + losses + "\n" + ties;
            }
        }
    }

    // Represents a quit button with a JButton
    private class QuitButton extends Button {

        // MODIFIES: this
        // EFFECTS: constructs a standard button by calling super and passing in its parent
        public QuitButton(JComponent parent) {
            super(parent);
        }

        // MODIFIES: this
        // EFFECTS: constructs a new listener object with its handler which is added to the JButton
        @Override
        protected void addListener() {
            button.addActionListener(new QuitButtonClickHandler());
        }

        // MODIFIES: this
        // EFFECTS: constructs a quit button which is added to the JComponent (parent)
        // which is passed in as a parameter
        @Override
        protected void createButton(JComponent parent) {
            button = new JButton("Quit");
            addToParent(parent);
        }

        // Represents the handler or behaviour of the ViewScoreButton
        private class QuitButtonClickHandler implements ActionListener {

            // EFFECTS: quits the game by closing the application when the button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                EventLog eventLog = EventLog.getInstance();
                for (Event next : eventLog) {
                    System.out.println(next.toString());
                    System.out.println();
                }
                System.exit(0);
            }
        }
    }

    // Represents a pokemon adding button with a JButton
    private class AddPokemonButton extends Button {

        // MODIFIES: this
        // EFFECTS: constructs a standard button by calling super and passing in its parent
        public AddPokemonButton(JComponent parent) {
            super(parent);
        }

        // MODIFIES: this
        // EFFECTS: constructs a new listener object with its handler which is added to the JButton
        @Override
        protected void addListener() {
            button.addActionListener(new AddPokemonToTeamButtonClickHandler());
        }

        // MODIFIES: this
        // EFFECTS: constructs a pokemon adding button which is added to the
        // JComponent (parent) which is passed in as a parameter
        @Override
        protected void createButton(JComponent parent) {
            button = new JButton("Add Pokemon");
            addToParent(parent);
        }

        // Represents the handler or behaviour of the AddPokemonButton
        private class AddPokemonToTeamButtonClickHandler implements ActionListener {

            // MODIFIES: this
            // EFFECTS: displays a pane that prompts the user to choose from a list of pokemons to add
            // to their team when the button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Charmander", "Bulbasaur", "Squirtle", "Chikorita", "Cyndaquil", "Totodile"};
                String input = (String) JOptionPane.showInputDialog(null,
                        "Which Pokemon would you like to add?", "Add Pokemon",
                        JOptionPane.QUESTION_MESSAGE, null, options, "");
                for (Pokemon next : usablePokemons) {
                    if (next.getName().equals(input)) {
                        if (userTeam.getAllPokemonNames().contains(input)) {
                            userTeam.addPokemon(new Pokemon(next.getName()
                                    + " " + duplicateCounter(input), USER_POKEMON_HEALTH));
                        } else {
                            userTeam.addPokemon(new Pokemon(next.getName(), USER_POKEMON_HEALTH));
                        }
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds 1 to the counter to a pokemon of the same name and returns
    // the number to represent that latest iteration
    private int duplicateCounter(String name) {
        if (name.contains("Charmander")) {
            charmanderCounter++;
            return charmanderCounter;
        } else if (name.contains("Bulbasaur")) {
            bulbasaurCounter++;
            return bulbasaurCounter;
        } else if (name.contains("Squirtle")) {
            squirtleCounter++;
            return squirtleCounter;
        } else if (name.contains("Chikorita")) {
            chikoritaCounter++;
            return chikoritaCounter;
        } else if (name.contains("Cyndaquil")) {
            cyndaquilCounter++;
            return cyndaquilCounter;
        } else {
            totodileCounter++;
            return totodileCounter;
        }
    }

    // Represents a view team button with a JButton
    private class ViewTeamButton extends Button {

        // MODIFIES: this
        // EFFECTS: constructs a standard button by calling super and passing in its parent
        public ViewTeamButton(JComponent parent) {
            super(parent);
        }

        // MODIFIES: this
        // EFFECTS: constructs a new listener object with its handler which is added to the JButton
        @Override
        protected void addListener() {
            button.addActionListener(new ViewTeamButtonClickHandler());
        }

        // MODIFIES: this
        // EFFECTS: constructs a view team button which is added to the
        // JComponent which is passed in as a parameter
        @Override
        protected void createButton(JComponent parent) {
            button = new JButton("View Team");
            addToParent(parent);
        }

        // Represents the handler or behaviour of the ViewTeamButton
        private class ViewTeamButtonClickHandler implements ActionListener {

            // EFFECTS: displays a pane that has the names of all the pokemons in the players team
            // current when the button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userTeam.getListOfPokemons().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "You don't have any pokemons on your team to view.", "View Team",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, printPokemonNames(), "View Team",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }

            // EFFECTS: creates a string in the proper display format with all the pokemon names
            // in the players team
            private String printPokemonNames() {
                StringBuilder allNames = new StringBuilder();
                allNames.append("Your Team:" + "\n");
                for (Pokemon p : userTeam.getListOfPokemons()) {
                    allNames.append(p.getName()).append("\n");
                }
                return allNames.toString();
            }
        }
    }

    // Represents a pokemon remover button with a JButton
    private class RemovePokemonButton extends Button {

        // MODIFIES: this
        // EFFECTS: constructs a standard button by calling super and passing in its parent
        public RemovePokemonButton(JComponent parent) {
            super(parent);
        }

        // MODIFIES: this
        // EFFECTS: constructs a new listener object with its handler which is added to the JButton
        @Override
        protected void addListener() {
            button.addActionListener(new RemovePokemonFromTeamButtonClickHandler());
        }

        // MODIFIES: this
        // EFFECTS: constructs a remove pokemon button which is added to the
        // JComponent (parent) which is passed in as a parameter
        @Override
        protected void createButton(JComponent parent) {
            button = new JButton("Remove Pokemon");
            addToParent(parent);
        }

        // Represents the handler or behaviour of the RemovePokemonButton
        private class RemovePokemonFromTeamButtonClickHandler implements ActionListener {

            // MODIFIES: this
            // EFFECTS: displays a pane with a dropdown menu to choose which pokemon
            // from the players team to remove when the button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userTeam.getListOfPokemons().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "You don't have any pokemons on your team to remove.", "Remove Pokemon",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    Object[] options = userTeam.getAllPokemonNames().toArray();
                    String input = (String) JOptionPane.showInputDialog(null,
                            "Select a pokemon to remove from your team", "Remove Pokemon",
                            JOptionPane.QUESTION_MESSAGE, null, options, "");
                    for (int i = 0; i < userTeam.getListOfPokemons().size(); i++) {
                        if (userTeam.getListOfPokemons().get(i).getName().equals(input)) {
                            userTeam.removePokemon(userTeam.getListOfPokemons().get(i));
                        }
                    }
                }
            }
        }
    }

    // Represents a save button with a JButton
    private class SaveTeamButton extends Button {

        // MODIFIES: this
        // EFFECTS: constructs a standard button by calling super and passing in its parent
        public SaveTeamButton(JComponent parent) {
            super(parent);
        }

        // MODIFIES: this
        // EFFECTS: constructs a new listener object with its handler which is added to the JButton
        @Override
        protected void addListener() {
            button.addActionListener(new SaveTeamButtonClickHandler());
        }

        // MODIFIES: this
        // EFFECTS: constructs a save team button which is added to the
        // JComponent (parent) which is passed in as a parameter
        @Override
        protected void createButton(JComponent parent) {
            button = new JButton("Save Team");
            addToParent(parent);
        }

        // Represents the handler or behaviour of the SaveTeamButton
        private class SaveTeamButtonClickHandler implements ActionListener {

            // MODIFIES: json save file (pokemonteam.json)
            // EFFECTS: saves the pokemon team along with the scores to a json file
            // then displays a pane that tells the user where it was saved when the
            // button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.openWriter();
                    jsonWriter.write(userTeam);
                    jsonWriter.closeWriter();
                    JOptionPane.showMessageDialog(null,
                            "Saved your pokemon team to " + JSON_STORE,
                            "Save Team", JOptionPane.INFORMATION_MESSAGE);
                } catch (FileNotFoundException exception) {
                    System.out.println("Unable to save/write to file: " + JSON_STORE);
                    JOptionPane.showMessageDialog(null,
                            "Unable to save/write to file: " + JSON_STORE,
                            "Save Team", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    // Represents a load team button with a JButton
    private class LoadTeamButton extends Button {

        // MODIFIES: this
        // EFFECTS: constructs a standard button by calling super and passing in its parent
        public LoadTeamButton(JComponent parent) {
            super(parent);
        }

        // MODIFIES: this
        // EFFECTS: constructs a new listener object with its handler which is added to the JButton
        @Override
        protected void addListener() {
            button.addActionListener(new LoadTeamButton.LoadTeamButtonClickHandler());
        }

        // MODIFIES: this
        // EFFECTS: constructs a load team button which is added to the
        // JComponent (parent) which is passed in as a parameter
        @Override
        protected void createButton(JComponent parent) {
            button = new JButton("Load Team");
            addToParent(parent);
        }

        // Represents the handler or behaviour of the LoadTeamButton
        private class LoadTeamButtonClickHandler implements ActionListener {

            // MODIFIES: json save file (pokemonteam.json)
            // EFFECTS: loads the pokemon team along with the scores from a json file
            // then displays a pane that tells the user that their save has been loaded
            // when the button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userTeam = jsonReader.read();
                    JOptionPane.showMessageDialog(null,
                            "Loaded your pokemon team from " + JSON_STORE,
                            "Load Team", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException exceptions) {
                    JOptionPane.showMessageDialog(null,
                            "Unable to load/read from file: " + JSON_STORE,
                            "Load Team", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    // Represents the battle initiation button with a JButton
    private class BattleButton extends Button {

        // MODIFIES: this
        // EFFECTS: constructs a standard button by calling super and passing in its parent
        public BattleButton(JComponent parent) {
            super(parent);
        }

        // MODIFIES: this
        // EFFECTS: constructs a new listener object with its handler which is added to the JButton
        @Override
        protected void addListener() {
            button.addActionListener(new BattleButtonClickHandler());
        }

        // MODIFIES: this
        // EFFECTS: constructs a battle button which is added to the
        // JComponent (parent) which is passed in as a parameter
        @Override
        protected void createButton(JComponent parent) {
            button = new JButton("Battle");
            addToParent(parent);
        }

        // Represents the handler or behaviour of the BattleButton
        private class BattleButtonClickHandler implements ActionListener {

            // MODIFIES: this
            // EFFECTS: prompts the player from a few battle options from a display pane
            // and starts the pokemon battle when the button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                battleSetup();
            }

            // MODIFIES: this
            // EFFECTS: sets up the pokemon battle by letting the user choose who to send out first
            // and what enemy to battle
            private void battleSetup() {
                if (userTeam.getListOfPokemons().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "You must have at least 1 pokemon on your team first before battling.",
                            "Battle", JOptionPane.WARNING_MESSAGE);
                } else {
                    Object[] options = userTeam.getAllPokemonNames().toArray();
                    String input = (String) JOptionPane.showInputDialog(null,
                            "Which pokemon would you like to send out first?", "Choose Pokemon",
                            JOptionPane.QUESTION_MESSAGE, null, options, "");
                    for (Pokemon next : userTeam.getListOfPokemons()) {
                        if (next.getName().equals(input)) {
                            Pokemon enemyPokemon = chooseEnemy();
                            isGameSetup(enemyPokemon, next);
                            break;
                        }
                    }
                }
            }

            // REQUIRES: next is not null
            // MODIFIES: this
            // EFFECTS: determines if the game should start, if yes then start game else close battle setup
            private void isGameSetup(Pokemon enemyPokemon, Pokemon next) {
                if (enemyPokemon == null) {
                    JOptionPane.showMessageDialog(null,
                            "You did not choose a pokemon to battle.",
                            "Battle", JOptionPane.WARNING_MESSAGE);
                } else {
                    battle = new PokemonBattle(next, enemyPokemon);
                    startBattle();
                }
            }

            // EFFECTS: prompts the player to choose from the list of enemy pokemons
            // which one to battle
            private Pokemon chooseEnemy() {
                List<String> enemyNames = new ArrayList<>();
                for (Pokemon e : enemies) {
                    enemyNames.add(e.getName());
                }
                Object[] options = enemyNames.toArray();
                String input = (String) JOptionPane.showInputDialog(null,
                        "Which pokemon would you like to battle?", "Choose Enemy",
                        JOptionPane.QUESTION_MESSAGE, null, options, "");
                for (Pokemon next : enemies) {
                    if (next.getName().equals(input)) {
                        return next;
                    }
                }
                return null;
            }

            // MODIFIES: this
            // EFFECTS: starts the pokemon battle by initialising the battle window
            private void startBattle() {
                battleWindow = new BattleWindow();
            }
        }
    }

    // Represents the battle window with its own specified GUI
    private class BattleWindow extends JFrame {

        // MODIFIES: this
        // EFFECTS: constructs the battle window by calling super and initialising
        // all the necessary graphics
        public BattleWindow() {
            super("Battle");
            initBattleGraphics();
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    EventLog eventLog = EventLog.getInstance();
                    for (Event next : eventLog) {
                        System.out.println(next.toString());
                        System.out.println();
                    }
                    System.exit(0);
                }
            });
        }

        // MODIFIES: this
        // EFFECTS: initializes all the battle graphics including the frame settings
        // and the available buttons
        private void initBattleGraphics() {
            this.setLayout(new FlowLayout());
            this.setMinimumSize(new Dimension(600, 360));
            createBattleButtons();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            initUserPokemonGraphics();
            initEnemyPokemonGraphics();
        }

        // MODIFIES: this
        // EFFECTS: initializes the visual component of the players on field pokemon
        // along with its name and health
        private void initUserPokemonGraphics() {
            currentUserPokemon = new JLabel(new ImageIcon(chooseSpriteUserPokemon()));
            add(currentUserPokemon);
            currentUserPokemon.setBorder(new EmptyBorder(150, 75, 0, 0));
            currentUserPokemon.setVisible(true);

            currentUserPokemonInfo = new JLabel();
            updateUserPokemonInfo();
            add(currentUserPokemonInfo);
            currentUserPokemonInfo.setBorder(new EmptyBorder(200, 0, 0, 75));
            currentUserPokemon.setVisible(true);
        }

        // EFFECTS: returns the path to the image of the players on field pokemon based on
        // what was chosen from the beginning of the battle or from the switch pokemon menu
        private String chooseSpriteUserPokemon() {
            if (battle.getChosenPokemonOnField().getName().contains("Charmander")) {
                return "./data/charmanderback.gif";
            } else if (battle.getChosenPokemonOnField().getName().contains("Bulbasaur")) {
                return "./data/bulbasaurback.gif";
            } else if (battle.getChosenPokemonOnField().getName().contains("Squirtle")) {
                return "./data/squirtleback.gif";
            } else if (battle.getChosenPokemonOnField().getName().contains("Chikorita")) {
                return "./data/chikoritaback.gif";
            } else if (battle.getChosenPokemonOnField().getName().contains("Cyndaquil")) {
                return "./data/cyndaquilback.gif";
            } else {
                return "./data/totodileback.gif";
            }
        }

        // MODIFIES: this
        // EFFECTS: initializes the visual component of the enemy pokemon along with
        // its name and health
        private void initEnemyPokemonGraphics() {
            currentEnemyPokemon = new JLabel(new ImageIcon(chooseSpriteEnemyPokemon()));
            add(currentEnemyPokemon);
            currentEnemyPokemon.setBorder(new EmptyBorder(50, 15, 0, 0));
            currentEnemyPokemon.setVisible(true);

            currentEnemyPokemonInfo = new JLabel();
            updateEnemyInfo();
            add(currentEnemyPokemonInfo);
            currentEnemyPokemonInfo.setBorder(new EmptyBorder(0, 0, 0, 15));
            currentEnemyPokemonInfo.setVisible(true);
        }

        // EFFECTS: determines the maximum health an enemy pokemon can have then returns it
        private int findMaxHealth() {
            if (battle.getEnemyPokemon().getName().equals("Pidgey")) {
                return 100;
            } else if (battle.getEnemyPokemon().getName().equals("Rattata")) {
                return 200;
            } else {
                return 300;
            }
        }

        // EFFECTS: returns the path to the image of the enemy pokemon based on
        // what the player chose to battle at the start of the battle setup
        private String chooseSpriteEnemyPokemon() {
            if (battle.getEnemyPokemon().getName().contains("Pidgey")) {
                return "./data/pidgey.gif";
            } else if (battle.getEnemyPokemon().getName().contains("Rattata")) {
                return "./data/rattata.gif";
            } else {
                return "./data/pikachu.gif";
            }
        }

        // MODIFIES: this
        // EFFECTS: updates the players current on field pokemon in terms of
        // its current health and name
        private void updateUserPokemonInfo() {
            currentUserPokemonInfo.setText("<html>" + battle.getChosenPokemonOnField().getName()
                    + "<br>" + battle.getChosenPokemonOnField().getHealth() + "/" + "100 HP" + "<html>");
        }

        // MODIFIES: this
        // EFFECTS: updates the enemy pokemon that is on field in terms of
        // its current health
        private void updateEnemyInfo() {
            currentEnemyPokemonInfo.setText("<html>" + battle.getEnemyPokemon().getName()
                    + "<br>" + battle.getEnemyPokemon().getHealth() + "/" + findMaxHealth() + " HP" + "<html>");
        }

        // MODIFIES: this
        // EFFECTS: a helper method that declares and instantiates all the battle menu buttons
        private void createBattleButtons() {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(0, 1));
            buttonPanel.setSize(new Dimension(0, 0));
            add(buttonPanel, BorderLayout.SOUTH);

            Button attackButton = new AttackButton(buttonPanel);
            buttons.add(attackButton);

            Button switchButton = new SwitchButton(buttonPanel);
            buttons.add(switchButton);

            Button runButton = new RunButton(buttonPanel);
            buttons.add(runButton);
        }

        // Represents the attack button during the battle phase with a JButton
        private class AttackButton extends Button {

            // MODIFIES: this
            // EFFECTS: constructs a standard button by calling super and passing in its parent
            public AttackButton(JComponent parent) {
                super(parent);
            }

            // MODIFIES: this
            // EFFECTS: constructs a new listener object with its handler which is added to the JButton
            @Override
            protected void addListener() {
                button.addActionListener(new AttackButtonClickHandler());
            }

            // MODIFIES: this
            // EFFECTS: constructs an attack button which is added to the
            // JComponent (parent) which is passed in as a parameter
            @Override
            protected void createButton(JComponent parent) {
                button = new JButton("Attack");
                addToParent(parent);
            }

            // Represents the handler or behaviour of the AttackButton
            private class AttackButtonClickHandler implements ActionListener {

                // MODIFIES: this
                // EFFECTS: performs an attack on enemy pokemon and the enemy pokemon attacks back,
                // and updates the screen info on health and name, and checks when the battle is over
                // when the button is pressed
                @Override
                public void actionPerformed(ActionEvent e) {
                    battle.attackEnemyPokemon();
                    battle.enemyAttackUser();
                    updateUserPokemonInfo();
                    updateEnemyInfo();
                    isBattleOver();
                }
            }

            // MODIFIES: this
            // EFFECTS: determines when the battle is over, and manages score
            private void isBattleOver() {
                Pokemon userPokemon = battle.getChosenPokemonOnField();
                Pokemon enemyPokemon = battle.getEnemyPokemon();
                if (userPokemon.getHealth() == 0 || enemyPokemon.getHealth() == 0) {
                    if (userPokemon.getHealth() == 0 && enemyPokemon.getHealth() == 0) {
                        userTeam.addOneToTies();
                        JOptionPane.showMessageDialog(null,
                                "You have tied the battle.",
                                "Tie", JOptionPane.INFORMATION_MESSAGE);
                    } else if (userPokemon.getHealth() == 0) {
                        userTeam.addOneToLosses();
                        JOptionPane.showMessageDialog(null,
                                "You have lost the battle.",
                                "Loss", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        userTeam.addOneToWins();
                        JOptionPane.showMessageDialog(null,
                                "Congratulations! You have won the battle!",
                                "Win", JOptionPane.INFORMATION_MESSAGE);
                    }
                    resetPokemonHealth();
                    battleWindow.dispose();
                }
            }
        }

        // MODIFIES: this
        // EFFECTS: resets the health of every pokemon back to its starting value
        private void resetPokemonHealth() {
            for (Pokemon next : userTeam.getListOfPokemons()) {
                next.setHealth(USER_POKEMON_HEALTH);
            }
            enemies.get(0).setHealth(100);
            enemies.get(1).setHealth(200);
            enemies.get(2).setHealth(300);
        }

        // Represents a pokemon switching button during battle with a JButton
        private class SwitchButton extends Button {

            // MODIFIES: this
            // EFFECTS: constructs a standard button by calling super and passing in its parent
            public SwitchButton(JComponent parent) {
                super(parent);
            }

            // MODIFIES: this
            // EFFECTS: constructs a new listener object with its handler which is added to the JButton
            @Override
            protected void addListener() {
                button.addActionListener(new SwitchButtonClickHandler());
            }

            // MODIFIES: this
            // EFFECTS: constructs a switch pokemon button which is added to the
            // JComponent (parent) which is passed in as a parameter
            @Override
            protected void createButton(JComponent parent) {
                button = new JButton("Switch");
                addToParent(parent);
            }

            // Represents the handler or behaviour of the SwitchButton
            private class SwitchButtonClickHandler implements ActionListener {

                // MODIFIES: this
                // EFFECTS: displays a pane that prompts the user an option to swap the current on field
                // pokemon with a different one from the team when the button is clicked
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object[] options = userTeam.getAllPokemonNames().toArray();
                    String input = (String) JOptionPane.showInputDialog(null,
                            "Which pokemon would you like to switch to?", "Switch Pokemon",
                            JOptionPane.QUESTION_MESSAGE, null, options, "");
                    for (Pokemon next : userTeam.getListOfPokemons()) {
                        if (next.getName().equals(input)) {
                            battle.setChosenPokemonOnField(next);
                            updateUserPokemonInfo();
                            updateEnemyInfo();
                            currentUserPokemon.setIcon(new ImageIcon(chooseSpriteUserPokemon()));
                            currentEnemyPokemon.setIcon(new ImageIcon(chooseSpriteEnemyPokemon()));
                        }
                    }
                }
            }
        }

        // Represents the run button during battle with a JButton
        private class RunButton extends Button {

            // MODIFIES: this
            // EFFECTS: constructs a standard button by calling super and passing in its parent
            public RunButton(JComponent parent) {
                super(parent);
            }

            // MODIFIES: this
            // EFFECTS: constructs a new listener object with its handler which is added to the JButton
            @Override
            protected void addListener() {
                button.addActionListener(new RunButtonClickHandler());
            }

            // MODIFIES: this
            // EFFECTS: constructs a run button which is added to the
            // JComponent (parent) which is passed in as a parameter
            @Override
            protected void createButton(JComponent parent) {
                button = new JButton("Run");
                addToParent(parent);
            }

            // Represents the handler or behaviour of the RunButton
            private class RunButtonClickHandler implements ActionListener {

                // EFFECTS: displays a message the player has ran away from battle
                // and closes the battle window
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetPokemonHealth();
                    JOptionPane.showMessageDialog(null, "You have ran away from the battle",
                            "Run", JOptionPane.INFORMATION_MESSAGE);
                    battleWindow.dispose();
                }
            }
        }
    }
}