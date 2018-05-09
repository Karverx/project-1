package ru.levelp.myproject.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;

import static junit.framework.TestCase.assertNotNull;

public class RoleTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setup(){
        emf = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        em = emf.createEntityManager();
    }

    @After
    public void end(){
        em.close();
        emf.close();
    }

    @Test
    public void testCreateRole() throws Throwable {
        Role role = new Role("Разработчик");
        em.getTransaction().begin();
        try {
            em.persist(role);
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw t;
        } finally {
            em.getTransaction().commit();
        }
    }

    @Test
    public void testCreateRoleWithUser() throws Throwable {
        Role role = new Role("Разработчик");
        User user = new User();
        user.setName("Jack Smith");

        user.setRoles(Collections.singletonList(role));

        em.getTransaction().begin();
        try {
            em.persist(role);
            em.persist(user);
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw t;
        } finally {
            em.getTransaction().commit();
        }

        User found = em.find(User.class, user.getId());
        assertNotNull(found);
        assertNotNull(found.getRoles());

        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getRoles());
    }

}
