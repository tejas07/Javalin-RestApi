package dao;

import model.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class TxDao {
    private static TxDao txDao = null;
    private List<User> users = new CopyOnWriteArrayList<>();

    private TxDao() {
    }

    public static TxDao instance() {
        if (txDao == null) {
            txDao = new TxDao();
        }
        return txDao;
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getAccNo() == id).findFirst();
    }


    public Iterable<String> getAllUsernames() {
        return users.stream()
                .map(user -> user.getAccName())
                .collect(Collectors.toList());
    }

    public User saveUser(User user) {
        try {
            int index = users.get(users.size() - 1).getAccNo();
            user.setAccNo(++index);
        } catch (NullPointerException e) {
        } catch (ArrayIndexOutOfBoundsException e1) {
            user.setAccNo(1);
        }
        users.add(user);
        return user;
    }
}
