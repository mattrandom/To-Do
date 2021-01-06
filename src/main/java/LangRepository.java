import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class LangRepository {

    Optional<Lang> findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Optional<Lang> result = Optional.ofNullable(session.get(Lang.class, id));

        transaction.commit();
        session.close();

        return result;
    }
}
