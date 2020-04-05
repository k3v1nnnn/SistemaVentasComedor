/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ProductoElegidoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import modelo.Producto;

/**
 *
 * @author RetailAdmin
 */
public class ProductoElegido extends HBox {
    private Label infoProducto;
    private Button sacarProducto;
    private Producto productoElegido;
    
    public ProductoElegido(Producto infoProducto){
        this.productoElegido=infoProducto;
        this.setSpacing(100);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5,5,5,5));
        this.infoProducto=new Label();
        this.actualizarProductoElegido();
        this.sacarProducto=new Button("Quitar");
        this.sacarProducto.setOnAction(new ProductoElegidoController(this,infoProducto));
        this.getChildren().addAll(this.infoProducto,this.sacarProducto);
    }
    
    public void actualizarProductoElegido(){
         this.infoProducto.setText(this.productoElegido.getCantidad()+" - "+this.productoElegido.getNombre()+" - "+this.productoElegido.getPrecio());
    }
}