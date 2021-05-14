package com.demcare.demo.dao;



import com.demcare.demo.entities.AssociationCarerPlayer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AssociationCarerPlayerDao extends CrudRepository<AssociationCarerPlayer, Long>, JpaSpecificationExecutor<AssociationCarerPlayer> {



}
