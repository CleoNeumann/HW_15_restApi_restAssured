package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties({"total_pages", "data", "support"})
public class UsersListResponseModel {
    private String page, total;
    @JsonProperty("per_page")
    private String perPage;
}
