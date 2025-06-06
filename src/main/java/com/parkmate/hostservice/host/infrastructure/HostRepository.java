package com.parkmate.hostservice.host.infrastructure;

import com.parkmate.hostservice.host.domain.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {

}
