package com.impact.backend.repository;

import com.impact.backend.entity.CallData;
import com.impact.backend.entity.PhoneDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
//@RepositoryRestResource(collectionResourceRel = "phone_details", path = "phone_details")
public interface PhoneDetailsRepository extends JpaRepository<PhoneDetails, Long> {

}
