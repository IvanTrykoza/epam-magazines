package com.my.command.container;

import com.my.jdbc.DBException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String execute(HttpServletRequest req, HttpServletResponse res) throws DBException;
}
