package com.virtualpet.vpet.model;

import com.virtualpet.vpet.model.enums.AccessoryType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "accessories")
public class Accessory {
    @Id
    private String id;
    private String name;
    private String description;
    private AccessoryType type;
}