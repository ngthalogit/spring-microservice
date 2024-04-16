package org.example.devicesweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.example.devicesweb.model.Device;
import org.example.devicesweb.model.TokenResponse;
import org.example.devicesweb.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DeviceRequestServiceImpl implements DeviceRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceRequestServiceImpl.class);
    private final DeviceRepository deviceRepository;
    private final InvocationService invocationService;
    private final Environment environment;
    private final ObjectMapper objectMapper;

    public DeviceRequestServiceImpl(DeviceRepository deviceRepository, InvocationService invocationService, Environment environment, ObjectMapper objectMapper) {
        this.deviceRepository = deviceRepository;
        this.invocationService = invocationService;
        this.environment = environment;
        this.objectMapper = objectMapper;
    }

    @Override
    public void request(TokenResponse token) throws RuntimeException {
        String accessToken = token.getTokenType() + " " + token.getAccessToken();
        try {
            List<Device> devices = invoke(List.of(new BasicNameValuePair("Authorization", accessToken)));
            persist(devices);
        } catch (JsonProcessingException | RuntimeException e) {
            LOGGER.error("Error occurs when attempt to retrieve devices data: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private List<Device> invoke(List<NameValuePair> headers) throws JsonProcessingException, RuntimeException {
        String deviceUrl = environment.getProperty("resource-server.apis.devices");
        HttpResponse response = invocationService.execute(HttpGet.METHOD_NAME, deviceUrl, null, headers);
        if (response.getCode() == 200) {
            return objectMapper.readValue(response.getReasonPhrase(), new TypeReference<>() {
            });
        }
        if (response.getCode() == 403) {
            LOGGER.info("Access token for Resource-Server is invalid {}", response.getReasonPhrase());
        }
        throw new RuntimeException(response.getReasonPhrase());
    }

    private void persist(List<Device> devices) {
        long devicesCount = deviceRepository.count();
        if (devicesCount != 0) {
            deviceRepository.deleteAll();
        }
        int size = ((Collection<Device>) deviceRepository.saveAll(devices)).size();
        LOGGER.info("{} devices stored", size);
    }

}
