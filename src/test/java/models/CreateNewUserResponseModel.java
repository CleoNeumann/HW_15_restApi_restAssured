package models;

import lombok.Data;

@Data
public class CreateNewUserResponseModel {
    private String name, job, id, createdAt;
}
