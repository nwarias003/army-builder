/**
 * This program implements an army optimization tool inspired by Warhammer 40,000,
 * allowing users to build the most effective army configuration within a given points
 * budget. The program makes use of dynamic programming to compute the optimal
 * combination of units from a codex, balancing each unit's attributes (speed, wounds,
 * armor, accuracy) and the overall effectiveness in battle. The program includes several
 * custom functions that allow the user to add units to the codex, compute the most effective
 * army configuration, simulate battles, and calculate the effectiveness of the chosen army
 * based on combat roles and battlefield conditions. The program first initializes a codex
 * to store all available units, where each unit is assigned specific attributes for comparison 
 * during the optimization process. Then, the program initializes two Army objects, representing
 * two opposing armies (Army A and Army B), which will hold the units selected for each army. 
 * Next the program calls the addUnit method to insert six random units from the codex into each 
 * army. After inserting the units into both armies, the program outputs the current army compositions,
 * including their total points and effectiveness. The program then employs dynamic programming 
 * utilizing the ArmyOptimizer class to optimize each army within a predefined point budget. The optimizer
 * iterates through the available units and selects the most effective combination of units for the 
 * set cost limit. Once both armies are optimized, the program outputs the final compositions of the 
 * optimized armies, including their total points and effectiveness. Next, the program simulates a 
 * battle between the two optimized armies by invoking the BattleSimulator class, factoring in 
 * battlefield size and objectives. It then outputs the size of the battlefield and number of objectives.
 * Lastly, the program outputs the result of the battle, determining which army wins based on who has the 
 * most effective team.
 * 
 *
 * @author Nigel Arias, Sidney Gills, Brianne Tomaszek
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
 
  
/**
 * Class to represent a unit in the army.
 */
class Unit {
    
    // Declares the unit's properties of its name speed, wounds, armor, shooting accuracy, shooting damage, 
    // close combat accuracy, close combat damage, and point cost.
    String name;
    int speed;
    int wounds;
    double armor;
    double shootingAccuracy;
    int shootingDamage;
    double closeCombatAccuracy;
    int closeCombatDamage;
    int pointCost;

    // Initializes a new unit with its attributes: name, speed, wounds, armor, shooting accuracy, 
    // shooting damage, close combat accuracy, close combat damage, and point cost.
    // Each attribute is set using the values provided as parameters when creating the unit.
    public Unit(String name, int speed, int wounds, double armor, double shootingAccuracy, int shootingDamage,
                double closeCombatAccuracy, int closeCombatDamage, int pointCost) {
        
        this.name = name;
        this.speed = speed;
        this.wounds = wounds;
        this.armor = armor;
        this.shootingAccuracy = shootingAccuracy;
        this.shootingDamage = shootingDamage;
        this.closeCombatAccuracy = closeCombatAccuracy;
        this.closeCombatDamage = closeCombatDamage;
        this.pointCost = pointCost;
    }

    // Calculates the overall effectiveness of a unit based on its stats.
    public int calculateEffectiveness() {

        return (int) ((this.shootingDamage * this.shootingAccuracy + this.closeCombatDamage * this.closeCombatAccuracy)
                        * (this.speed + this.wounds * this.armor));
    }

    // Prints the unit's details in an orgainized format.
    @Override
    public String toString() {
        
        return name + " [Speed: " + speed + ", Wounds: " + wounds + ", Armor: " + armor +
            ", Shooting: " + shootingDamage + " (" + shootingAccuracy + ")" +
            ", Close Combat: " + closeCombatDamage + " (" + closeCombatAccuracy + ")" + 
            ", Points: " + pointCost + "]";
    }
}
 
 
/**
 * Class to represent a codex that holds all available units.
 */
class Codex {
    
    // Units that are available within the codex.
    List<Unit> units;

