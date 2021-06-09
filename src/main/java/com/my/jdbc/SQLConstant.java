package com.my.jdbc;

public interface SQLConstant {

    //User
    String USER_ID = "user_id";
    String USER_LOGIN = "login";
    String USER_ROLE_ID = "role_id";
    String USER_PASSWORD = "password";
    String USER_NAME = "user_name";
    String USER_STATUS = "status";
    String USER_WALLET = "wallet";

    //Magazine
    String MAGAZINE_ID = "magazine_id";
    String MAGAZINE_NAME = "magazine_name";
    String MAGAZINE_DESCRIPTION = "description";
    String MAGAZINE_CATEGORY_ID = "category_id";
    String MAGAZINE_CATEGORY_NAME = "category_name";
    String MAGAZINE_PRICE = "price";

    String CATEGORY_ID = "category_id";
    String CATEGORY_NAME = "category_name";

    //Receipt
    String RECEIPT_TOTAL_PRICE = "total_price";
    String RECEIPT_USER_ID = "user_id";
    String RECEIPT_STATUS_ID = "status_id";

    //Receipt-HAS-Product
    String R_HAS_P_RECEIPT_ID = "receipt_id";
    String R_HAS_P_PRODUCT_ID = "product_id";

    //Role-Constants
    int CUSTOMER_ROLE_ID = 2;
    int ADMIN_ROLE_ID = 1;

    //Status-Constants
    int VALUE_STATUS_TRUE = 1;
    int VALUE_STATUS_FALSE = 0;


    //Query to user table
    String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    String FIND_USER_BY_ID = "SELECT * FROM user WHERE user_id=?";
    String FIND_ALL_USERS = "SELECT * FROM user WHERE role_id=2 LIMIT ?, ?";
    String ADD_NEW_USER = "INSERT INTO user (login, password, user_name, role_id) VALUES (?, ?, ?, ?)";
    String SET_USER_BALANCE = "UPDATE user SET wallet=? WHERE user_id=?";
    String GET_USER_BALANCE = "SELECT wallet FROM user WHERE user_id=?";
    String GET_USER_STATUS = "SELECT status FROM user WHERE user_id=?";
    String SET_USER_STATUS = "UPDATE user SET status=? WHERE user_id=?";
    String GET_AMOUNT_OF_ALL_USERS = "SELECT COUNT(user_id) FROM user WHERE role_id=2";


    //Query to magazine table
    String GET_AMOUNT_OF_MAGAZINES_FOUND_BY_CATEGORY = "SELECT COUNT(magazine_id) FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE c.category_name=?";
    String GET_AMOUNT_OF_ALL_MAGAZINES = "SELECT COUNT(magazine_id) FROM magazine";
    String GET_AMOUNT_OF_MAGAZINES_FOUND_BY_NAME = "SELECT COUNT(magazine_id) FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE magazine_name LIKE ?";

    String FIND_ALL_MAGAZINES = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id LIMIT ?, ?";                                 // *
    String FIND_MAGAZINES_BY_NAME = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE magazine_name LIKE ? LIMIT ?, ?";           // *
    String FIND_MAGAZINES_BY_CATEGORY = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE c.category_name=? LIMIT ?, ?";      // *

    String DELETE_MAGAZINE = "DELETE FROM magazine WHERE magazine_id=?";
    String GET_CATEGORY = "SELECT * FROM category";
    String SET_MAGAZINE_INFO = "UPDATE magazine SET magazine_name=?, description=?, category_id=?, price=? WHERE magazine_id=?";
    String SET_CATEGORY = "INSERT INTO category (category_name) VALUES (?)";
    String ADD_NEW_MAGAZINE = "INSERT INTO magazine (magazine_name, description, category_id, price) VALUES (?, ?, ?, ?)";

    String SORT_MAGAZINES_BY_NAME_ALL_REQUEST = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id ORDER BY magazine_name ASC LIMIT ?, ?";
    String SORT_MAGAZINES_BY_NAME_SEARCH_REQUEST = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE magazine_name LIKE ? ORDER BY magazine_name ASC LIMIT ?, ?";
    String SORT_MAGAZINES_BY_NAME_CATEGORY_REQUEST = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE c.category_name=? ORDER BY magazine_name ASC LIMIT ?, ?";

    String SORT_MAGAZINES_BY_PRICE_ALL_REQUEST_LH = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id ORDER BY price ASC LIMIT ?, ?";
    String SORT_MAGAZINES_BY_PRICE_SEARCH_REQUEST_LH = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE magazine_name LIKE ? ORDER BY price ASC LIMIT ?, ?";
    String SORT_MAGAZINES_BY_PRICE_CATEGORY_REQUEST_LH = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE c.category_name=? ORDER BY price ASC LIMIT ?, ?";

    String SORT_MAGAZINES_BY_PRICE_ALL_REQUEST_HL = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id ORDER BY price DESC LIMIT ?, ?";
    String SORT_MAGAZINES_BY_PRICE_SEARCH_REQUEST_HL = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE magazine_name LIKE ? ORDER BY price DESC LIMIT ?, ?";
    String SORT_MAGAZINES_BY_PRICE_CATEGORY_REQUEST_HL = "SELECT * FROM magazine m JOIN category c ON m.category_id=c.category_id WHERE c.category_name=? ORDER BY price DESC LIMIT ?, ?";

}