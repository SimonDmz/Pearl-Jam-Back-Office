package fr.insee.pearljam.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.insee.pearljam.api.domain.Referent;

@Repository
public interface ReferentRepository extends JpaRepository<Referent, Long> {

}