    // Intializes the codex with predefined units. 
    public Codex() {
        
        // Declares and intializes an array.
        units = new ArrayList<>();
        
        // Inserts units into the codex.
        units.add(new Unit("Spartan", 8, 12, 0.95, 0.9, 20, 0.85, 15, 150));     // Spartan: Super soldiers.
        units.add(new Unit("Elite", 7, 10, 0.9, 0.85, 18, 0.8, 14, 130));       // Elite: Covenant warriors.
        units.add(new Unit("Grunt", 5, 5, 0.6, 0.5, 8, 0.4, 6, 40));           // Grunt: Covenant infantry.
        units.add(new Unit("Jackal", 6, 6, 0.7, 0.65, 10, 0.6, 8, 50));        // Jackal: Shield-wielding infantry.
        units.add(new Unit("Hunter", 4, 20, 0.95, 0.7, 25, 0.9, 30, 200));     // Hunter: Heavy covenant infantry.
        units.add(new Unit("ODST", 7, 9, 0.85, 0.85, 16, 0.75, 12, 100));      // ODST: Orbital Drop Shock Troopers.
        units.add(new Unit("Brute", 6, 14, 0.8, 0.75, 22, 0.85, 20, 160));     // Brute: Covenant heavy infantry.
        units.add(new Unit("Warthog", 10, 12, 0.9, 0.6, 15, 0.6, 10, 120));    // Warthog: Light vehicle.
        units.add(new Unit("Ghost", 12, 8, 0.85, 0.8, 18, 0.5, 8, 100));       // Ghost: Covenant scout vehicle.
        units.add(new Unit("Banshee", 14, 10, 0.85, 0.9, 20, 0.6, 12, 180));   // Banshee: Aerial attack vehicle.
        units.add(new Unit("Marine", 6, 7, 0.75, 0.7, 10, 0.5, 8, 60));        // Marine: Standard infantry.
        units.add(new Unit("Scorpion", 8, 25, 0.95, 0.5, 40, 0.85, 25, 300));  // Scorpion: Heavy tank.
        units.add(new Unit("Mongoose", 15, 6, 0.8, 0.3, 5, 0.2, 5, 70));       // Mongoose: Fast scout vehicle.
        units.add(new Unit("Wraith", 7, 20, 0.9, 0.85, 30, 0.9, 28, 250));     /// Wraith: Heavy tank.
        units.add(new Unit("Sniper", 5, 5, 0.98, 0.6, 12, 0.3, 10, 100));      // Sniper: High accuracy, long-range.
        units.add(new Unit("Engineer", 6, 4, 0.7, 0.4, 5, 0.3, 6, 50));        // Engineer: Support, repairs allies.
        units.add(new Unit("Chopper", 12, 8, 0.85, 0.75, 15, 0.7, 10, 110));   // Chopper: Fast, hit-and-run vehicle.
        units.add(new Unit("Master Chief", 10, 15, 0.98, 0.95, 30, 0.9, 20, 250)); // Master Chief: Legendary Spartan.
        units.add(new Unit("Arbiter", 9, 13, 0.96, 0.9, 28, 0.88, 18, 240));     // Arbiter: Elite commander.
        units.add(new Unit("Sergeant Johnson", 8, 10, 0.9, 0.85, 15, 0.8, 12, 150)); // Sgt. Johnson: "Your daddy."
        
    }

    // Retrieves and returns the list of units found within the codex.
    public List<Unit> getUnits() {
        return units;
    }
}
 

/**
 * Class to represent an army that holds all selected units.
 */
class Army {

    List<Unit> selectedUnits;

    // Initalizes the army.
    public Army() {
        
        selectedUnits = new ArrayList<>();
    }

    // Inserts a unit into the army.
    public void addUnit(Unit unit) {
        
        selectedUnits.add(unit);
    }

    // Retrieves and returns the list of units found within the army.
    public List<Unit> getSelectedUnits() {
        
        return selectedUnits;
    }

    // Calculates the total point cost of the army.
    public int calculateTotalPoints() {
        
        return selectedUnits.stream().mapToInt(unit -> unit.pointCost).sum();
    }

    // Calculates the total effectiveness of the army.
    public int calculateTotalEffectiveness() {
        
        return (int) selectedUnits.stream().mapToInt(Unit::calculateEffectiveness).sum() / 50;
    }

    // Prints all of the units in the army in an orgainized format.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Units:\n");
        for (Unit unit : selectedUnits) {
            sb.append(unit).append("\n");
        }
        return sb.toString();
    }
}
 

/**
 * Class to optimize an army based on point budget using dynamic programming.
 */
