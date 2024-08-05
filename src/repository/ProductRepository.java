package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Product;

public class ProductRepository implements Repository<Product> {
	
	private Connection conn;
	
	public ProductRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Product> findAll() throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM tb_product");
		List<Product> products = new ArrayList<>();
		while (rs.next()) {
			products.add(instantiateProduct(rs));
		}
		return products;
	}
	
	public static Product instantiateProduct(ResultSet rs) throws SQLException {
		Product p = new Product();
		p.setId(rs.getLong("id"));
		p.setName(rs.getString("name"));
		p.setDescription(rs.getString("description"));
		p.setImageUri(rs.getString("image_uri"));
		p.setPrice(rs.getDouble("price"));
		return p;
	}
	
}
