package rest.db2;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "account2", path = "account2")
public interface SecondAccountRepository extends PagingAndSortingRepository<SecondAccount, Long> {

	List<SecondAccount> findByName(@Param("name") String name);

}
