package Trip_Items.Places;

import java.sql.Time;

public class Place {

    private String                _name;
    private Time                  _opensAt;
    private Time                  _closesAt;
    private String                _address;

    public Place(String _name, Time _opensAt, Time _closesAt, String _address) {
        this._name = _name;
        this._opensAt = _opensAt;
        this._closesAt = _closesAt;
        this._address = _address;
    }

    public String getPlaceData() {
        return _name + "\n" + _address +
                "opens at: " + _opensAt.toString() +
                "closes at: " + _closesAt.toString();
    }

}
