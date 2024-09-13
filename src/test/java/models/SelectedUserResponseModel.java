package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties({"page", "per_page", "total", "total_pages", "support"})
public class SelectedUserResponseModel {
    private UserData data;

    @Data
    @JsonIgnoreProperties({"avatar"})
    public static class UserData {
        private String id, email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
    }
}
