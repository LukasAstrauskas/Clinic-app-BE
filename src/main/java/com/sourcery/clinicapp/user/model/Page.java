package com.sourcery.clinicapp.user.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
    private List<T> data;
    private int total;
}
