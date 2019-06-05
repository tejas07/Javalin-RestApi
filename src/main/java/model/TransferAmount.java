package model;

import lombok.Data;

@Data
public class TransferAmount {
    Integer fromAccNo;
    Integer toAccNo;
    Double amount;
}
