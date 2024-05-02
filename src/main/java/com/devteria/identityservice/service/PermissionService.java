package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.PermissionRequest;
import com.devteria.identityservice.dto.response.PermissionResponse;
import com.devteria.identityservice.entity.Permission;
import com.devteria.identityservice.mapper.PermissionMapper;
import com.devteria.identityservice.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // sử dụng để thay thế cho @Autowired
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// lưu các đối tượng không có acess modifier thành private final
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
    public List<PermissionResponse> getAll(){
        var permission = permissionRepository.findAll();
        return permission.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
