package org.launchcode.models.data;


import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import org.launchcode.models.Category;
import org.springframework.data.repository.CrudRepository;

@Repository
@Transactional
public interface CategoryDao extends CrudRepository<Category,Integer> {



}
