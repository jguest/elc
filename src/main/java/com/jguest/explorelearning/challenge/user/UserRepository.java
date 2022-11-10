package com.jguest.explorelearning.challenge.user;

import com.jguest.explorelearning.challenge.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
}
