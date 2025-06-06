package com.example.demo.entity;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "currency", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"})
})
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
}
