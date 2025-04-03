package examblock.model;

import java.util.ArrayList;
import java.util.List;

public class UnitList {

    private ArrayList<Unit> units;

    public UnitList() {
        this.units = new ArrayList<>();
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    public List<Unit> all() {
        return new ArrayList<>(units);
    }

    public Unit getUnit(String title, Character unitId) {
        for (Unit unit : this.units) {
            if (unit.id().equals(unitId) && unit.getSubject().getTitle().equals(title)) {
                return unit;
            }
        }
        throw new IllegalStateException();
    }

    public void removeUnit(Unit unit) {
        if (this.units.contains(unit)) {
            this.units.remove(unit);
        }
    }

    public String getFullDetail() {
        if (this.units.isEmpty()) {
            return "";
        } else {
            String total = "";
            for (int i=0; i<this.units.size(); i++) {
                total = total + this.units.get(i).getFullDetail() + "\n";
            }
            return total;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Unit unit : this.units) {
            String str = unit.getSubject() + " : Unit " + unit.id() + " " + unit.getSubject().getTitle() + "\n";
            sb.append(str);
        }
        return sb.toString();
    }
}
