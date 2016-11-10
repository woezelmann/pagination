package org.woezelmann.pagination.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BasicPaginationRepository extends JpaRepository<FooEntity, String> {

    @Query(value = "SELECT * FROM foo order by created DESC limit ?1 offset ?2", nativeQuery = true)
    List<FooEntity> queryPage(int pagesize, int offset);
}
