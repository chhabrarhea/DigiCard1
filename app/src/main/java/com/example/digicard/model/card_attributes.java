package com.example.digicard.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.digicard.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class card_attributes implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("upi")
    @Expose
    private String upi;
    @SerializedName("linkedin")
    @Expose
    private String linkedin;
    @SerializedName("linkedin_bsn")
    @Expose
    private String linkedinBsn;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("youtube")
    @Expose
    private String youtube;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("github")
    @Expose
    private String github;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("color")
    @Expose
    private int color;


    protected card_attributes(Parcel in) {
        id = in.readInt();
        username = in.readString();
        name = in.readString();
        designation = in.readString();
        company = in.readString();
        address = in.readString();
        gst = in.readString();
        about = in.readString();
        account = in.readString();
        upi = in.readString();
        linkedin = in.readString();
        linkedinBsn = in.readString();
        facebook = in.readString();
        youtube = in.readString();
        instagram = in.readString();
        twitter = in.readString();
        github = in.readString();
        phone = in.readString();
        email = in.readString();
        color = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(designation);
        dest.writeString(company);
        dest.writeString(address);
        dest.writeString(gst);
        dest.writeString(about);
        dest.writeString(account);
        dest.writeString(upi);
        dest.writeString(linkedin);
        dest.writeString(linkedinBsn);
        dest.writeString(facebook);
        dest.writeString(youtube);
        dest.writeString(instagram);
        dest.writeString(twitter);
        dest.writeString(github);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeInt(color);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<card_attributes> CREATOR = new Creator<card_attributes>() {
        @Override
        public card_attributes createFromParcel(Parcel in) {
            return new card_attributes(in);
        }

        @Override
        public card_attributes[] newArray(int size) {
            return new card_attributes[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public card_attributes() {
        this.name="";
        this.about="";
        this.gst="";
        this.account="";
        this.upi="";
        this.facebook="";
        this.github="";
        this.twitter="";
        this.linkedin="";
        this.linkedinBsn="";
        this.instagram="";
        this.youtube="";
        this.email="";
        this.phone="";
        this.address="";
        this.company="";
        this.designation="";
        this.color=(Color.CYAN);
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getLinkedinBsn() {
        return linkedinBsn;
    }

    public void setLinkedinBsn(String linkedinBsn) {
        this.linkedinBsn = linkedinBsn;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
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
}

