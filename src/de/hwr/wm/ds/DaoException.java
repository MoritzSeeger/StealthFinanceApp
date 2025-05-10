package de.hwr.wm.ds;

public class DaoException extends Exception {

    private static final long serialVersionUID = 1L;

    public DaoException(String arg0) {
        super("Exception in DAO: " + arg0);
    }

}