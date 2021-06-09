package com.provider.internet.repository;

import com.provider.internet.model.entity.IncludedOption;
import com.provider.internet.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncludedOptionRepository extends JpaRepository<IncludedOption, Long> {

}
