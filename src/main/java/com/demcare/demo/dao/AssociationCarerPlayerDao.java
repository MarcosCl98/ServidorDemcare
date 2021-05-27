package com.demcare.demo.dao;



import com.demcare.demo.entities.AssociationCarerPlayer;
import com.demcare.demo.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AssociationCarerPlayerDao extends CrudRepository<AssociationCarerPlayer, Long>, JpaSpecificationExecutor<AssociationCarerPlayer> {

    List<AssociationCarerPlayer> findByCarerUser(User carer);

}
