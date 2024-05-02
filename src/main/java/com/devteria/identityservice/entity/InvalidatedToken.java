package com.devteria.identityservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) // tự định nghĩa cho lớp là private hết
@Entity
public class InvalidatedToken {
    @Id
    String id;
    Date expiryTime;
}
