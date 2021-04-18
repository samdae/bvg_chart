package com.braveg.chart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitDto {
    private int visit_id;
    private String visit_ip;
    private String visit_time;
    private String visit_refer;
    private String visit_agent;
}
