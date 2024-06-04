package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("update from User set login = :fLogin, password = :fPassword where id = :fId")
                    .setParameter("fId", user.getId())
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        }
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("delete from User where id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        List<User> userList = null;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            userList = session.createQuery("from User order by id", User.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        }
        if (userList == null) {
            return List.of();
        }
        return List.copyOf(userList);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        User user = null;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            user = session.createQuery("from User where id = :fId", User.class)
                    .setParameter("fId", userId)
                    .getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        }
        return Optional.of(user);
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        List<User> userList = null;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            userList = session.createQuery("from User where login like :fKey", User.class)
                    .setParameter("fKey", "%" + key + "%")
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        }
        if (userList == null) {
            return List.of();
        }
        return List.copyOf(userList);
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        User user = null;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            user = session.createQuery("from User where login = :fLogin", User.class)
                    .setParameter("fLogin", login)
                    .getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        }
        return Optional.of(user);
    }
}
