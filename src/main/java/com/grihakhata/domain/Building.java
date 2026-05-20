package com.grihakhata.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(name = "buildings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building extends BaseEntity {
    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 255)
    private String addressLine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PropertyType propertyType;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Unit> units = new ArrayList<>();
}
