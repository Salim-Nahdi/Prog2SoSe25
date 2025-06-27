package Model; 
public class Attack {
    private final String name;
    private final int damage;

    public Attack(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public String getName() { return name; }
    public int getDamage() { return damage; }
}