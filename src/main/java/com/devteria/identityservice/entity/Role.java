package com.devteria.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) // tự định nghĩa cho lớp là private hết
@Entity
public class Role {
    @Id
    String name;
    String description;

    @ManyToMany
    Set<Permission> permissions;

}