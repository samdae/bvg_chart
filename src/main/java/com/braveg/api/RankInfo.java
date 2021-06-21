package com.braveg.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RankInfo {
    private int rank;
    private String title;
    private String artist;
    //private LocalDateTime dateTime;
    private int dateTime;
}
