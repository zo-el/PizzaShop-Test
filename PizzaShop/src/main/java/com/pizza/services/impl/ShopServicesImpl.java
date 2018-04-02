package com.pizza.services.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.pizza.entity.Order;
import com.pizza.repository.ShopRepository;
import com.pizza.repository.impl.ShopRepositoryImpl;
import com.pizza.services.ShopServices;

public class ShopServicesImpl implements ShopServices {

	private ShopRepository repository;

	public ShopServicesImpl() {
		this.repository = new ShopRepositoryImpl();
	}

	@Override
	public List<String> readFile(String read_path) throws IOException {
		List<String> orderList = repository.readTextFile(read_path);
		return orderList;
	}

	@Override
	public List<Order> createOrderList(List<String> original_order) {
		List<Order> order_list = new ArrayList<>();
		for (String order : original_order) {
			String[] order_Array = order.trim().split("\\s+");
			/*
			 * for (String name : order_Array) { System.out.println("->" + name); }
			 * System.out.println("-##-");
			 */
			Order o = new Order();
			if (!order_Array[0].equalsIgnoreCase("order")) {
				o.setName(order_Array[0]);
				o.setTime(order_Array[1]);
				order_list.add(o);
			}
		}
		return order_list;
	}

	@Override
	public List<Order> sortOrderList(List<Order> recreated_order) {

		Collections.sort(recreated_order, (o1, o2) -> {
			return new BigInteger(o1.getTime()).compareTo(new BigInteger(o2.getTime()));
		});

		return recreated_order;
	}

	@Override
	public void writeFile(String destination_path, List<Order> sorted_order) {
		repository.writeTextFile(destination_path, sorted_order);

	}

	@Override
	public List<Order> changeTime(List<Order> order) {
		// TODO Auto-generated method stub
		for(Order o : order) {
			String time = o.getTime();
			Date date = new Date(Long.parseLong(time));
	        DateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
	        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
	        String formatted = format.format(date);
	        o.setTime(formatted);
		}
		
		return order;
	}

}