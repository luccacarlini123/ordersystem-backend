package com.mouzetech.ordersystem;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mouzetech.ordersystem.domain.Categoria;
import com.mouzetech.ordersystem.domain.Cidade;
import com.mouzetech.ordersystem.domain.Cliente;
import com.mouzetech.ordersystem.domain.Endereco;
import com.mouzetech.ordersystem.domain.Estado;
import com.mouzetech.ordersystem.domain.Pagamento;
import com.mouzetech.ordersystem.domain.PagamentoComBoleto;
import com.mouzetech.ordersystem.domain.PagamentoComCartao;
import com.mouzetech.ordersystem.domain.Pedido;
import com.mouzetech.ordersystem.domain.Produto;
import com.mouzetech.ordersystem.domain.enums.EstadoPagamento;
import com.mouzetech.ordersystem.domain.enums.TipoCliente;
import com.mouzetech.ordersystem.repositories.CategoriaRepository;
import com.mouzetech.ordersystem.repositories.CidadeRepository;
import com.mouzetech.ordersystem.repositories.ClienteRepository;
import com.mouzetech.ordersystem.repositories.EnderecoRepository;
import com.mouzetech.ordersystem.repositories.EstadoRepository;
import com.mouzetech.ordersystem.repositories.PagamentoRepository;
import com.mouzetech.ordersystem.repositories.PedidoRepository;
import com.mouzetech.ordersystem.repositories.ProdutoRepository;

@SpringBootApplication
public class OrdersystemApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Autowired
	private PagamentoRepository pagamentoRepo;
	

	public static void main(String[] args) {
		SpringApplication.run(OrdersystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.setProdutos(Arrays.asList(p1, p2, p3));
		cat2.setProdutos(Arrays.asList(p2));
		
		p1.setCategorias(Arrays.asList(cat1));
		p2.setCategorias(Arrays.asList(cat1, cat2));
		p3.setCategorias(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.setCidade(Arrays.asList(c1));
		est2.setCidade(Arrays.asList(c2, c3));
				
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2));
		produtoRepo.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepo.saveAll(Arrays.asList(est1, est2));
		cidadeRepo.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		Endereco e1 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Jardim", "23300333", c1, cli1);
		Endereco e2 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Centro", "14320637", c2, cli1);
		cli1.getTelefones().addAll(Arrays.asList("21966425063", "21990836512"));
		cli1.setEnderecos(Arrays.asList(e1, e2));
		
		clienteRepo.saveAll(Arrays.asList(cli1));
		enderecoRepo.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), e1, cli1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), e2, cli1);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		
		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepo.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepo.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		
	}

}
