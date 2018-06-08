package Trip_Items.Trips_Plan;

import android.graphics.Color;

import java.util.Calendar;
import android.widget.ImageView;
import Trip_Items.Places.Place;

public class PlanPoint implements Comparable {

    private String          _title;
    private Color _colorBlock;
    private ImageView       _icon;
    private Place           _place;
    private Calendar        _calendar;
    private String          _otherDetails;

    public PlanPoint(String _title, Color _colorBlock,
                     ImageView _icon, Place _place,
                     Calendar _calendar, String _otherDetails) {
        this._title = _title;
        this._colorBlock = _colorBlock;
        this._icon = _icon;
        this._place = _place;
        this._calendar = _calendar;
        if (this._calendar == null) {
           this._calendar = Calendar.getInstance();
        }
        this._otherDetails = _otherDetails;
    }


    @Override
    public int compareTo(Object o) {
        PlanPoint p = (PlanPoint)o;
        return _calendar.compareTo(p._calendar);
    }

    public Calendar getCalendar() {
        return _calendar;
    }

    public void setTime(int y, int m, int d, int hour, int min) {
        _calendar.set(y, m, d, hour, min,0);
    }
}
