package com.example.hbkjgoa.jydj;

import java.io.Serializable;

public class SBYJ_Bean implements Serializable {

    public String    ID;
    public String    BookName;
    public String    JieShuDate;
    public String    GuiHuanDate;
    public String    JieHuanState;
    public String    BackInfo;
    public String    UserName;
    public String    TimeStr;

    public String    QRUser;


    public String getQRUser() {
        return QRUser;
    }

    public void setQRUser(String QRUser) {
        this.QRUser = QRUser;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getJieShuDate() {
        return JieShuDate;
    }

    public void setJieShuDate(String jieShuDate) {
        JieShuDate = jieShuDate;
    }

    public String getGuiHuanDate() {
        return GuiHuanDate;
    }

    public void setGuiHuanDate(String guiHuanDate) {
        GuiHuanDate = guiHuanDate;
    }

    public String getJieHuanState() {
        return JieHuanState;
    }

    public void setJieHuanState(String jieHuanState) {
        JieHuanState = jieHuanState;
    }

    public String getBackInfo() {
        return BackInfo;
    }

    public void setBackInfo(String backInfo) {
        BackInfo = backInfo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTimeStr() {
        return TimeStr;
    }

    public void setTimeStr(String timeStr) {
        TimeStr = timeStr;
    }
}
