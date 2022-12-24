package com.pantho.bbm.objects;

public class AccountInfo {
    String name,email,phone , address;
    int apos,aneg,abpos,abneg,bpos,bneg,opos,oneg;

    public AccountInfo() {
    }

    public AccountInfo(String name, String email, String phone, String address, int apos, int aneg, int abpos, int abneg, int bpos, int bneg, int opos, int oneg) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.apos = apos;
        this.aneg = aneg;
        this.abpos = abpos;
        this.abneg = abneg;
        this.bpos = bpos;
        this.bneg = bneg;
        this.opos = opos;
        this.oneg = oneg;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getApos() {
        return apos;
    }

    public void setApos(int apos) {
        this.apos = apos;
    }

    public int getAneg() {
        return aneg;
    }

    public void setAneg(int aneg) {
        this.aneg = aneg;
    }

    public int getAbpos() {
        return abpos;
    }

    public void setAbpos(int abpos) {
        this.abpos = abpos;
    }

    public int getAbneg() {
        return abneg;
    }

    public void setAbneg(int abneg) {
        this.abneg = abneg;
    }

    public int getBpos() {
        return bpos;
    }

    public void setBpos(int bpos) {
        this.bpos = bpos;
    }

    public int getBneg() {
        return bneg;
    }

    public void setBneg(int bneg) {
        this.bneg = bneg;
    }

    public int getOpos() {
        return opos;
    }

    public void setOpos(int opos) {
        this.opos = opos;
    }

    public int getOneg() {
        return oneg;
    }

    public void setOneg(int oneg) {
        this.oneg = oneg;
    }
}
