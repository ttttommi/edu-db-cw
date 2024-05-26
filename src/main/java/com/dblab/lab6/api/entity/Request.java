package com.dblab.lab6.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(nullable = false)
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "datafile_id")
    @JsonIgnore
    private Datafile datafile;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    private String description;

    @Column(nullable = false)
    private String format;
}