class ArmyOptimizer {

    // Declares variables.
    private Army army;
    private int maxPoints;

    // Initializes the optimizer with an army and a max point variables.
    public ArmyOptimizer(Army army, int maxPoints) {
        this.army = army;
        this.maxPoints = maxPoints;
    }

    // Optimizes the army by selecting the best units within the point restriction.
    public Army optimize() {
        
        // Retrieve the codex (available units) from the army.
        List<Unit> codex = new ArrayList<>(army.selectedUnits);
        
        // Use the buildOptimalArmy method to get the optimized army.
        return buildOptimalArmy(codex, maxPoints);
    }
    
    
    // Using dynamic programming to select and build the optimal army of units.
    public static Army buildOptimalArmy(List<Unit> codex, int maxPoints) {

        int n = codex.size();
        
        // Dynamic programming (DP) table to store best values.
        int[][] dp = new int[n + 1][maxPoints + 1];

        // Fill DP table.
        for (int i = 1; i <= n; i++) {
            Unit unit = codex.get(i - 1);
            for (int points = 0; points <= maxPoints; points++) {
                
                // Checks to see that an invalid index is not accessed.  
                if (unit.pointCost <= points) {
                    dp[i][points] = Math.max(dp[i - 1][points],
                                            dp[i - 1][points - unit.pointCost] + unit.calculateEffectiveness());
                } else {
                    
                    // If unit can't be added, carry forward the previous value.
                    dp[i][points] = dp[i - 1][points]; 
                }
            }
        }

        // Backtrack to find the optimal unit composition.
        Army optimalArmy = new Army();
        int points = maxPoints;

        for (int i = n; i > 0 && points > 0; i--) {
            if (dp[i][points] != dp[i - 1][points]) {
                Unit unit = codex.get(i - 1);

                // Ensure we only add the unit if we can afford its cost.
                if (unit.pointCost <= points) {
                    optimalArmy.addUnit(unit);
                    points -= unit.pointCost; // Deduct the unit's point cost.
                }
            }
        }

        return optimalArmy;
    }
}
 
 
/**
 *  Class that simulates unit combat.
 */
class UnitCombat {
    
    // Simulate combat between two units at a given distance.
    public static String Combat(Unit A, Unit B, int distance) {
        
        // Compares the effectiveness based on how distance affects shooting vs close combat.
        int effectivenessA = A.calculateEffectiveness();
        int effectivenessB = B.calculateEffectiveness();

        if (distance > 5) {
            
            // Assume shooting combat at a distance.
            effectivenessA *= A.shootingAccuracy;
            effectivenessB *= B.shootingAccuracy;
        } 
        else {
            
            // Assume close combat at short distance.
            effectivenessA *= A.closeCombatAccuracy;
            effectivenessB *= B.closeCombatAccuracy;
        }

        if (effectivenessA > effectivenessB) {
            return A.name + " wins";
        } else if (effectivenessB > effectivenessA) {
            return B.name + " wins";
        } else {
            return "Draw";
        }
    }
}
 
 
/**
 *  Class represents a battlefield that sets its size, number of objectives, and combat multipliers.
 */
class Battlefield {
    int distance;
    int objectives;
    String size;
    double rangeMultiplier;
    double meleeMultiplier;
    double speedMultiplier;
    double unitForceMultiplier;

    public Battlefield(int distance, int objectives) {
        setBattlefieldVars(distance, objectives);
        setMultipliers();
    }
    
    // Ensures battlefield conditions are between 0 and 10 and classifies its size.
    private void setBattlefieldVars(int distance, int objectives) {
        
        if (distance < 0) {
            this.distance = 0;
        } else if (distance > 10) {
            this.distance = 10;
        } else {
            this.distance = distance;
        }

        if (objectives < 0) {
            this.objectives = 0;
        } else if (objectives > 10) {
            this.objectives = 10;
        } else {
            this.objectives = objectives;
        }

        if (distance <= 3) {
            this.size = "Small";
        } else if (distance >= 7) {
            this.size = "Large";
        } else {
            this.size = "Medium";
        }
    }

