package com.sensei.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WalletDto {

    private Long id;
    private Long userId;
    private boolean active;
    private List<Long> walletsCryptoList;
    private List<Long> trades;
}
