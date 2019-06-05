import controller.TxController;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestApi{
    static final Logger LOGGER = LoggerFactory.getLogger(RestApi.class);
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.get("/fetch-account-holders", TxController.fetchAllUsernames);
        app.get("/fetch-individual-account/:accountno", TxController.fetchByAccountNo);
        app.post("/transfer-money",TxController.transferMoney);
        app.post("/create-account",TxController.saveUser);

    }
}
