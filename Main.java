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
 * Next the program calls the addUnit method to  insert specific units from the codex into each 
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
        
        // Calculates effectiveness by combinding the stats for wounds, damage, and accuracy. 
        return (int) (this.wounds * this.shootingDamage * this.shootingAccuracy + 
                      this.closeCombatDamage * this.closeCombatAccuracy);
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

    // Intializes the codex with predfined units. 
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
 * Class to represent a army that holds all selected units.
 */
class Army {
   
    List<Unit> selectedUnits;

    // Initalizes the army.
    public Army() {
        
        selectedUnits = new ArrayList<>();
    }

    // Inserts a unit to the army.
    public void addUnit(Unit unit) {
        
        selectedUnits.add(unit);
    }

    // Calculates the total point cost of the army.
    public int calculateTotalPoints() {
        
        return selectedUnits.stream().mapToInt(unit -> unit.pointCost).sum();
    }

    // Calculates the total effectiveness of the army.
    public int calculateTotalEffectiveness() {
        
        return selectedUnits.stream().mapToInt(Unit::calculateEffectiveness).sum();
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
 *  Class that represents a battlefield that sets the size of it and amount of objectives.
 */
class Battlefield {
    String size; // "small", "large"
    int objectives;

    public Battlefield(String size, int objectives) {
        this.size = size;
        this.objectives = objectives;
    }

    // Check if the battlefield is large or small.
    public boolean isLarge() {
        return this.size.equals("large");
    }

    // Prints the size of battlefield and objectives in an orgainized format.
    @Override
    public String toString() {
        return "Battlefield [Size: " + size + ", Objectives: " + objectives + "]";
    }
}


/**
 *  Class that simulates a battle between two armies.
 */
class BattleSimulator {

    // Function that simulates a battle between two armies on a set battlefield.
    public static String Battle(Army armyA, Army armyB, Battlefield battlefield) {
        
        int armyAEffectiveness = armyA.calculateTotalEffectiveness();
        int armyBEffectiveness = armyB.calculateTotalEffectiveness();

       
        // Checks to see which army wins based on who has the highest effectiveness.
        if (armyAEffectiveness > armyBEffectiveness) {
            return "Army A wins!";
        } else if (armyBEffectiveness > armyAEffectiveness) {
            return "Army B wins!";
        } else {
            return "It's a draw!";
        }
    }
}




public class Main {
public static void main(String[] args) {
    Codex codex = new Codex();
        
    Army armyA = new Army();
    Army armyB = new Army();
    
    // Add units to Army A.
    armyA.addUnit(codex.getUnits().get(0)); // Spartan
    armyA.addUnit(codex.getUnits().get(2)); // Grunt
    armyA.addUnit(codex.getUnits().get(4)); // Hunter
    armyA.addUnit(codex.getUnits().get(6)); // Brute
    armyA.addUnit(codex.getUnits().get(10)); // Marine
    armyA.addUnit(codex.getUnits().get(16)); // Engineer

    // Add units to Army B.
    armyB.addUnit(codex.getUnits().get(1)); // Elite
    armyB.addUnit(codex.getUnits().get(3)); // Jackal
    armyB.addUnit(codex.getUnits().get(5)); // ODST
    armyB.addUnit(codex.getUnits().get(9)); // Banshee
    armyB.addUnit(codex.getUnits().get(11)); // Scorpion
    armyB.addUnit(codex.getUnits().get(15)); // Chopper
   
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

    
    // Simulate the battle between Army A and Army B.
    Battlefield battlefield = new Battlefield("small", 3); // Small battlefield with 3 objectives
    String battleResult = BattleSimulator.Battle(optimizedArmyA, optimizedArmyB, battlefield);
    
    // Output the size of battlefield and objectives.
    System.out.println("\n" + battlefield);
    
    // Output the result of who won the battle between the two armies.
    System.out.println("Battle Result: " + battleResult);
    
    
    }
}    
