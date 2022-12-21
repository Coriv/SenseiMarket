package com.sensei.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WalletDto {

    private Long id;
    private Long userId;
    private boolean isActive;
    private List<Long> walletsCryptoList;
}
