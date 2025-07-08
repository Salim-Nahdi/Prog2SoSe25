package Model;

public class Attack {
    private String path;
    private String name;
    private int damage;
    private boolean isUltimate;
    private int ultCooldown;

    public Attack(String path, String name, int damage, boolean isUltimate) {
        this.path = path;
        this.name = name;
        this.damage = damage;
        this.isUltimate = isUltimate;
        this.ultCooldown = 0;
    }

    public String getPath() { return path; }
    public String getName() { return name; }
    public int getDamage() { return damage; }
    public boolean getIsUltimate() { return isUltimate; }
    public int getUltCooldown() { return ultCooldown; }

    public void updateCooldown(boolean ultUsed) {
        if (ultUsed) this.ultCooldown = 0;
        else {
            if (this.ultCooldown < 2) this.ultCooldown += 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attack)) return false;

        Attack attack = (Attack) o;

        if (damage != attack.damage) return false;
        if (!path.equals(attack.path)) return false;
        return name.equals(attack.name);
    }
}
