package com.my.command.commandContainer;

import com.my.jdbc.exception.DBException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String execute(HttpServletRequest req, HttpServletResponse res) throws DBException;
}
