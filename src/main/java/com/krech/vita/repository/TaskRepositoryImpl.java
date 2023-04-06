package com.krech.vita.repository;

import com.krech.vita.domain.rest.response.PaginationResult;
import com.krech.vita.domain.db.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

@Repository
public class TaskRepositoryImpl extends AbstractRepository implements TaskRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TaskRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * This method returned all tasks of user.
     * @param pageNumber
     * @param pageSize
     * @param ordering
     * @param userId
     * @return
     */
    public PaginationResult<Task> getAllTasksByUser(int pageNumber, int pageSize, String ordering, long userId) {
        int lastPageNumber;
        Long totalRecords;
        List<Task> taskList;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            TypedQuery<Long> countQuery = session.createNativeQuery("SELECT COUNT (tasks.id) FROM tasks;", Long.class);
            totalRecords = countQuery.getSingleResult();
            lastPageNumber = calculatingLastPageNumberForPagination(pageSize, totalRecords);

            session.getTransaction().commit();
        }
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            TypedQuery<Task> query = session.createNativeQuery("SELECT * FROM tasks JOIN users ON tasks.author = user.id WHERE user.id = " + userId + " ORDER BY tasks.date " + ordering + ";",
                    Task.class);

            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            taskList = query.getResultList();

            session.getTransaction().commit();
        }

        PaginationResult<Task> result = new PaginationResult<>();
        result.setCurrentPageNumber(pageNumber);
        result.setPageSize(pageSize);
        result.setLastPageNumber(lastPageNumber);
        result.setTotalRecords(totalRecords);
        result.setRecords(taskList);

        return result;


    }

    /**
     * All sent tasks of user.
     * @param pageNumber
     * @param pageSize
     * @param ordering
     * @param userName
     * @return
     */
    public PaginationResult<Task> getSentTasksByUser(int pageNumber, int pageSize, String ordering, String userName) {
        int lastPageNumber;
        long totalRecords;
        List<Task> taskList;
        BigInteger bigInteger;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            NativeQuery countQuery = session.createNativeQuery("SELECT COUNT(tasks.id) FROM tasks JOIN users ON tasks.author = users.id WHERE users.name LIKE '" + userName + "%' GROUP BY users.name;");
            bigInteger = (BigInteger) countQuery.getSingleResult();
            totalRecords = bigInteger.longValue();
            lastPageNumber = calculatingLastPageNumberForPagination(pageSize, totalRecords);


            TypedQuery<Task> query = session.createNativeQuery("SELECT * FROM tasks JOIN users ON tasks.author = users.id WHERE users.name LIKE '" + userName + "%' ORDER BY tasks.date " + ordering,
                    Task.class);

            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            taskList = query.getResultList();

            session.getTransaction().commit();
        }

        PaginationResult<Task> result = new PaginationResult<>();
        result.setCurrentPageNumber(pageNumber);
        result.setPageSize(pageSize);
        result.setLastPageNumber(lastPageNumber);
        result.setTotalRecords(totalRecords);
        result.setRecords(taskList);

        return result;

    }

    /**
     * Searching task by id
     * @param taskId
     * @return
     */
    @Override
    public Task getTaskById(long taskId) {
        try (Session session = sessionFactory.openSession()) {

            return session.get(Task.class, taskId);
        }
    }

    /**
     * Creating task
     * @param task
     * @return
     */

    public Task createTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(task);
            session.getTransaction().commit();
            return task;

        }

    }

    /**
     * Updating task in repo
     * @param task
     * @return
     */

    public Task updateTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.merge(task);
            session.getTransaction().commit();
            return task;
        }
    }

    /**
     * Return all sent tasks.
     * @param pageNumber
     * @param pageSize
     * @param ordering
     * @return
     */
    public PaginationResult<Task> getAllSentTasks(int pageNumber, int pageSize, String ordering) {
        int lastPageNumber;
        long totalRecords;
        List<Task> taskList;
        BigInteger bigInteger;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            NativeQuery countQuery = session.createNativeQuery("SELECT COUNT(tasks.id) FROM tasks WHERE tasks.status = 'sent';");

            bigInteger = (BigInteger) countQuery.getSingleResult();
            totalRecords = bigInteger.longValue();
            lastPageNumber = calculatingLastPageNumberForPagination(pageSize, totalRecords);

            TypedQuery<Task> query = session.createNativeQuery("SELECT * FROM tasks WHERE tasks.status = 'sent' ORDER BY tasks.date " + ordering,
                    Task.class);
            if(pageNumber != 0){
                query.setFirstResult((pageNumber - 1) * pageSize);
            }else{
                query.setFirstResult((pageNumber) * pageSize);
            }

            query.setMaxResults(pageSize);
            taskList = query.getResultList();

            session.getTransaction().commit();
        }

        PaginationResult<Task> result = new PaginationResult<>();
        result.setCurrentPageNumber(pageNumber);
        result.setPageSize(pageSize);
        result.setLastPageNumber(lastPageNumber);
        result.setTotalRecords(totalRecords);
        result.setRecords(taskList);

        return result;
    }

    /**
     * Calculation number of last page for pagination
     * @param pageSize
     * @param totalRecords
     * @return
     */
    private int calculatingLastPageNumberForPagination(int pageSize, long totalRecords) {
        int lastPageNumber;
        if (totalRecords % pageSize == 0) {
            lastPageNumber = (int) (totalRecords / pageSize);
        } else {
            lastPageNumber = (int) (totalRecords / pageSize) + 1;
        }
        return lastPageNumber;
    }


}


