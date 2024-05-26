package com.dblab.lab6.api.dto;

import com.dblab.lab6.api.entity.Datafile;
import com.dblab.lab6.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    private String message;

    private LocalDate requestDate;

    private User user;

    private Datafile datafile;

    private String name;

    private String content;

    private String description;

    private String format;
}
