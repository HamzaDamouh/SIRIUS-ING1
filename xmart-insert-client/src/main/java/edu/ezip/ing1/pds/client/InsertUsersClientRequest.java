package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.User;
import edu.ezip.ing1.pds.business.dto.Users;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertUsersClientRequest extends ClientRequest<User, String> {

    public InsertUsersClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, User info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> userIdMap = mapper.readValue(body, Map.class);
        final String result  = userIdMap.get("user_id").toString();
        return result;
    }
}




