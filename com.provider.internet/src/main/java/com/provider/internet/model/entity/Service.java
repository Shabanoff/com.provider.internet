package com.provider.internet.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serviceName;

    @OneToMany(mappedBy = "service", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Tariff> tariffs;


}
