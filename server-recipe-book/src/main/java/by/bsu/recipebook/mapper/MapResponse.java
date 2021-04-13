package by.bsu.recipebook.mapper;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class MapResponse {
    public static Map<String, Object> getResponseAsMap(String dataKey, Object data, Page<?> pageTuts) {
        Map<String, Object> response = new HashMap<>();
        response.put(dataKey, data);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }
}
