package kr.or.connect.chatbotserver.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Transactional
@Repository
public class PhoneNumberOfUniversityDAO {


    @PersistenceContext
    private EntityManager entityManager;
}
