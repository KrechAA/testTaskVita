package com.krech.vita.repository;

import com.krech.vita.domain.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }


    /**
     * Get all users in base
     *
     * @return
     */
    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query<User> query = session.createNativeQuery("SELECT * FROM users;", User.class);
            userList = query.getResultList();

            session.getTransaction().commit();
        }
        return userList;
    }

    /**
     * Get all users whose name starts with the input string
     *
     * @param name
     * @return
     */
    @Override
    public List<User> getUserByName(String name) {
        List<User> userList;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query<User> query = session.createNativeQuery("SELECT * FROM users WHERE users.name LIKE '" + name + "%';", User.class);
            userList = query.getResultList();

            session.getTransaction().commit();
        }
        return userList;
    }

    /**
     *  Get user by login
     * @param login
     * @return
     */
    @Override
    public User getUserByLogin(String login) {
        User user;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query<User> query = session.createNativeQuery("SELECT * FROM users WHERE users.login = '" + login + "';", User.class);

             user = query.getSingleResult();

            session.getTransaction().commit();
        }
        return user;
    }


    /**
     * Getting user by id
     * @param userId
     * @return
     */
    @Override
    public User getUserById(Long userId) {
        User user;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query<User> query = session.createNativeQuery("SELECT * FROM users WHERE users.id = " + userId + ";", User.class);

            user = query.getSingleResult();

            session.getTransaction().commit();
        }
        return user;
    }

    /**
     * Updating user in base
     * @param user
     * @return
     */
    @Override
    public User updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.merge(user);
            session.getTransaction().commit();
        }
        return user;
    }


}
