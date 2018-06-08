package Trip_Items.Trips_Plan;

import java.util.Calendar;
import java.util.TreeMap;

public class Plan extends TreeMap<Calendar, PlanPoint> {
    public Plan() {
        super();
    }

    public PlanPoint put(PlanPoint planPoint) {
        return super.put(planPoint.getCalendar(), planPoint);
    }
}
