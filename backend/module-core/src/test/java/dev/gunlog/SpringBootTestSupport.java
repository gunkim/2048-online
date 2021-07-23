package dev.gunlog;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class SpringBootTestSupport {
    protected EntityManager entityManager;
    protected EntityTransaction transaction;

    public SpringBootTestSupport(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    protected <T> T save(T entity) {
        transaction.begin();

        try {
            entityManager.persist(entity);
            entityManager.flush();
            transaction.commit();
            entityManager.clear();
        } catch (Exception e) {
            transaction.rollback();
        }
        return entity;
    }
    protected <T> Iterable<T> saveAll(Iterable<T> entities) {
        transaction.begin();

        for (T entity : entities) {
            try {
                entityManager.persist(entity);
                entityManager.flush();
                transaction.commit();
                entityManager.clear();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
        return entities;
    }
}