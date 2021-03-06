package com.my;


import com.my.filter.AuthenticationFilter;
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

public class AuthenticationTest {
    @InjectMocks
    private AuthenticationFilter authenticationFilter;

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

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoggedIn() throws Exception {
        when(servletRequest.getSession(false)).thenReturn(session);
        when(session.getAttribute("loggedUser")).thenReturn(new User());
        when(servletRequest.getParameter("command")).thenReturn("accountInfo");
        authenticationFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testNonLoggedIn() throws Exception {
        when(servletRequest.getSession(false)).thenReturn(null);
        when(servletRequest.getParameter("command")).thenReturn("showAllMagazine");
        authenticationFilter.init(filterConfig);
        authenticationFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain).doFilter(servletRequest, servletResponse);
    }
}
