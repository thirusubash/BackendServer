package  com.gksvp.web.company.repository;
import com.gksvp.web.company.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    // Define custom query methods here

    // Custom search method to find plants by name
    Page<Plant> findByNameContaining(String name, Pageable pageable);

    // Example of a more complex query using @Query annotation
    @Query("SELECT p FROM Plant p WHERE p.name LIKE %:searchTerm%")
    Page<Plant> searchPlants(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Define custom query methods here if needed
    Page<Plant> findAllByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(String name, String location, Pageable pageable);

    Page<Plant> findByCompanyIdAndNameContainingIgnoreCase(Long companyId, String keyword, Pageable pageable);

    Page<Plant> findByCompanyId(Long companyId, Pageable pageable);
    List<Plant> findByCompanyId(Long companyId);
    List<Plant> findByCompanyIdAndNameContainingIgnoreCase(Long companyId, String keyword);

}
