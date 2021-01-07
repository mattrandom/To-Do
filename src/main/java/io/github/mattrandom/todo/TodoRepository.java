package io.github.mattrandom.todo;

import io.github.mattrandom.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

class TodoRepository {

    List<Todo> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Todo> result = session.createQuery("from Todo", Todo.class).list();

        transaction.commit();
        session.close();

        return result;
    }
}
