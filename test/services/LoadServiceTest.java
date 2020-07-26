package services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import RequestResult.LoadRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {

    @Test
    void load() throws IOException {
        Gson gson = new Gson();
        LoadRequest request = gson.fromJson(Files.readString(Paths.get("json/workingexample.json")), LoadRequest.class);
        // send that request to the service
        LoadService service = new LoadService();
        // also, get back a response
        assertTrue(service.load(request).isSuccess());
    }
    @Test
    void loadFails() throws IOException {
        Gson gson = new Gson();
        LoadRequest request = gson.fromJson(Files.readString(Paths.get("json/example.json")), LoadRequest.class);
        // send that request to the service
        LoadService service = new LoadService();
        // also, get back a response
        request = gson.fromJson(Files.readString(Paths.get("json/emptyexample.json")), LoadRequest.class);
        assertTrue(service.load(request).isSuccess());
    }
}