package Trip_Items.Trips_Plan;

import android.graphics.Color;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.ImageView;
import Trip_Items.Places.Place;

public class PlanPoint implements Comparable {

    private String          _title;
    // TODO: make the blocks to be colorful
//    private Color _colorBlock;
//    private ImageView       _icon;
    private Place           _place;
    private Calendar        _calendar;
    private String          _otherDetails;
    DateFormat              _timeFormat;

    public PlanPoint(String _title,
                     // TODO: make icons + color
//                     Color _colorBlock,
//                     ImageView _icon,
                     Place _place,
                     Calendar _calendar, String _otherDetails) {
        this._title = _title;
//        this._colorBlock = _colorBlock;
        // TODO: make icons
//        this._icon = _icon;
//        this._icon = null;
        this._place = _place;
        this._calendar = _calendar;
        if (this._calendar == null) {
           this._calendar = Calendar.getInstance();
        }
        this._otherDetails = _otherDetails;
        _timeFormat = new SimpleDateFormat("HH:mm");
    }


    @Override
    public int compareTo(Object o) {
        PlanPoint p = (PlanPoint)o;
        return _calendar.compareTo(p._calendar);
    }

    public String getTime() {
        return _timeFormat.format(_calendar.getTime());
    }

    public String get_otherDetails() {
        return _otherDetails;
    }

    public void setTime(int y, int m, int d, int hour, int min) {
        _calendar.set(y, m, d, hour, min,0);
    }
}
