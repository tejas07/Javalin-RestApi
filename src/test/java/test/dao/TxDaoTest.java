package test.dao;

import test.model.UserTest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class TxDaoTest {
    private static TxDaoTest txDao = null;
    private List<UserTest> userTests = new CopyOnWriteArrayList<>();

    private TxDaoTest() {
    }

    public static TxDaoTest instance() {
        if (txDao == null) {
            txDao = new TxDaoTest();
        }
        return txDao;
    }

    public Optional<UserTest> getUserById(int id) {
        return userTests.stream().filter(u -> u.getAccNo() == id).findFirst();
    }


    public Iterable<String> getAllUsernames() {
        return userTests.stream()
                .map(userTest -> userTest.getAccName())
                .collect(Collectors.toList());
    }

    public UserTest saveUser(UserTest userTest) {
        try {
            int index = userTests.get(userTests.size() - 1).getAccNo();
            userTest.setAccNo(++index);
        } catch (NullPointerException e) {
        } catch (ArrayIndexOutOfBoundsException e1) {
            userTest.setAccNo(1);
        }
        userTests.add(userTest);
        return userTest;
    }
}
