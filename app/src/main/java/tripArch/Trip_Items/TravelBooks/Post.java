package Trip_Items.TravelBooks;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.dreamtrip.dreamtrip.ViewsHandler;

import java.io.File;

import Trip_DBs.IDB;
import Trip_Items.Trips_trip;

public class Post implements IDB {
    private String      _name;
    private int         _colorImg;
    private int         _colorText;
    private int         _colorTextBg;
    private String      _titlePhoto1;
    private Uri _backGroundImg;
    private Uri      _mainImg;

    public Post(String _name,
                String _titlePhoto1,
                int _colorText,
                int _colorTextBg) {
        this._name = _name;
        this._titlePhoto1 = _titlePhoto1;
        this._colorText = _colorText;
        this._colorTextBg = _colorTextBg;
    }

    public Post(Post post) {
        _name = post._name;
    }

    // getters
    public String getName() {
        return _name;
    }

    public int getColorImg() {
        return _colorImg;
    }

    public int getColorText() {
        return _colorText;
    }

    public int getColorTextBg() {
        return _colorTextBg;
    }

    public String getTitlePhoto1() {
        return _titlePhoto1;
    }

    public Uri getBackGroundImgUri() {
        return _backGroundImg;
    }

    public Uri getMainImgUri() {
        return _mainImg;
    }

    // setters


    public void setBackGroundImgUri(Uri _backGroundImg) {
        this._backGroundImg = _backGroundImg;
    }

    public void setMainImgUri(Uri _mainImg) {
        this._mainImg =  _mainImg;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public void setColorImg(int _colorImg) {
        this._colorImg = _colorImg;
    }

    public void clear() {
        ViewsHandler.deleteByUri(_mainImg);
        ViewsHandler.deleteByUri(_backGroundImg);
    }


    //    public void setBackGroungImg(... _backGroundImg) {
//        this._backGroundImg = _backGroundImg;
//    }

    @Override
    public void addToDb() {
        // sql specific code, should add only stuff fields
    }

    @Override
    public void removeFromDb() {
        // sql specific code, should remove only stuff fields
    }
}
