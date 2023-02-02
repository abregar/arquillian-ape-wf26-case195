package com.example.demo;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.common.Assert;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
public class DemoIT {

    @PersistenceUnit(name = "default", unitName = "default")
    private EntityManagerFactory emf;

    @Deployment
    @TargetsContainer("jboss")
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class).addClasses(Demo.class).addAsResource("META-INF/beans.xml")
            .addAsResource("META-INF/persistence.xml").addAsManifestResource("META-INF/TEST-MANIFEST.MF",
                "MANIFEST.MF");
    }

    @Test
    @UsingDataSet("arquillianTest.xml")
    public void should_populate_and_rollback_data() {
        final EntityManager em = emf.createEntityManager();
        final TypedQuery<Demo> query = em.createQuery("select d from Demo d", Demo.class);
        List<Demo> list = query.getResultList();
        Assert.assertTrue(list.size() == 1);

        Demo demo = new Demo();
        demo.setId(555L);
        demo.setName("user data");
        em.persist(demo);

        list = query.getResultList();
        Assert.assertTrue(list.size() == 2);
    }
}
