import controller.TxController;
import io.javalin.Javalin;

public class RestApi{
    public static void main(String[] args) {
        Javalin app = Javalin.create().disableStartupBanner().start(7000);
        app.get("/fetch-account-holders", TxController.fetchAllUsernames);
        app.get("/fetch-individual-account/:accountno", TxController.fetchByAccountNo);
        app.post("/transfer-money",TxController.transferMoney);
        app.post("/create-account",TxController.saveUser);
    }
}