    // Sets combat multipliers based on battlefield conditions.
    private void setMultipliers() {
        
        // Adjusts how range and melee combat are weighted, with a smaller battlefield
        // benefiting melee, and a larger battlefield benefiting range.
        if (distance < 5) {
            meleeMultiplier = 1.0 + 0.1 * (5 - distance);
            rangeMultiplier = 1.0 - 0.1 * (5 - distance);
        } else if (distance > 5) {
            rangeMultiplier = 1.0 + 0.1 * (distance - 5);
            meleeMultiplier = 1.0 - 0.1 * (distance - 5);
        } else {
            rangeMultiplier = 1.0;
            meleeMultiplier = 1.0;
        }

        // Adjusts how speed and the number of units is weighted, with a greater
        // number of objectives leading to heavier multipliers.
        speedMultiplier = 1.0 + 0.05 * objectives;
        unitForceMultiplier = 1.0 + 0.01 * objectives;
    }
    
    // Prints the size of battlefield and objectives in an orgainized format.
    @Override
    public String toString() {
        // [Size: Medium - 5, Objectives: 5]
        return "[Size: " + size + " - " + distance + ", Objectives: " + objectives + "]";
    }
}
 
 
/**
 *  Class that simulates a battle between two armies.
 */
class BattleSimulator {
    
    // Calculates an army's effectiveness based on battlefield conditions.
    private static double calculateBattleEffectiveness(Army army, Battlefield battlefield) {

        double totalBattleEffectiveness = 0.0;

        // Applies combat multipliers to each unit then adds it to the total.
        for (Unit unit : army.getSelectedUnits()) {
            double range = unit.shootingDamage * unit.shootingAccuracy * battlefield.rangeMultiplier;
            double melee = unit.closeCombatDamage * unit.closeCombatAccuracy * battlefield.meleeMultiplier;
            double agility = unit.speed * battlefield.speedMultiplier;
            double stamina = unit.wounds * unit.armor;

            double unitBattleEffectiveness = (range + melee) * (agility + stamina);

            totalBattleEffectiveness += unitBattleEffectiveness;
        }

        // Applies a force multiplier and returns the army's battlefield effectiveness.
        return totalBattleEffectiveness * (battlefield.unitForceMultiplier + 0.01 * army.getSelectedUnits().size()) / 50;
    }
    
    // Simulates a battle between two armies on a set battlefield.
    public static String Battle(Army armyA, Army armyB, Battlefield battlefield) {
        
        // Recalculates each army's effectiveness, taking battlefield conditions into account.
        int armyABattleEffectiveness = (int) calculateBattleEffectiveness(armyA, battlefield);
        int armyBBattleEffectiveness = (int) calculateBattleEffectiveness(armyB, battlefield);

        // Stores a formatted string that details the effectiveness of each army.
        String effectiveness = "\tArmy A Effectiveness: " + armyABattleEffectiveness +
                            "\n\tArmy B Effectiveness: " + armyBBattleEffectiveness + "\n";

        // Returns the result of the battle, declaring a winner or draw.
        if (armyABattleEffectiveness > armyBBattleEffectiveness) {
            return effectiveness + "Army A wins!";
        } else if (armyBBattleEffectiveness > armyABattleEffectiveness) {
            return effectiveness + "Army B wins!";
        } else {
            return effectiveness + "It's a draw!";
        }
    }
}
 

