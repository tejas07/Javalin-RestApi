package test.model;

import lombok.Data;

@Data
public class TransferAmountTest {
    Integer fromAccNo;
    Integer toAccNo;
    Double amount;
}
