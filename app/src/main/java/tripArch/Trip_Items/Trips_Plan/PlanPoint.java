package Trip_Items.Trips_Plan;

import android.graphics.Color;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.widget.ImageView;
import Trip_Items.Places.Place;

public class PlanPoint implements Comparable {

    private String          _title;
    private int             _colorIndex;
    private int             _iconIndex;
    private Place           _place;
    private Calendar        _calendar;
    private String          _otherDetails;
    DateFormat              _timeFormat;
    DateFormat              _dateFormat;

    public PlanPoint(String _title,
                     int _colorIndex,
                     int _icon,
                     Place _place,
                     Calendar _calendar, String _otherDetails) {
        this._title = _title;
        this._colorIndex = _colorIndex;
        this._iconIndex = _icon;
        this._place = _place;
        this._calendar = _calendar;
        if (this._calendar == null) {
           this._calendar = Calendar.getInstance();
        }
        this._otherDetails = _otherDetails;
        _timeFormat = new SimpleDateFormat("HH:mm");
        _dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }


    @Override
    public int compareTo(Object o) {
        PlanPoint p = (PlanPoint)o;
        return _calendar.compareTo(p._calendar);
    }

    public String getTime() {
        return _timeFormat.format(_calendar.getTime());
    }

    public Calendar getCalendar() {
        return _calendar;
    }

    public String getDate() {
        return _dateFormat.format(_calendar.getTime());
    }

    public String getDayOfWeek() {
            int i = getCalendar().get(Calendar.DAY_OF_WEEK);
            String day = "";
            switch(i){
                case 1:
                    day="Sunday";
                    break;
                case 2:
                    day="Monday";
                    break;
                case 3:
                    day="Tuesday";
                    break;
                case 4:
                    day="Wednesday";
                    break;
                case 5:
                    day="Thursday";
                    break;
                case 6:
                    day="Friday";
                    break;
                case 7:
                    day="Saturday";
                    break;
            }
            return day;
    }

    public String getOtherDetails() {
        return _otherDetails;
    }

    public Place getPlace() {
        return _place;
    }

    public String getTitle(){
        return _title;
    }

    public int getColorIndex() {
        return _colorIndex;
    }

    public int getIconIndex() {
        return _iconIndex;
    }

    public void setTime(int y, int m, int d, int hour, int min) {
        _calendar.set(y, m, d, hour, min,0);
    }
}
