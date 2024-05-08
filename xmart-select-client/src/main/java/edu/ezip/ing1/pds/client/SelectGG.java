package edu.ezip.ing1.pds.client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.User;
import edu.ezip.ing1.pds.business.dto.Users;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class SelectGG {

    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "SELECT_GG";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    // for GUI
    public static String retrieveGG() throws IOException, InterruptedException, SQLException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectGGClientRequest clientRequest = new SelectGGClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        StringBuilder usersInfoBuilder = new StringBuilder();
        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            final Users users = (Users) joinedClientRequest.getResult();
            usersInfoBuilder.append(getUsersInfo(users)).append("\n");
        }
        return usersInfoBuilder.toString();
    }

    // For dispatch
    private static String getUsersInfo(Users users) {
        StringBuilder stringBuilder = new StringBuilder();
        AsciiTable asciiTable = new AsciiTable();
        for (User user : users.getUsers()) {
            asciiTable.addRule();
            asciiTable.addRow(user.getLastname(), user.getFirstname(), user.getEmail(),user.getGender(),user.getAge(),user.getHeight(), user.getWeight(), user.getCalories());
        }
        asciiTable.addRule();
        stringBuilder.append("\n").append(asciiTable.render()).append("\n");
        return stringBuilder.toString();
    }

}
