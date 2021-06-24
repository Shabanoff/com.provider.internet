package com.provider.internet.service;

import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Status;
import com.provider.internet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserService implements UserDetailsService {
    private final TariffService tariffService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
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

    public Optional<User> findByLogin(String login) {
        return userRepository.getUserByLogin(login);

    }

    public void createUser(User user) {
        Objects.requireNonNull(user);
        userRepository.save(user);
    }

    public void updateUserStatus(User user, Status status) {
        Optional<User> currentUserOpt = findUserById(user.getId());
        if (currentUserOpt.isPresent()) {
            User currentUser = currentUserOpt.get();
                currentUser.setStatus(status);
            userRepository.save(user);
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
                return error;
            }
            if (includedPackageService.existsByUserIdAndTariffId(currentUser, tariff)) {
                error.add("already.add");
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
            this.decreaseUserBalance(currentUser, tariff.getCost());
        }
        return error;
    }

    public User increaseUserBalance(long userId, BigDecimal amount) {
        Optional<User> currentUserOpt = findUserById(userId);
        if (currentUserOpt.isPresent()) {
            User currentUser = currentUserOpt.get();
            currentUser.setBalance(currentUser.getBalance().add(amount));
            if (currentUser.getStatus().equals(Status.BLOCK)){
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

    public boolean isCredentialsValid(String login, String password) {
        Optional<User> user = userRepository.getUserByLogin(login);

        return user
                .filter(u -> bCryptPasswordEncoder.encode(
                        password).equals(u.getPassword()))
                .isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByLogin(s);
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }
}

