package de.hwr.wm.ds;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public interface UserData {
    public ArrayList<Double> getBalanceUserArray() throws DaoException;

    public  ArrayList<Double> getBalanceUserArray(String customer_ID) throws DaoException; // Für Überweisung wichtig

    public void addBalanceFromBank(Double newBalance, LocalDate t_Date , String t_Sender) throws DaoException;

    public void uploadTransactionToDatabase(Double newBalance, String customer_ID, String message, String T_Receiver_ID, String T_Sender_ID ) throws DaoException;

    public ArrayList<String> getUser_TransactionMessage(String customer_ID) throws  DaoException;
    public ArrayList<String> getUser_T_Sender_ID(String customer_ID) throws DaoException;
    public String getIban() throws DaoException;
    public String getUser_Name() throws DaoException;
}
