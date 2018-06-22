package Trip_Items.Places;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Place {

    // TODO: add _name in future
//    private String                _name;
    private Calendar _opensAt;
    private Calendar _closesAt;
    private String                _address;
    DateFormat                  _timeFormat;

//    public Place(String _name, Calendar _opensAt, Calendar _closesAt, String _address) {
    public Place(Calendar _opensAt, Calendar _closesAt, String _address) {
//        this._name = _name;
        this._opensAt = _opensAt;
        this._closesAt = _closesAt;
        this._address = _address;
        _timeFormat = new SimpleDateFormat("HH:mm");
    }

    // TODO: _name +
    public String getPlaceData() {
        return _address +
                " " + _timeFormat.format(_opensAt.getTime()) +
                " - " + _timeFormat.format(_closesAt.getTime())+ " ";
    }

    public Calendar get_opensAt() {
        return _opensAt;
    }

    public Calendar get_closesAt() {
        return _closesAt;
    }

    public String get_address() {
        return _address;
    }

    public DateFormat get_timeFormat() {
        return _timeFormat;
    }
}
