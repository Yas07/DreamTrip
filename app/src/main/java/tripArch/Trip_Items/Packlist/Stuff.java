package Trip_Items.Packlist;

import Trip_DBs.IDB;

public class Stuff implements IDB {
    private String      _name;
    private String      _groupName;
    private boolean     _isChecked;

    public Stuff(String _name) {
        this._name = _name;
        this._groupName = _groupName;
        _isChecked = false;
    }

    // getters
    public String getName() {
        return _name;
    }

    public String getGroupName() {
        return _groupName;
    }

    public boolean isChecked() {
        return _isChecked;
    }

    public boolean isGroup() {
        return _name.equals(_groupName);
    }

    // setters
    public void setName(String _name) {
        this._name = _name;
    }

    public void setGroupName(String _groupName) {
        this._groupName = _groupName;
    }

    public void setCheck(Boolean _isChecked) {
        this._isChecked = _isChecked;
    }

    @Override
    public void addToDb() {
        // sql specific code, should add only stuff fields
    }

    @Override
    public void removeFromDb() {
        // sql specific code, should remove only stuff fields
    }
}
