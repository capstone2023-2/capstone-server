package com.capstone.demo.model.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "topics")
@Getter
public class Topic {

    @Id
    private int id;
    private String topic;
    private String uri;
}