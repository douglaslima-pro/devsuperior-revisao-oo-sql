package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import db.DB;
import repository.OrderRepository;
import repository.ProductRepository;

public class Program {

	private static final Connection CONN = DB.getConnection();

	public static void main(String[] args) throws SQLException {
		ProductRepository productRepository = new ProductRepository(CONN);
		OrderRepository orderRepository = new OrderRepository(CONN);

		System.out.println("""
				1. Listar todos os produtos
				2. Listar todos os pedidos
				3. Listar todos os pedidos com os produtos
				""");
		System.out.print("Digite o número da sua opção: ");

		Scanner scanner = new Scanner(System.in);
		int opcao = scanner.nextInt();

		System.out.println();

		switch (opcao) {
			case 1:
				productRepository.findAll().forEach(System.out::println);
				break;
			case 2:
				orderRepository.findAll().forEach(System.out::println);
				break;
			case 3:
				orderRepository.findAllJoinProduct().forEach(order -> {
					System.out.println(order);
					order.getProducts().forEach(product -> {
						System.out.println("\t" + product);
					});
				});
				break;
			default:
				System.out.println("Opção inválida!");
		}
		
		scanner.close();
	}

}
