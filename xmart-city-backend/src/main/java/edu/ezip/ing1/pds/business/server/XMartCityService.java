package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL("SELECT * FROM \"ezip-ing1\".users t"),
        SELECT_ALL_Student("SELECT * FROM \"ezip-ing1\".students t"),
        SELECT_ALL_Recipe("SELECT * FROM \"ezip-ing1\".recipes t"),

        INSERT_USER("INSERT into \"ezip-ing1\".users (\"lastname\", \"firstname\", \"email\", \"gender\", \"age\", \"height\", \"weight\") values (?, ?, ?,?, ?, ?, ?)"),
        INSERT_STUDENT("INSERT into \"ezip-ing1\".students (\"name\", \"firstname\", \"group\") values (?, ?, ?)"),
        INSERT_RECIPE("INSERT into \"ezip-ing1\".recipes (\"name\", \"ingredients\", \"calories\",\"breakfast\") values (?, ?, ?, ?)");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }

    public static XMartCityService inst = null;
    public static final XMartCityService getInstance()  {
        if(inst == null) {
            inst = new XMartCityService();
        }
        return inst;
    }

    private XMartCityService() {}

    public Response selectAllStudents(final Request request, final Connection connection) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_Student.query);
            ResultSet resultSet = selectStatement.executeQuery();

            Students students = new Students();

            while (resultSet.next()) {
                Student student = new Student();
                student.build(resultSet);
                students.add(student);
            }

            // Mapper users en JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(students);

            return new Response(request.getRequestId(), responseBody);
        } catch (Exception e) {
            logger.error("Error executing SELECT_ALL_Student: {}", e.getMessage());
            return new Response(request.getRequestId(), "Error executing SELECT_ALL query");
        }
    }
    public Response selectAllRecipes(final Request request, final Connection connection) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_Recipe.query);
            ResultSet resultSet = selectStatement.executeQuery();

            Recipes recipes = new Recipes();

            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.build(resultSet);
                recipes.add(recipe);
            }

            // Mapper users en JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(recipes);

            return new Response(request.getRequestId(), responseBody);
        } catch (Exception e) {
            logger.error("Error executing SELECT_ALL_Recipe: {}", e.getMessage());
            return new Response(request.getRequestId(), "Error executing SELECT_ALL query");
        }
    }


    public Response selectAllUsers(final Request request, final Connection connection) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL.query);
            ResultSet resultSet = selectStatement.executeQuery();

            Users users = new Users();

            while (resultSet.next()) {
                User user = new User();
                user.build(resultSet);
                users.add(user);
            }

            // Mapper users en JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(users);

            return new Response(request.getRequestId(), responseBody);
        } catch (Exception e) {
            logger.error("Error executing SELECT_ALL: {}", e.getMessage());
            return new Response(request.getRequestId(), "Error executing SELECT_ALL query");
        }
    }

    public Response insertUser(final Request request, final Connection connection) {
        try {
            String requestBody = request.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(requestBody, User.class);
            PreparedStatement insertStatement = connection.prepareStatement(Queries.INSERT_USER.query);
            insertStatement.setString(1, user.getLastname());
            insertStatement.setString(2, user.getFirstname());
            insertStatement.setString(3, user.getEmail());
            insertStatement.setString(4, user.getGender());
            insertStatement.setString(5, user.getAge());
            insertStatement.setString(6, user.getHeight());
            insertStatement.setString(7, user.getWeight());

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                return new Response(request.getRequestId(),String.format("{\"user_id\": %d}", rowsAffected));
            } else {
                return new Response(request.getRequestId(), "Failed to insert user");
            }
        } catch (SQLException | IOException e) {
            logger.error("Error executing INSERT_USER query: {}", e.getMessage());
            return new Response(request.getRequestId(), "Error executing INSERT_USER query");
        }
    }
    public Response insertStudent(final Request request, final Connection connection) {
        try {
            String requestBody = request.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();
            Student student  = objectMapper.readValue(requestBody, Student.class);
            PreparedStatement insertStatement = connection.prepareStatement(Queries.INSERT_STUDENT.query);
            insertStatement.setString(1, student.getName());
            insertStatement.setString(2, student.getFirstname());
            insertStatement.setString(3, student.getGroup());

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                return new Response(request.getRequestId(),String.format("{\"student_id\": %d}", rowsAffected));
            } else {
                return new Response(request.getRequestId(), "Failed to insert user");
            }
        } catch (SQLException | IOException e) {
            logger.error("Error executing INSERT_STUDENT query: {}", e.getMessage());
            return new Response(request.getRequestId(), "Error executing INSERT_STUDENT  query");
        }
    }
    public Response insertRecipe(final Request request, final Connection connection) {
        try {
            String requestBody = request.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();
            Recipe recipe  = objectMapper.readValue(requestBody, Recipe.class);
            PreparedStatement insertStatement = connection.prepareStatement(Queries.INSERT_RECIPE.query);
            insertStatement.setString(1, recipe.getName() );
            insertStatement.setString(2, recipe.getIngredients());
            insertStatement.setString(3, recipe.getCalories());
            insertStatement.setString(4, recipe.getBreakfast());

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                return new Response(request.getRequestId(),String.format("{\"recipe_id\": %d}", rowsAffected));
            } else {
                return new Response(request.getRequestId(), "Failed to insert recipe");
            }
        } catch (SQLException | IOException e) {
            logger.error("Error executing INSERT_RECIPE query: {}", e.getMessage());
            return new Response(request.getRequestId(), "Error executing INSERT_RECIPE  query");
        }
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException {
        Response response = null;

        if (request != null) {
            String action = request.getRequestOrder();

            switch (action) {
                case "SELECT_ALL_USERS":
                    response = selectAllUsers(request, connection);
                    break;
                case "SELECT_ALL_Student":
                    response = selectAllStudents(request, connection);
                    break;
                case "SELECT_ALL_Recipe":
                    response = selectAllRecipes(request, connection);
                    break;
                case "INSERT_USER":
                    response = insertUser(request, connection);
                    break;
                case "INSERT_STUDENT":
                    response = insertStudent(request, connection);
                    break;
                case "INSERT_RECIPE":
                    response = insertRecipe(request, connection);
                    break;
                default:
                    // Handle unknown action
                    response = new Response(request.getRequestId(), "Unknown action");
                    break;
            }
        }

        return response;
    }

}
