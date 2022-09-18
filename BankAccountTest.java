import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class BankAccountTest {

    private final String[] IDS = {"aaa","bbb","ccc","ddd", "negative", "eee"};
    private final double[] BALANCES = {0.0, 10.0, 100.0, 1000.0, -10.0, 5000.0};

    private final double[] DEPOSITS = {3.0, 30.0, 25.50, 500, -5000, -300};
    private final double[] WITHDRAWLS = {10, 5, 500, 20, -4, -540};

    private ArrayList<BankAccount> baList;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        assert(IDS.length == BALANCES.length);

        baList = new ArrayList<>();
        for (int ndx = 0; ndx < IDS.length; ndx++) {
            BankAccount ba = new BankAccount(IDS[ndx], BALANCES[ndx]);
            baList.add(ba);
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        baList = null;
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        for (BankAccount bankAccount : baList) {
            System.out.println(bankAccount);
        }
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        assert(baList.size() == IDS.length &&
                IDS.length == BALANCES.length);
        for (BankAccount bankAccount : baList) {
            BankAccount shallowCopy = bankAccount; // both variables point to same object
            assertEquals(bankAccount,shallowCopy);
            BankAccount nullPointer = null;
            assertFalse(bankAccount.equals(nullPointer));
            assertFalse(bankAccount.equals("not a bank account"));
        }
        for (int ndx = 0; ndx < IDS.length; ndx++) {
            BankAccount ba = baList.get(ndx);
            BankAccount other_ba = new BankAccount(IDS[ndx], BALANCES[ndx]);
            assertNotSame(other_ba,ba);
            assertEquals(other_ba,ba);
            BankAccount unequal_ba = new BankAccount(IDS[ndx] + "x", BALANCES[ndx]);
            assertNotEquals(unequal_ba,ba);
        }
    }

    @org.junit.jupiter.api.Test
    void testHashCode() {
        assert(baList.size() == IDS.length &&
                IDS.length == BALANCES.length);
        /**
         * objects that are equal must also have hash codes that are equal
         */
        for (int ndx = 0; ndx < IDS.length; ndx++) {
            BankAccount ba = baList.get(ndx);
            BankAccount other_ba = new BankAccount(IDS[ndx], BALANCES[ndx]);
            assertNotSame(other_ba, ba);
            assertEquals(other_ba.hashCode(), ba.hashCode());
        }
    }

    @org.junit.jupiter.api.Test
    void getId() {
        assert(baList.size() == IDS.length);
        for (int ndx = 0; ndx < baList.size(); ndx++) {
            String the_id = baList.get(ndx).getId();
            String exp_id = IDS[ndx];
            assertEquals(exp_id,the_id);
        }
    }

    @org.junit.jupiter.api.Test
    void getBalance() {
        assert(baList.size() == BALANCES.length);
        for (int ndx = 0; ndx < baList.size(); ndx++) {
            double the_bal = baList.get(ndx).getBalance();
            double exp_bal = BALANCES[ndx] > 0 ? BALANCES[ndx] : 0;
            assertEquals(exp_bal,the_bal);
        }
    }

    @org.junit.jupiter.api.Test
    void deposit() {
        assert(DEPOSITS.length == baList.size());
        for (int ndx = 0; ndx < baList.size(); ndx++) {
            BankAccount ba = baList.get(ndx);
            ba.deposit(DEPOSITS[ndx]);
            double the_balance = ba.getBalance();
            double expected_balance = (BALANCES[ndx] > 0 ? BALANCES[ndx] : 0)
                    + (DEPOSITS[ndx] > 0 ? DEPOSITS[ndx] : 0);
            assertEquals(expected_balance,the_balance);
        }
    }

    @org.junit.jupiter.api.Test
    void withdraw() {
        assert(baList.size() == WITHDRAWLS.length);
        for (int ndx = 0; ndx < baList.size(); ndx++) {
            BankAccount ba = baList.get(ndx);
            double withdrawAmnt = WITHDRAWLS[ndx];
            ba.withdraw(withdrawAmnt);
            double actual_balance = ba.getBalance();
            double expected_balance = 0;
            if (BALANCES[ndx] > 0 && 0 < withdrawAmnt && withdrawAmnt <= BALANCES[ndx]) {
                expected_balance = BALANCES[ndx] - withdrawAmnt;
            } else if (BALANCES[ndx] < 0) {
                expected_balance = 0;
            } else if (withdrawAmnt > BALANCES[ndx] || withdrawAmnt < 0) {
                expected_balance = BALANCES[ndx];
            }
            assertEquals(expected_balance,actual_balance);
        }
    }
}