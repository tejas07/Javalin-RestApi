package test.controller;

import io.javalin.Handler;
import test.dao.TxDaoTest;
import test.model.TransferAmountTest;
import test.model.UserTest;

import java.util.Objects;

public class TxControllerTest {
    public static Handler fetchAllUsernames = ctx -> {
        TxDaoTest dao = TxDaoTest.instance();
        Iterable<String> allUsers = dao.getAllUsernames();
        ctx.json(allUsers);
    };

    public static Handler fetchByAccountNo = ctx -> {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("accountno")));
        TxDaoTest dao = TxDaoTest.instance();
        UserTest userTest = dao.getUserById(id).orElse(null);
        if (userTest == null) {
            ctx.html("Not Found");
        } else {
            ctx.json(userTest);
        }
    };

    public static Handler transferMoney = ctx -> {
        TransferAmountTest transferAmountTest = ctx.bodyAsClass(TransferAmountTest.class);
        TxDaoTest dao = TxDaoTest.instance();
        UserTest debit = dao.getUserById(transferAmountTest.getFromAccNo()).orElse(null);
        UserTest credit = dao.getUserById(transferAmountTest.getToAccNo()).orElse(null);
        if (debit == null|| credit==null) {
            ctx.html("Account Number Not Found");
        } else if (debit.getAmount()< transferAmountTest.getAmount()){
            ctx.html("InsufficientBalance");
        }else {
            debit.setAmount(debit.getAmount()- transferAmountTest.getAmount());
            credit.setAmount(credit.getAmount()+ transferAmountTest.getAmount());
            ctx.json(credit);
        }
    };

    public static Handler saveUser = ctx -> {
        TxDaoTest dao = TxDaoTest.instance();
        UserTest saveUserTest = ctx.bodyAsClass(UserTest.class);
        UserTest userTest =dao.saveUser(saveUserTest);
        ctx.json(userTest);
    };
}