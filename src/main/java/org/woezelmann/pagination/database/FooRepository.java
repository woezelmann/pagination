package org.woezelmann.pagination.database;

import org.springframework.data.repository.CrudRepository;

public interface FooRepository extends CrudRepository<FooEntity, String> {
}
