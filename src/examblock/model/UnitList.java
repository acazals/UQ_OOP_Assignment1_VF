package examblock.model;

import java.util.ArrayList;
import java.util.List;

/** Javadoc  */
public class UnitList {

    /** Javadoc  */
    private final ArrayList<Unit> units;

    /** Javadoc  */
    public UnitList() {
        this.units = new ArrayList<>();
    }

    /** Javadoc
     * @param unit myunit
     * */
    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    /** Javadoc
     * @return List<Unit> </Unit>
     * */
    public List<Unit> all() {
        return new ArrayList<>(units);
    }

    /**
     *  * @param title the title
     * @param unitId id
     * @param title title
     * @return the id*/

    public Unit getUnit(String title, Character unitId) {
        for (Unit unit : this.units) {
            if (unit.id().equals(unitId) && unit.getSubject().title().equals(title)) {
                return unit;
            }
        }
        throw new java.lang.IllegalStateException();
    }

    /** Javadoc
     * @param unit u
     * */
    public void removeUnit(Unit unit) {
        this.units.remove(unit);
    }

    /** Javadoc
     * @return str
     * */
    public String getFullDetail() {
        if (this.units.isEmpty()) {
            return "";
        } else {
            StringBuilder total = new StringBuilder();
            for (Unit unit : this.units) {
                total.append(unit.getFullDetail()).append("\n");
            }
            return total.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Unit unit : this.units) {
            String str = unit.getSubject() + " : Unit " + unit.id()
                    + " " + unit.getSubject().title() + "\n";
            sb.append(str);
        }
        return sb.toString();
    }
}
