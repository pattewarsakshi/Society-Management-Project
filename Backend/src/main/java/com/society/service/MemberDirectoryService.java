package com.society.service;

import java.util.List;

import com.society.dto.MemberDirectoryDTO;
import com.society.entityenum.Role;

public interface MemberDirectoryService {
    List<MemberDirectoryDTO> getMembersDirectory(Integer societyId, Role role);
}
