package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.ing1.pds.business.dto.User;
import edu.ezip.ing1.pds.business.dto.Users;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MainInsertUser {

    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    //private final static String usersToBeInserted = "users-to-insert.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "INSERT_USER";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

        /*final Students guys = ConfigLoader.loadConfig(Students.class, studentsToBeInserted);
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        // Logging configuration details
        logger.trace("Students loaded : {}", guys.toString());
        logger.debug("Load Network config file : {}", networkConfig.toString());


        // Insertion request for each student
        int birthdate = 0;
        for(final Student guy : guys.getStudents()) {

            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(guy);
            logger.trace("Student with its JSON face : {}", jsonifiedGuy);

            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(requestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertStudentsClientRequest clientRequest = new InsertStudentsClientRequest (
                    networkConfig,
                    birthdate++, request, guy, requestBytes);
            clientRequests.push(clientRequest);
        }

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest = clientRequests.pop();
            clientRequest.join();
            final Student guy = (Student)clientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest.getThreadName(),
                    guy.getName(), guy.getFirstname(), guy.getGroup(),
                    clientRequest.getResult());
        }*/

        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        List<User> usersList = new ArrayList<>();
        UserForm userForm = new UserForm();
        userForm.waitForSubmission();
        usersList.add(userForm.getUser());

        Thread.sleep(1000);

        for (User user : usersList){
            // same block instead of guys.getStudent() user in usersList
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            logger.trace("Student with its JSON face : {}", jsonifiedGuy);

            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(requestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertUsersClientRequest clientRequest = new InsertUsersClientRequest (
                    networkConfig,
                    0, request, user, requestBytes);
            clientRequests.push(clientRequest);

        }

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest = clientRequests.pop();
            clientRequest.join();
            final User user = (User)clientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest.getThreadName(),
                    user.getFirstname(), user.getLastname(), user.getEmail(),
                    clientRequest.getResult());
        }
    }

}
