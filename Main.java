/**
 * 
 *
 * @author Nigel Arias, Sidney Gills, Brianne Tomaszek
 */


import java.util.ArrayList;
import java.util.List;

 class Unit {
    
    String name;
    int speed;
    int wounds;
    double armor;
    double shootingAccuracy;
    int shootingDamage;
    double closeCombatAccuracy;
    int closeCombatDamage;
    int pointCost;

    // Constructor
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

    // Method to calculate unit's effectiveness
    public int calculateEffectiveness() {
        
        // Effectiveness can be calculated differently based on your game rules
        return (int) (this.wounds * this.shootingDamage * this.shootingAccuracy + 
                      this.closeCombatDamage * this.closeCombatAccuracy);
    }

    @Override
    public String toString() {
        
        return name + " [Speed: " + speed + ", Wounds: " + wounds + ", Armor: " + armor +
               ", Shooting: " + shootingDamage + " (" + shootingAccuracy + ")" +
               ", Close Combat: " + closeCombatDamage + " (" + closeCombatAccuracy + ")" + 
               ", Points: " + pointCost + "]";
    }
}


class Codex {
    List<Unit> units;

    // Constructor
    public Codex() {
        
        units = new ArrayList<>();
        
        // Sample units
        units.add(new Unit("Marine", 6, 10, 0.9, 0.85, 18, 0.75, 12, 80));
        units.add(new Unit("Assault Marine", 7, 8, 0.85, 0.8, 16, 0.8, 14, 90));
        units.add(new Unit("Terminator", 5, 12, 0.95, 0.9, 20, 0.7, 18, 120));
        units.add(new Unit("Scout", 8, 6, 0.7, 0.65, 12, 0.6, 10, 60));
    }

    public List<Unit> getUnits() {
        return units;
    }
}


class Army {
   
    List<Unit> selectedUnits;

    // Constructor
    public Army() {
        
        selectedUnits = new ArrayList<>();
    }

    // Adds a unit to the army.
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Army:\n");
        for (Unit unit : selectedUnits) {
            sb.append(unit).append("\n");
        }
        return sb.toString();
    }
}

public class Main {
public static void main(String[] args) {
    Codex codex = new Codex();
        Army myArmy = new Army();

        // Add units to the army
        myArmy.addUnit(codex.getUnits().get(0)); // Marine
        myArmy.addUnit(codex.getUnits().get(2)); // Terminator

        // Display the army and its stats
        System.out.println(myArmy);
        System.out.println("Total Points: " + myArmy.calculateTotalPoints());
        System.out.println("Total Effectiveness: " + myArmy.calculateTotalEffectiveness());
    }

    
    
}    
