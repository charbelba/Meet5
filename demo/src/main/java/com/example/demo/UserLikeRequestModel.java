package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class UserLikeRequestModel {
    private Long userAId;
    private Long userBId;
    private LocalDateTime likeTime;
}
