package controller;

import dao.TxDao;
import io.javalin.Handler;
import model.TransferAmount;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class TxController {
    static final Logger logger = LoggerFactory.getLogger(TxController.class);

    /*
    * GET API
    * @handler fetchAllUsernames
    * List all the api to fetch account details
    * */
    public static Handler fetchAllUsernames = ctx -> {
        logger.info("Inside fetching all account holders name");
        TxDao dao = TxDao.instance();
        List<String> allUsers = dao.getAllUsernames();
        logger.info("total account holders "+allUsers.size());
        ctx.json(allUsers);
    };
    /*
    *GET API
    * @handler fetchByAccountNo
    * @pathParam account number is required
    * */
    public static Handler fetchByAccountNo = ctx -> {
        logger.info("fetching account info account number");
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("accountno")));
        TxDao dao = TxDao.instance();
        User user = dao.getUserById(id).orElse(null);
        if (user == null) {
            logger.info("No account info found");
            ctx.html("Not Found");
        } else {
            logger.info("account info found for a/c no"+user.getAccNo());
            ctx.json(user);
        }
    };

    /*
    * POST API
    * @handler transferMoney
    * In request body it is required to provide to & from a/c no and amount
    * to be tansfered.
    * It will terminate transfer if transfer happens between same a/c's
    * or if insufficient balance in transferee's a/c or if invalid transferer a/c.
    * */
    public static  Handler transferMoney = ctx -> {
        logger.info("transfer money handler");
        TransferAmount transferAmount= ctx.bodyAsClass(TransferAmount.class);
        TxDao dao = TxDao.instance();
        User debit = dao.getUserById(transferAmount.getFromAccNo()).orElse(null);
        User credit = dao.getUserById(transferAmount.getToAccNo()).orElse(null);
        if (debit == null|| credit==null) {
            logger.info("a/c 's debit="+debit+" & credit= "+credit);
            ctx.html("Oops!..Account Number Not Found");
        } else if (debit.getAmount()<transferAmount.getAmount() || debit.getAmount()==0){
            logger.info("balance in a/c debit="+debit.getAccNo()+" & balance= "+debit.getAmount());
            ctx.html("SNAP!.. InsufficientBalance");
        }else if(debit.getAccNo()==credit.getAccNo()){
            logger.info("Tx can't happen both have same a/c number");
            ctx.html("Oops !! Improper Transfer");
        }
        else {
            logger.info("Bingo...!! amount transfered");
            debit.setAmount(debit.getAmount()-transferAmount.getAmount());
            credit.setAmount(credit.getAmount()+transferAmount.getAmount());
            ctx.json(credit);
        }
    };
    /*
    * POST API
    * @handler saveUser required username & initial amount.
    * */
    public static Handler saveUser = ctx -> {
        logger.info("creating a/c");
        TxDao dao = TxDao.instance();
        User saveUser= ctx.bodyAsClass(User.class);
        if (saveUser.getAccName().isEmpty()|| saveUser.getAccName()==null){
            ctx.html("Account Name required");}
        else {
            User user = dao.saveUser(saveUser);
            logger.info("HOLA!.. a/c created with a/c no " + user.getAccNo());
            ctx.json(user);
        }
    };
}