package com.accenture.flowershop.be.access.user;

import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.user.User;

import jooq.flyway.db.h2.public_.tables.records.UserRecord;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static jooq.flyway.db.h2.public_.Tables.USER;

@Repository
public class UserDAOImpl implements UserDAO { //extends DAOImpl<UserRecord, User, Integer> { //implements UserDAO {

    private Connection con;

    private Connection getConnection() {
        try {
            con = DriverManager.getConnection("jdbc:h2:~/flowershop", "sa", "");
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void addUser(User user) {
        DSL.using(getConnection()).insertInto(USER)
                .set(USER.EMAIL, user.getEmail())
                .set(USER.ROLE, user.getRole())
                .set(USER.USERNAME, user.getUsername())
                .set(USER.PASSWORD, user.getPassword()).execute();
    }

    public User findUserById(int userId) {
        User user = DSL.using(getConnection()).selectFrom(USER)
                .where(USER.ID.eq(userId)).fetchAny().into(User.class);
        return user;
    }

    public User findUserByUsername(String username) {
        List<User> user = DSL.using(getConnection()).selectFrom(USER)
                .where(USER.USERNAME.eq(username)).fetch().into(User.class);
        if (user.isEmpty()) {
            return null;
        }
        return user.get(0);

      /* User user =  DSL.using(getConnection()).select(USER.USERNAME,CUSTOMER.).from(USER)
                .join(CUSTOMER)
                .on(CUSTOMER.USER_ID.equal(USER.ID))
                .where(USER.ID.eq(3)).fetchAny().into(User.class);
    return user;*/


    }

    public List<User> getAllUsers() {
        List<User> users = DSL.using(getConnection()).selectFrom(USER).fetch().into(User.class);
        return users;
    }


    // @PersistenceContext
    // EntityManager entityManager;


    /*@Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUserById(long userId) {
        TypedQuery<User> q = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class
        );
        q.setParameter("id", userId);
        //User user = q.getSingleResult();
        return q.getSingleResult();
    }

    @Override
    public User findUserByUsername(String username) {
        *//*TypedQuery<User> q = entityManager.createQuery(
                "select u from User u where u.username = :username", User.class
        );
        q.setParameter("username", username);
        return q.getSingleResult();*//*
        TypedQuery<User> q = entityManager.createQuery(
                "select u from User u where u.username = :username", User.class
        );
        q.setParameter("username", username);
        //q.setMaxResults(1);
        List<User> users = q.getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery(
                "select u from User u", User.class
        ).getResultList();
    }*/
}
