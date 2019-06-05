package controller;

import dao.TxDao;
import io.javalin.Handler;
import model.TransferAmount;
import model.User;

import java.util.Objects;

public class TxController {
    public static Handler fetchAllUsernames = ctx -> {
        TxDao dao = TxDao.instance();
        Iterable<String> allUsers = dao.getAllUsernames();
        ctx.json(allUsers);
    };

    public static Handler fetchByAccountNo = ctx -> {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("accountno")));
        TxDao dao = TxDao.instance();
        User user = dao.getUserById(id).orElse(null);
        if (user == null) {
            ctx.html("Not Found");
        } else {
            ctx.json(user);
        }
    };

    public static Handler transferMoney = ctx -> {
        TransferAmount transferAmount= ctx.bodyAsClass(TransferAmount.class);
        TxDao dao = TxDao.instance();
        User debit = dao.getUserById(transferAmount.getFromAccNo()).orElse(null);
        User credit = dao.getUserById(transferAmount.getToAccNo()).orElse(null);
        if (debit == null|| credit==null) {
            ctx.html("Account Number Not Found");
        } else if (debit.getAmount()<transferAmount.getAmount() || debit.getAmount()==0){
            ctx.html("InsufficientBalance");
        }else if(debit.getAccNo()==credit.getAccNo()){
            ctx.html("Improper Transfer");
        }
        else {
            debit.setAmount(debit.getAmount()-transferAmount.getAmount());
            credit.setAmount(credit.getAmount()+transferAmount.getAmount());
            ctx.json(credit);
        }
    };

    public static Handler saveUser = ctx -> {
        TxDao dao = TxDao.instance();
        User saveUser= ctx.bodyAsClass(User.class);
        User user=dao.saveUser(saveUser);
        ctx.json(user );
    };
}