// Tests the functionality of the program and algorithm.
public class Main {
    public static void main(String[] args) {
        Codex codex = new Codex();
            
        Army armyA = new Army();
        Army armyB = new Army();
        
        // Get list of units from codex.
        List<Unit> units = codex.getUnits();
        
        // Randomly select 6 units for each army without overlapping.
        Set<Integer> selectedIndices = new HashSet<>();
        Random random = new Random();

        while (selectedIndices.size() < 12) {        
           selectedIndices.add(random.nextInt(units.size()));
           
        }
        
        List<Integer> indicesList = new ArrayList<>(selectedIndices);
        
        for(int i = 0; i < 6; i++) {
        
           // Add units to Army A.
           armyA.addUnit(units.get(indicesList.get(i)));
           
           // Add units to Army B.
           armyB.addUnit(units.get(indicesList.get(i + 6)));
           
        }

    
        // Output both armies and their stats.
        System.out.println("Army A:\n");
        System.out.println(armyA);
        System.out.println("Total Points: " + armyA.calculateTotalPoints());
        System.out.println("Total Effectiveness: " + armyA.calculateTotalEffectiveness());
        
        System.out.println("\nArmy B:\n");
        System.out.println(armyB);
        System.out.println("Total Points: " + armyB.calculateTotalPoints());
        System.out.println("Total Effectiveness: " + armyB.calculateTotalEffectiveness());
        
        System.out.println("\n-----------------------------------------------------------------------------------");
    
        
        // Test the ArmyOptimizer for a maximum point budget.
        int maxPointsA = 600;
        int maxPointsB = 600;
                
        // Optimize Army A and Army B.
        ArmyOptimizer optimizerA = new ArmyOptimizer(armyA, maxPointsA);
        Army optimizedArmyA = optimizerA.optimize();

        ArmyOptimizer optimizerB = new ArmyOptimizer(armyB, maxPointsB);
        Army optimizedArmyB = optimizerB.optimize();
        
        // Display the optimized armies and their stats.
        System.out.println("\nOptimized Army A (Max Points: " + maxPointsA + "):");
        System.out.println(optimizedArmyA);
        System.out.println("Total Points: " + optimizedArmyA.calculateTotalPoints());
        System.out.println("Total Effectiveness: " + optimizedArmyA.calculateTotalEffectiveness());

        System.out.println("\nOptimized Army B (Max Points: " + maxPointsB + "):");
        System.out.println(optimizedArmyB);
        System.out.println("Total Points: " + optimizedArmyB.calculateTotalPoints());
        System.out.println("Total Effectiveness: " + optimizedArmyB.calculateTotalEffectiveness());

        System.out.println("\n-----------------------------------------------------------------------------------");
        

        // Simulates battles between Army A and Army B on different battlefields, displaying battlefield conditions,
        // the effectiveness of each army in these conditions, and the result of the battle.
        System.out.println("\nBattle Results:");

        // Battle 1
        Battlefield battlefield1 = new Battlefield(3, 3);
        String battleResult1 = BattleSimulator.Battle(optimizedArmyA, optimizedArmyB, battlefield1);
        System.out.println("\nBattlefield 1 " + battlefield1);
        System.out.println(battleResult1);
        
        // Battle 2
        Battlefield battlefield2 = new Battlefield(7, 4);
        String battleResult2 = BattleSimulator.Battle(optimizedArmyA, optimizedArmyB, battlefield2);
        System.out.println("\nBattlefield 2 " + battlefield2);
        System.out.println(battleResult2);

        // Battle 3
        Battlefield battlefield3 = new Battlefield(10, 10); 
        String battleResult3 = BattleSimulator.Battle(optimizedArmyA, optimizedArmyB, battlefield3);
        System.out.println("\nBattlefield 3 " + battlefield3);
        System.out.println(battleResult3);
        
        // Battle 4
        Battlefield battlefield4 = new Battlefield(0, 0); 
        String battleResult4 = BattleSimulator.Battle(optimizedArmyA, optimizedArmyB, battlefield4);
        System.out.println("\nBattlefield 4 " + battlefield4);
        System.out.println(battleResult4);
        
        // Battle 5
        Battlefield battlefield5 = new Battlefield(5, 5); 
        String battleResult5 = BattleSimulator.Battle(optimizedArmyA, optimizedArmyB, battlefield5);
        System.out.println("\nBattlefield 5 " + battlefield5);
        System.out.println(battleResult5);
        
        // Battle 6
        Battlefield battlefield6 = new Battlefield(10, 0); 
        String battleResult6 = BattleSimulator.Battle(optimizedArmyA, optimizedArmyB, battlefield6);
        System.out.println("\nBattlefield 6 " + battlefield6);
        System.out.println(battleResult6);
        
        // Battle 7
        Battlefield battlefield7 = new Battlefield(0, 10);
        String battleResult7 = BattleSimulator.Battle(optimizedArmyA, optimizedArmyB, battlefield7);
        System.out.println("\nBattlefield 7 " + battlefield7);
        System.out.println(battleResult7);   
        
    }
}    
 
