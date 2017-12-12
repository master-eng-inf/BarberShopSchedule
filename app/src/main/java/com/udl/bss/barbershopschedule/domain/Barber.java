package com.udl.bss.barbershopschedule.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.udl.bss.barbershopschedule.utils.BitmapUtils;


public class Barber implements Parcelable {

    private int id;
    private String name;
    private String description;
    private String city;
    private String address;
    private String phone;
    private String email;
    private Bitmap image;
    private String password;
    private String placesID;
    private String gender;
    private String imagePath;

    public Barber(int id, String name, String description, String city, String address, String phone, String email, Bitmap image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }

    public Barber(int id, String name, String email, String placesID, String password, String phone,
                  String gender, String description, String address, String imagePath) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.placesID = placesID;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.description = description;
        this.address = address;
        this.imagePath = imagePath;
    }


    private Barber(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.city = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Barber> CREATOR = new Creator<Barber>() {
        @Override
        public Barber createFromParcel(Parcel in) {
            return new Barber(in);
        }

        @Override
        public Barber[] newArray(int size) {
            return new Barber[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(city);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeByteArray(BitmapUtils.bitmapToByteArray(image));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String   getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPlacesID() {
        return placesID;
    }

    public String getGender() {
        return gender;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Barber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", image=" + image +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Barber barber = (Barber) o;

        if (id != barber.id) return false;
        if (name != null ? !name.equals(barber.name) : barber.name != null) return false;
        if (description != null ? !description.equals(barber.description) : barber.description != null)
            return false;
        if (city != null ? !city.equals(barber.city) : barber.city != null) return false;
        if (phone != null ? !phone.equals(barber.phone) : barber.phone != null) return false;
        if (email != null ? !email.equals(barber.email) : barber.email != null) return false;
        if (address != null ? !address.equals(barber.address) : barber.address != null)
            return false;
        return image != null ? image.equals(barber.image) : barber.image == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
