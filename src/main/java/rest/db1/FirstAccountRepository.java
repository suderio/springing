package rest.db1;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "account1", path = "account1")
public interface FirstAccountRepository extends PagingAndSortingRepository<FirstAccount, Long> {
	List<FirstAccount> findByName(@Param("name") String name);

}
