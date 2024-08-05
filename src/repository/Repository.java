package repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {

	List<T> findAll() throws SQLException;
	
}
