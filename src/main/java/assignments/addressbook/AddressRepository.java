package assignments.addressbook;
import org.springframework.data.repository.CrudRepository;
public interface AddressRepository extends CrudRepository<Person,Long>{

}
