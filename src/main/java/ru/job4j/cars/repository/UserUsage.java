package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.User;


public class UserUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            User user = new User();

            user.setLogin("Login");
            user.setPassword("Password");
            var userRepository = new UserRepository(new CrudRepository(sf));
            userRepository.create(user);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}