package com.provider.internet.service;

import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Status;
import com.provider.internet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Intermediate layer between command layer and dao layer. Implements operations of finding,
 * creating, deleting entities. Account dao layer.
 *
 * @author Shabanoff
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final TariffService tariffService;
    private final UserRepository userRepository;
    private final IncludedPackageService includedPackageService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findUserById(long userId) {
        return userRepository.findById(userId);
    }

    public void createUser(User user) {
        Objects.requireNonNull(user);
        userRepository.save(user);
        log.info("Create user successfully");
    }

    public void updateUserStatus(User user, Status status) {
        Optional<User> currentUserOpt = findUserById(user.getId());
        if (currentUserOpt.isPresent()) {
            User currentUser = currentUserOpt.get();
            currentUser.setStatus(status);
            userRepository.save(user);
            log.info("Update status successfully");
        }
    }

    public List<String> updateUserTariff(long userId, long tariffId) {
        List<String> error = new ArrayList<>();
        User currentUser = userRepository.getById(userId);
        Optional<Tariff> tariffOptional = tariffService.findTariffById(tariffId);
        if (tariffOptional.isPresent()) {
            Tariff tariff = tariffOptional.get();
            if (currentUser.getBalance().compareTo(tariff.getCost()) <= 0) {
                error.add("no.money");
                log.info("User hasn't money to this tariff");
                return error;
            }
            if (includedPackageService.existsByUserIdAndTariffId(currentUser, tariff)) {
                error.add("already.add");
                log.info("User already add this tariff");
                return error;
            }
            IncludedPackage includedPackage;
            if (includedPackageService.findByUserIdAndServiceId(currentUser, tariff.getService()).isPresent()) {
                includedPackage = includedPackageService.findByUserIdAndServiceId(currentUser, tariff.getService()).get();
            } else {
                includedPackage = new IncludedPackage();
                includedPackage.setService(tariff.getService());
            }
            includedPackage.setTariff(tariff);
            includedPackage.setUser(currentUser);
            includedPackage.setSubscriptionDate(LocalDate.now());
            includedPackageService.updateIncludePackage(includedPackage);
            decreaseUserBalance(currentUser, tariff.getCost());
        }
        log.info("User add tariff successfully");
        return error;
    }

    public User increaseUserBalance(long userId, BigDecimal amount) {
        Optional<User> currentUserOpt = findUserById(userId);
        if (currentUserOpt.isPresent()) {
            User currentUser = currentUserOpt.get();
            currentUser.setBalance(currentUser.getBalance().add(amount));
            if (currentUser.getStatus().equals(Status.BLOCK)) {
                currentUser.setStatus(Status.ACTIVE);
            }
            userRepository.save(currentUser);
            return currentUser;
        }
        return null;
    }

    public void decreaseUserBalance(User user, BigDecimal amount) {
        Optional<User> currentUserOpt = findUserById(user.getId());
        if (currentUserOpt.isPresent()) {
            User currentUser = currentUserOpt.get();
            if (currentUser.getBalance().compareTo(amount) >= 0) {
                currentUser.setBalance(currentUser.getBalance().subtract(amount));
                userRepository.save(user);
            }
        }
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByLogin(login);
        if (user.isPresent()) {
            log.info("User is present");
            return user.get();
        }
        return null;
    }
}

