package com.my;


import com.my.filter.AuthorizationFilter;
import com.my.jdbc.constatants.SQLConstant;
import com.my.jdbc.entity.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorizationTest {
    @InjectMocks
    private AuthorizationFilter authorizationFilter;

    @Mock
    private HttpSession session;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    @Mock
    private FilterConfig filterConfig;

    private User user;

    private User admin;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        admin = new User();
        admin.setRoleId(SQLConstant.ADMIN_ROLE_ID);
    }

    @Test
    public void testAdmin() throws Exception {
        when(servletRequest.getSession()).thenReturn(session);
        when(servletRequest.getSession(false)).thenReturn(session);
        when(session.getAttribute("loggedUser")).thenReturn(admin);
        when(servletRequest.getParameter("command")).thenReturn("showAllUsers");
        authorizationFilter.init(filterConfig);
        authorizationFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testUser() throws Exception {
        when(servletRequest.getSession()).thenReturn(session);
        when(servletRequest.getSession(false)).thenReturn(session);
        when(session.getAttribute("loggedUser")).thenReturn(user);
        when(servletRequest.getParameter("command")).thenReturn("showAllUsers");
        authorizationFilter.init(filterConfig);
        authorizationFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
