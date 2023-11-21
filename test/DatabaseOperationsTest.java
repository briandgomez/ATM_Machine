import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseOperationsTest {
    private static DatabaseOperations dbOperations;
    private static Connection mockConnection;
    private static PreparedStatement mockPreparedStatement;
    private static PreparedStatement mockPreparedStatement2;
    private static ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        // simulate connection
        mockConnection = mock(Connection.class);
        // simulate preparedstatements
        mockPreparedStatement = mock(PreparedStatement.class);
        mockPreparedStatement2 = mock(PreparedStatement.class);

        // check preparedstatements have been executed successfully
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement, mockPreparedStatement2);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement2.executeUpdate()).thenReturn(2);

        // initialize db
        dbOperations = new DatabaseOperations(mockConnection);
    }

    @Test
    public void checkDataHasBeenAddedDatabase() throws SQLException {
        dbOperations.performDatabaseOperations();
        // check data insertion into db was successful
        verify(mockConnection).prepareStatement(
                "INSERT INTO atm_machine_sample_table (primary_account_number, pin, balance, date) VALUES (?, ?, ?, ?)");
        verify(mockConnection).prepareStatement(
                "INSERT INTO atm_machine_sample_table_2 (primary_account_number, pin, balance, date) VALUES (?, ?, ?, ?)");

        // check that the insertion to db statements were called once
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement2, times(1)).executeUpdate();

    }

    @Test
    public void testGetCurrentBalanceForValidCardNumber() throws SQLException {
        long validCardNumber = 1234567891;
        long expectedBalance = 9500L;

        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("balance")).thenReturn((expectedBalance));

        long actualBalance = dbOperations.getCurrentBalance(validCardNumber);
        System.out.println(actualBalance);
        String expectedSql = "SELECT balance FROM atm_machine_sample_table WHERE primary_account_number = ?";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setLong(1, validCardNumber);

        verify(mockPreparedStatement).executeQuery();

        verify(mockResultSet).next();
        verify(mockResultSet).getLong("balance");

        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void checkWithdrawUpdatesBalanceCorrectly() throws SQLException {
        long validCardNumber = 1234567891L;
        long initialBalance = 9500;
        long withdrawAmount = 2445;
        long newBalance = initialBalance - withdrawAmount;

        // New preparedstatement created
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("balance")).thenReturn(initialBalance);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        long result = dbOperations.Withdraw(validCardNumber, withdrawAmount, true);

        String expectedSql = "UPDATE atm_machine_sample_table SET balance = ? WHERE primary_account_number = ?";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setLong(1, newBalance);
        verify(mockPreparedStatement).setLong(2, validCardNumber);

        assertEquals(newBalance, result);
    }

        @Test
    public void checkDepositUpdatesBalanceCorrectly() throws SQLException {
        long validCardNumber = 1234567891L;
        long initialBalance = 9500;
        long depositAmount = 2000;
        long newBalance = initialBalance + depositAmount;

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("balance")).thenReturn(initialBalance);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        long result = dbOperations.Deposit(validCardNumber, depositAmount, true);

        String expectedSql = "UPDATE atm_machine_sample_table SET balance = ? WHERE primary_account_number = ?";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setLong(1, newBalance);
        verify(mockPreparedStatement).setLong(2, validCardNumber);

        assertEquals(newBalance, result);
    }
}