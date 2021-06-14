package com.provider.internet.controller.scheduler;

import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Status;
import com.provider.internet.service.IncludedPackageService;
import com.provider.internet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final UserService userService ;
    private final IncludedPackageService includedPackageService;
    @Scheduled(cron = "0 12 * * ?")
    public void scheduledMethod() {
        int currentDay = LocalDate.now().getDayOfMonth();
        BigDecimal cost;
        List<User> users =
                userService.findAll();

        users = users.stream().filter(user -> Status.ACTIVE.equals(user.getStatus())).collect(Collectors.toList());

        for (User user : users) {
            if (user.getStatus().equals(Status.ACTIVE)) {
                for (IncludedPackage includedPackage : includedPackageService.findByUser(user.getId())) {
                    cost = includedPackage.getTariff().getCost();
                    if (user.getBalance().compareTo(cost) >= 0) {
                        if (includedPackage.getSubscriptionDate().getDayOfMonth() >= currentDay ||
                                includedPackage.getSubscriptionDate().getDayOfMonth() == currentDay) {
                            userService.decreaseUserBalance(user, cost);
                        }
                    } else {
                        userService.updateUserStatus(user, Status.BLOCK);
                    }

                }
            }
        }
    }
}
