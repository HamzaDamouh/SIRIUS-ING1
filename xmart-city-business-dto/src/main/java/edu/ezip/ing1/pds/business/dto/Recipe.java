package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "recipe")
public class Recipe {
    private  String name;
    private  String ingredients;
    private  String calories;
    private  String breakfast;


    public Recipe() {
    }
    public final Recipe build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "name", "ingredients","calories", "breakfast");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, name,ingredients,calories,breakfast);
    }
    public Recipe(String name, String ingredients, String calories, String breakfast) {
        this.name = name;
        this.ingredients = ingredients;
        this.calories = calories;
        this.breakfast =breakfast;
    }
    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getCalories() {
        return calories;
    }

    public String getBreakfast() {
        return breakfast;
    }



    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("ingredients")
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @JsonProperty("calories")
    public void setCalories(String calories) {
        this.calories = calories;
    }

    @JsonProperty("breakfast")
    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }



    private void setFieldsFromResulset(final ResultSet resultSet, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for(final String fieldName : fieldNames ) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }
    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for(final String fieldName : fieldNames ) {
            preparedStatement.setString(++ix, fieldName);
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", calories='" + calories + '\'' +
                ", breakfast='" + breakfast + '\'' +
                '}';
    }
}
