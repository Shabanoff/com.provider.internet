package com.provider.internet.repository;

import com.provider.internet.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPaginationRepository extends PagingAndSortingRepository<User, Long> {

}
