package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.Order;
import entity.OrderStatus;
import entity.Product;

public class OrderRepository implements Repository<Order> {

	private Connection conn;

	public OrderRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Order> findAll() throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM tb_order");
		List<Order> orders = new ArrayList<>();
		while (rs.next()) {
			orders.add(instantiateOrder(rs));
		}
		return orders;
	}

	public List<Order> findAllJoinProduct() throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("""
				SELECT * FROM tb_order
				INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id
				INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id
				""");
		Map<Long, Order> orders = new LinkedHashMap<>();
		while (rs.next()) {
			if (!orders.containsKey(rs.getLong("order_id"))) {
				Order o = instantiateOrder(rs);
				o.setId(rs.getLong("order_id"));
				Product p = ProductRepository.instantiateProduct(rs);
				p.setId(rs.getLong("product_id"));
				o.getProducts().add(p);
				orders.put(o.getId(), o);
			} else {
				Product p = ProductRepository.instantiateProduct(rs);
				p.setId(rs.getLong("product_id"));
				orders.get(rs.getLong("order_id")).getProducts().add(p);
			}
		}
		return orders.values()
				.stream()
				.toList();
	}

	public static Order instantiateOrder(ResultSet rs) throws SQLException {
		Order o = new Order();
		o.setId(rs.getLong("id"));
		o.setLatitude(rs.getDouble("latitude"));
		o.setLongitude(rs.getDouble("longitude"));
		o.setMoment(rs.getTimestamp("moment").toInstant());
		o.setStatus(OrderStatus.values()[rs.getInt("status")]);
		return o;
	}

}
