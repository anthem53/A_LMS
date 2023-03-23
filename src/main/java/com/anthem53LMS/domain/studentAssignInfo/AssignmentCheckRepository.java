package com.anthem53LMS.domain.studentAssignInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasAuthenticationCallbackHandler;

public interface AssignmentCheckRepository extends JpaRepository<AssignmentCheck, Long> {
}
