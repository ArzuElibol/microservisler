package com.example.productservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendlyMessage {
    private String title;
    private String descrption;
    private String buttonPositive;
}
