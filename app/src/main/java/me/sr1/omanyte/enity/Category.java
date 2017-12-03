package me.sr1.omanyte.enity;

import com.google.gson.annotations.SerializedName;

/**
 * 分类信息
 * @author SR1
 */

public class Category {

    @SerializedName("id")
    public final String Id;

    @SerializedName("name")
    public final String Name;

    public Category(String id, String name) {
        Id = id;
        Name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
