package kr.co.paywith.pw.data.repository.mbs.globals;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface GlobalsRepository extends CrudRepository<Globals, GlobalsKey> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Globals> findByGlobalsKey(GlobalsKey globalsKey);
}
