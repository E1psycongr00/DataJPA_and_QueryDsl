package com.example.mysqltest.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@ToString
public class UserDto {
    private String nickname;
    private int age;
    
}
