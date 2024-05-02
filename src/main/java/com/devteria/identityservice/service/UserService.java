package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.UserCreationRequest;
import com.devteria.identityservice.dto.request.UserUpdateRequest;
import com.devteria.identityservice.dto.response.UserResponse;
import com.devteria.identityservice.entity.User;
import com.devteria.identityservice.enums.Role;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.mapper.UserMapper;
import com.devteria.identityservice.repository.RoleRepository;
import com.devteria.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // sử dụng để thay thế cho @Autowired
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// lưu các đối tượng không có acess modifier thành private final
@Slf4j
public class UserService {
//    @Autowired
    UserRepository userRepository;
//    @Autowired
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request){
        log.info("Service: Create User");
//        User user = new User();
//        if(userRepository.existsByUsername(request.getUsername()))
//            throw new RuntimeException("User existed.");
//        if(userRepository.existsByUsername(request.getUsername()))
//            throw new AppException(ErrorCode.USER_EXISTED);
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

//        if(userRepository.existsByUsername(request.getUsername()))
//            throw new RuntimeException("ErrorCode.USER_EXISTED");
        User user = userMapper.toUser(request);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED) );
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());
//        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    // Kiểm tra role trước rồi mới thực hiện method
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostAuthorize("hasRole('APPROVE_DATA')") // không được
    @PostAuthorize("hasAuthority('APPROVE_DATA')") // sử dụng với permission
    public List<UserResponse> getUsers(){
        log.info("In method get users!");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    // Thực hiện method rồi mới kiểm tra, nếu role sai thì ko trả về role
//    @PostAuthorize("hasRole('ADMIN')")
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id){
        log.info("In method get user by Id!");
        return userMapper.toUserResponse((userRepository.findById(id))
                .orElseThrow(() -> new RuntimeException("User not found")));
    }
}