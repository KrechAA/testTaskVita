package com.krech.vita.repository;

import com.krech.vita.domain.db.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class RoleRepositoryImpl extends AbstractRepository implements RoleRepository{
    private final SessionFactory sessionFactory;

    @Autowired
    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Getting role by id
     * @param roleId
     * @return
     */
    public Role getRoleById(int roleId) {
        Role role;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query<Role> query = session.createNativeQuery("SELECT * FROM roles WHERE roles.id = " + roleId + ";", Role.class);

            role = query.getSingleResult();

            session.getTransaction().commit();
        }
        return role;
    }
}
