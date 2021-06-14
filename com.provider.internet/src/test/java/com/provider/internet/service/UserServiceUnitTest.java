package com.provider.internet.service;

import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Status;
import com.provider.internet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private TariffService tariffService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private IncludedPackageService includedPackageService;
    @InjectMocks
    private UserService userService;

    @Test
    public void findAll_worksCorrectly() {
        userService.findAll();

        verify(userRepository).findAll();
    }

    @Test
    public void findAllUsers_worksCorrectly() {

        PageRequest pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.unsorted());
        List<User> userList = Collections.emptyList();
        PageImpl<User> response = new PageImpl<>(userList, pageable, 1);

        when(userRepository.findAll(pageable)).thenReturn(response);

        userService.findAllUsers(pageable);

        verify(userRepository).findAll(pageable);
    }

    @Test
    public void updateUserStatusUserNotFound_worksCorrectly() {

        User user = new User();
        user.setId(1L);
        user.setStatus(Status.ACTIVE);

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        userService.updateUserStatus(user, Status.BLOCK);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void updateUserStatus_worksCorrectly() {

        User user = new User();
        user.setId(1L);
        user.setStatus(Status.ACTIVE);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.updateUserStatus(user, Status.BLOCK);

        ArgumentCaptor<User> saveCapture = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(saveCapture.capture());
        User actual = saveCapture.getValue();
        assertThat(actual.getStatus()).isEqualTo(Status.BLOCK);
    }

    @Test
    public void increaseUserBalance_worksCorrectly() {

        User user = new User();
        user.setId(1L);
        user.setBalance(BigDecimal.ZERO);
        user.setStatus(Status.BLOCK);


        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.increaseUserBalance(user.getId(), BigDecimal.ONE);

        ArgumentCaptor<User> saveCapture = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(saveCapture.capture());
        User actual = saveCapture.getValue();
        assertThat(actual.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(actual.getBalance()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void decreaseUserBalance_worksCorrectly() {
        User user = new User();
        user.setId(1L);
        user.setBalance(BigDecimal.ONE);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.decreaseUserBalance(user, BigDecimal.ONE);

        ArgumentCaptor<User> saveCapture = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(saveCapture.capture());
        User actual = saveCapture.getValue();
        assertThat(BigDecimal.ZERO).isEqualTo(actual.getBalance());
    }
}
