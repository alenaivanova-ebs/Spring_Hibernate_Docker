package com.runner.dao;

import com.runner.dao.model.Card;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class CardDAOImpl implements CardDAO {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Card find(String name) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Card> criteria = criteriaBuilder.createQuery(Card.class);
        Root<Card> root = criteria.from(Card.class);
        criteria.where(criteriaBuilder.equal(root.get("name"), name)).distinct(true);
        return em.createQuery(criteria).getSingleResult();

    }

    @Override
    public Optional<Card> get(Long id) {
        return Optional.ofNullable(em.find(Card.class, id));
    }

    @Override
    public Long create(Card entity) {
        em.persist(entity);
        String name = entity.getCardName();
        return find(name).getId();
    }
}
