package com.devteria.identityservice.dto.response;

import com.devteria.identityservice.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) // tự định nghĩa cho lớp là private hết
public class UserResponse {
    String id;
    String username;
//    private String password;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<RoleResponse> roles;
}
