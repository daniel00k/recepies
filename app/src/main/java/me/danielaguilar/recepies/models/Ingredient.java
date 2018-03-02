package me.danielaguilar.recepies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by danielaguilar on 25-01-18.
 */

public class Ingredient implements Parcelable{
    public static final String CLASS_NAME = Ingredient.class.getName();

    @SerializedName("ingredient")
    private String name;
    private String measure;
    private float quantity;

    public Ingredient(){}
    protected Ingredient(Parcel in) {
        name = in.readString();
        measure = in.readString();
        quantity = in.readFloat();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(measure);
        parcel.writeFloat(quantity);
    }

    public String getDescription() {
        return String.valueOf(quantity)+" "+measure+" of "+name;
    }
}
