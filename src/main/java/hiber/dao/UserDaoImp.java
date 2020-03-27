package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserBySeries(int series) {
        try {
            TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User  WHERE car.series = :seriesParam ").setParameter("seriesParam", series);
            User user = query.getResultList().get(0);
            return user;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("User with this series is not exist on base");
            return null;
        }
    }
}
