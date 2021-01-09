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

    Todo toggleTodo(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Todo result = session.get(Todo.class, id);
        result.setDone(!result.isDone());

        transaction.commit();
        session.close();

        return result;
    }
}
