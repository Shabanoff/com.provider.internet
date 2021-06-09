package com.provider.internet.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data

@RequiredArgsConstructor
public class Tariff {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String tariffName;
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Service service;

    @ManyToMany
    @JoinTable(
            name = "tariff_to_option",
            joinColumns = {@JoinColumn(name = "tariff_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_id")}
    )
    private Set<IncludedOption> includedOptions;

}
