package Projeto.projeto.nova.versao;

import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<Pizza> pizzas;
    private double valorTotal;
    private double valorFrete;

    public Pedido(int id, Cliente cliente, List<Pizza> pizzas, double valorTotal, double valorFrete){
        this.id = id;
        this.cliente = cliente;
        this.pizzas = pizzas;
        this.valorTotal = valorTotal;
        this.valorFrete = valorFrete;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Cliente: " + cliente.getNome() + " | Pizzas: " + pizzas.size() + " | Total: R$ " + String.format("%.2f", valorTotal + valorFrete);
    }

}
