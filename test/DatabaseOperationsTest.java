import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperationsTest {
    private static DatabaseOperations dbOperations;
    private static Connection mockConnection;
    private static PreparedStatement mockPreparedStatement;
    private static PreparedStatement mockPreparedStatement2;

    @BeforeClass
    public static void setUp() throws SQLException {
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
}