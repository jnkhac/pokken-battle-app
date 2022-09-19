package ui;

import model.Pokemon;
import model.PokemonBattle;
import model.PokemonTeam;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// CODE REFERENCES:
// AccountNotRobust - TellerApp
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
// JsonSerializationDemo - WorkRoomApp
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents the PokkenBattle application
public class PokkenBattleApp {
    private static final String JSON_STORE = "./data/pokemonteam.json";
    private Scanner userInput;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean gameStatus;
    private boolean battleStatus;
    private PokemonTeam team;
    private PokemonBattle battle;
    private ArrayList<Pokemon> usablePokemons;
    private ArrayList<Pokemon> enemies;
    private int userPokemonHealth;
    private Pokemon charmander;
    private Pokemon bulbasaur;
    private Pokemon squirtle;
    private Pokemon chikorita;
    private Pokemon cyndaquil;
    private Pokemon totodile;
    private Pokemon pidgey;
    private Pokemon rattata;
    private Pokemon pikachu;

    // MODIFIES: this
    // EFFECTS: runs the PokkenBattle application and initializes fields
    public PokkenBattleApp() {
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        gameStatus = true;
        battleStatus = false;
        team = new PokemonTeam("User's Team");
        userPokemonHealth = 100;
        initPokemons();
        initUsablePokemons();
        initEnemies();
        runGame();
    }
    // REFERENCE: userInput.useDelimiter("\n"); taken from TellerApp

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
        charmander = new Pokemon("Charmander", userPokemonHealth);
        bulbasaur = new Pokemon("Bulbasaur", userPokemonHealth);
        squirtle = new Pokemon("Squirtle", userPokemonHealth);
        chikorita = new Pokemon("Chikorita", userPokemonHealth);
        cyndaquil = new Pokemon("Cyndaquil", userPokemonHealth);
        totodile = new Pokemon("Totodile", userPokemonHealth);
        pidgey = new Pokemon("Pidgey", 100);
        rattata = new Pokemon("Rattata", 200);
        pikachu = new Pokemon("Pikachu", 300);
    }

    // REQUIRES: given input from the user for givenInput must be of type String
    // MODIFIES: this
    // EFFECTS: reads user inputs for main menu
    private void runGame() {
        while (gameStatus) {
            mainMenu();
            String givenInput = userInput.next();
            if (givenInput.equals("Q")) {
                gameStatus = false;
            } else {
                readMenuInput(givenInput);
            }
        }
        System.out.println("Thanks for playing!");
    }
    // REFERENCE: formatting and methods of runGame was inspired by runTeller() from TellerApp

    // REQUIRES: given givenInput is not null
    // MODIFIES: this
    // EFFECTS: reads the given user inputs for main menu
    private void readMenuInput(String givenInput) {
        if (givenInput.equals("B")) {
            battleSetup();
        } else if (givenInput.equals("T")) {
            startViewTeam();
        } else if (givenInput.equals("A")) {
            startAddPokemon();
        } else if (givenInput.equals("R")) {
            startRemovePokemon();
        } else if (givenInput.equals("V")) {
            startScore();
        } else if (givenInput.equals("S")) {
            startSave();
        } else if (givenInput.equals("L")) {
            startLoad();
        } else {
            System.out.println("That is not a valid option.");
        }
    }
    // REFERENCE: readMenuInput method was inspired by processCommand() from TellerApp

    // MODIFIES: json save file
    // EFFECTS: saves the pokemon team along with the scores to a json file
    private void startSave() {
        try {
            jsonWriter.openWriter();
            jsonWriter.write(team);
            jsonWriter.closeWriter();
            System.out.println("Saved your pokemon team to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save/write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the pokemon team along with the scores from json file
    private void startLoad() {
        try {
            team = jsonReader.read();
            System.out.println("Loaded your pokemon team from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load/read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints the number of wins, losses, and ties in battle
    private void startScore() {
        System.out.println("Wins: " + team.getNumberOfWins());
        System.out.println("Losses: " + team.getNumberOfLosses());
        System.out.println("Ties: " + team.getNumberOfTies());
    }

    // REQUIRES: given input from the user for choiceNumber must be of type int
    // MODIFIES: this
    // EFFECTS: removes a pokemon from the team
    private void startRemovePokemon() {
        if (team.getListOfPokemons().isEmpty()) {
            System.out.println("You don't have any pokemons on your team to remove.");
        } else {
            System.out.println("Which Pokemon would you like to remove?");
            for (int i = 0; i < team.getListOfPokemons().size(); i++) {
                System.out.println("\t" + i + ") " + team.getListOfPokemons().get(i).getName());
            }
            System.out.println();
            int choiceNumber = userInput.nextInt();
            if ((0 <= choiceNumber) && (choiceNumber < team.getListOfPokemons().size())) {
                System.out.println(team.getListOfPokemons().get(choiceNumber).getName() + " has been removed.");
                team.removePokemon(team.getListOfPokemons().get(choiceNumber));
            } else {
                System.out.println("That is not a valid option.");
            }
        }
    }

    // REQUIRES: given input from the user for choiceNumber must be of type int
    // MODIFIES: this
    // EFFECTS: adds a pokemon from the list of usable pokemons to the team
    private void startAddPokemon() {
        System.out.println("Which Pokemon would you like to add?");
        for (int i = 0; i < usablePokemons.size(); i++) {
            System.out.println("\t" + i + ") " + usablePokemons.get(i).getName());
        }
        System.out.println();
        int choiceNumber = userInput.nextInt();
        if ((0 <= choiceNumber) && (choiceNumber < usablePokemons.size())) {
            team.addPokemon(new Pokemon(usablePokemons.get(choiceNumber).getName(), userPokemonHealth));
            System.out.println(usablePokemons.get(choiceNumber).getName() + " has been added.");
        } else {
            System.out.println("That is not a valid option.");
        }
    }

    // EFFECTS: prints out the names of every pokemon on the team
    private void startViewTeam() {
        ArrayList<String> pokemonNames = team.getAllPokemonNames();
        if (pokemonNames.isEmpty()) {
            System.out.println("You don't have any pokemons on your team.");
        } else {
            System.out.println("Current Team:");
            for (int i = 0; i < team.getListOfPokemons().size(); i++) {
                System.out.println("\t" + i + ") " + team.getListOfPokemons().get(i).getName());
            }
        }
    }

    // REQUIRES: given input from the user for choiceNumber must be of type int
    // MODIFIES: this
    // EFFECTS: reads the given user input to start a pokemon battle
    private void battleSetup() {
        if (team.getListOfPokemons().isEmpty()) {
            System.out.println("You must have at least 1 pokemon on your team first.");
        } else {
            System.out.println("Which pokemon would you like to send out first?");
            for (int i = 0; i < team.getListOfPokemons().size(); i++) {
                System.out.println("\t" + i + ") " + team.getListOfPokemons().get(i).getName());
            }
            System.out.println();
            int choiceNumber = userInput.nextInt();
            if ((0 <= choiceNumber) && (choiceNumber < team.getListOfPokemons().size())) {
                Pokemon enemyPokemon = chooseEnemy();
                battle = new PokemonBattle(team.getListOfPokemons().get(choiceNumber), enemyPokemon);
                System.out.println(team.getListOfPokemons().get(choiceNumber).getName() + " has been chosen.");
                startBattle(battle);
            } else {
                System.out.println("That is not a valid option.");
            }
        }
    }

    // REQUIRES: given input from the user for choiceNumber must be of type int
    // EFFECTS: prompts the user for options of enemies and returns the chosen one
    private Pokemon chooseEnemy() {
        System.out.println("Which pokemon would you like to battle?");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println("\t" + i + ") " + enemies.get(i).getName());
        }
        int choiceNumber = userInput.nextInt();
        Pokemon chosenEnemy = null;
        if ((0 <= choiceNumber) && (choiceNumber < enemies.size())) {
            chosenEnemy = enemies.get(choiceNumber);
        }
        return chosenEnemy;
    }

    // REQUIRES: given battle must not be null
    // MODIFIES: this
    // EFFECTS: starts the pokemon battle and keeps it running
    private void startBattle(PokemonBattle battle) {
        battleStatus = true;
        while (battleStatus) {
            Pokemon userPokemon = battle.getChosenPokemonOnField();
            Pokemon enemyPokemon = battle.getEnemyPokemon();
            battleMenu(userPokemon, enemyPokemon);
            isBattleOver(userPokemon, enemyPokemon);
        }
    }

    // REQUIRES: given userPokemon and enemyPokemon are both not null
    //           given input from the user for givenInput must be of type String
    // MODIFIES: this
    // EFFECTS: reads the given user input, determines when the battle is over, and manages score
    private void isBattleOver(Pokemon userPokemon, Pokemon enemyPokemon) {
        if (userPokemon.getHealth() == 0 || enemyPokemon.getHealth() == 0) {
            if (userPokemon.getHealth() == 0 && enemyPokemon.getHealth() == 0) {
                team.addOneToTies();
                System.out.println(userPokemon.getName() + " has fainted.");
                System.out.println(enemyPokemon.getName() + " has fainted.");
                System.out.println("You have tied the battle.");
            } else if (userPokemon.getHealth() == 0) {
                team.addOneToLosses();
                System.out.println(userPokemon.getName() + " has fainted.");
                System.out.println("You have lost the battle.");
            } else {
                team.addOneToWins();
                System.out.println(enemyPokemon.getName() + " has fainted.");
                System.out.println("Congratulations! You have won the battle!");
            }
            battleStatus = false;
            resetPokemonHealth();
        } else {
            String givenInput = userInput.next();
            readBattleInput(givenInput);
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the health of every pokemon back to its starting value
    private void resetPokemonHealth() {
        for (Pokemon next : team.getListOfPokemons()) {
            next.setHealth(userPokemonHealth);
        }
        enemies.get(0).setHealth(100);
        enemies.get(1).setHealth(200);
        enemies.get(2).setHealth(300);
    }

    // REQUIRES: given givenInput is not null
    // MODIFIES: this
    // EFFECTS: reads the given user input for the battle menu
    private void readBattleInput(String givenInput) {
        Pokemon userPokemon = battle.getChosenPokemonOnField();
        Pokemon enemyPokemon = battle.getEnemyPokemon();
        if (givenInput.equals("A")) {
            doAttack(userPokemon, enemyPokemon);
        } else if (givenInput.equals("S")) {
            doSwitchPokemon();
        } else if (givenInput.equals("R")) {
            doRunFromBattle();
        } else {
            System.out.println("That is not a valid option.");
        }
    }
    // REFERENCE: readBattleInput method was inspired by processCommand() from TellerApp

    // MODIFIES: this
    // EFFECTS: ends the pokemon battle by "running away"
    private void doRunFromBattle() {
        System.out.println("You ran away from the battle.");
        battleStatus = false;
        resetPokemonHealth();
    }

    // REQUIRES: given input from the user for choiceNumber must be of type int
    // MODIFIES: this
    // EFFECTS: reads the given user input and switches the pokemon that is on field
    private void doSwitchPokemon() {
        System.out.println("Which pokemon would you like to switch to?");
        for (int i = 0; i < team.getListOfPokemons().size(); i++) {
            System.out.println("\t" + i + ") " + team.getListOfPokemons().get(i).getName());
        }
        System.out.println();
        int choiceNumber = userInput.nextInt();
        if ((0 <= choiceNumber) && (choiceNumber < team.getListOfPokemons().size())) {
            battle.setChosenPokemonOnField(team.getListOfPokemons().get(choiceNumber));
            System.out.println("Go " + team.getListOfPokemons().get(choiceNumber).getName() + "!");
        } else {
            System.out.println("That is not a valid option.");
        }
    }

    // REQUIRES: given userPokemon and enemyPokemon are both not null
    // MODIFIES: this
    // EFFECTS: performs an attack on enemy pokemon and the enemy pokemon attacks back
    private void doAttack(Pokemon userPokemon, Pokemon enemyPokemon) {
        System.out.println(userPokemon.getName() + " used " + userPokemon.getTackle().getAttackMoveName() + ".");
        battle.attackEnemyPokemon();
        System.out.println(enemyPokemon.getName() + " used " + enemyPokemon.getTackle().getAttackMoveName() + ".");
        battle.enemyAttackUser();
    }

    // REQUIRES: given userPokemon and enemyPokemon are both not null
    // EFFECTS: prints the battle menu options for the user
    private void battleMenu(Pokemon userPokemon, Pokemon enemyPokemon) {
        horizontalLine();
        System.out.println("Battle Menu:\n");
        System.out.println(userPokemon.getName() + " HP: " + userPokemon.getHealth());
        System.out.println(enemyPokemon.getName() + " HP: " + enemyPokemon.getHealth() + "\n");
        System.out.println("Choose from:");
        System.out.println("\tA) Attack");
        System.out.println("\tS) Switch Pokemon");
        System.out.println("\tR) Run\n");
    }
    // REFERENCE: battleMenu method was inspired by displayMenu() from TellerApp


    // EFFECTS: prints the main menu options for the user
    private void mainMenu() {
        horizontalLine();
        System.out.println("Main Menu:\n");
        System.out.println("Choose from:");
        System.out.println("\tB) Battle");
        System.out.println("\tT) View Team");
        System.out.println("\tV) View Score");
        System.out.println("\tA) Add Pokemon to Team");
        System.out.println("\tR) Remove Pokemon from Team");
        System.out.println("\tS) Save Pokemon Team");
        System.out.println("\tL) Load Pokemon Team");
        System.out.println("\tQ) Quit\n");
    }
    // REFERENCE: mainMenu method was inspired by displayMenu() from TellerApp

    // EFFECTS: prints a horizontal line for separation
    private void horizontalLine() {
        System.out.println("====================================================");
    }
}