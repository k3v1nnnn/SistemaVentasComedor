package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.BaseDatosCsv;
import modelo.Compra;
import modelo.Producto;


public class VentanaCompraController implements Initializable {

    @FXML
    private Label subTotal;
    @FXML
    private Label descuento;
    @FXML
    private Label total;
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto,Integer> cantidadColumna;
    @FXML
    private TableColumn<Producto,String> productoColumna;
    @FXML
    private TableColumn<Producto,Integer> precioColumna;
    @FXML
    private Button agregarProducto;
    @FXML
    private Button sacarProducto;
    @FXML
    private Button aceptarPedido;
    @FXML
    private Button cancelarPedido;
    @FXML
    private Hyperlink link;
    private BaseDatosCsv baseDatos;
    private Compra compra;
    private int montoDescuento;
    private Aplicacion ap;
    @FXML
    private Button configProducto;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.productoColumna.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre"));
        this.precioColumna.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("precio"));
        this.cantidadColumna.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("cantidad"));
        this.montoDescuento=0;
        this.compra=new Compra();
        this.actualizarVentana();
        
    }
    public void VentasCompraControler(BaseDatosCsv baseDeDatos,Aplicacion app){
        this.baseDatos=baseDeDatos;
        this.ap=app;
    }

    @FXML
    private void agregarProductoBoton(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Agregar Producto");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Aplicacion.class.getResource("/vista/VentanaAgregarProducto.fxml"));
        Parent parent = loader.load();
        VentanaAgregarProductoController ventanaAgregarProducto = loader.getController();
        ventanaAgregarProducto.VentanaAgregarProductoControler(stage,this.baseDatos,this.compra,this);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void sacarProductoBoton(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Sacar Producto");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Aplicacion.class.getResource("/vista/VentanaQuitarProducto.fxml"));
        Parent parent = loader.load();
        VentanaQuitarProductoController ventanaQuitarProducto = loader.getController();
        ventanaQuitarProducto.VentanaQuitarProductoControler(stage,this.compra,this);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void aceptarPedidoBoton(ActionEvent event) {
        Calendar hoy = Calendar.getInstance();
        int dia = hoy.get(Calendar.DATE);
        int mes = hoy.get(Calendar.MONTH)+1;
        int ano = hoy.get(Calendar.YEAR);
        int precioTotal=this.compra.subTotalCompra()-this.montoDescuento;
        //this.baseDatos.agregarVenta(dia, mes, ano, precioTotal);
        this.compra=new Compra();
        this.actualizarVentana();
    }

    @FXML
    private void cancelarPedidoBoton(ActionEvent event) {
        this.compra=new Compra();
        this.actualizarVentana();
    }
    public void actualizarVentana(){
        int subPrecio=this.compra.subTotalCompra();
        this.subTotal.setText(subPrecio+"");
        this.descuento.setText(this.montoDescuento+"");
        this.total.setText(subPrecio-this.montoDescuento+"");
        this.tablaProductos.getItems().clear();
        for (Producto prod:this.compra.productos()){
            this.tablaProductos.getItems().add(prod);
        }
        if(subPrecio==0 && this.montoDescuento==0){
            this.aceptarPedido.setDisable(true);
        }else{
            this.aceptarPedido.setDisable(false);
        }
        if(this.compra.productos().isEmpty()){
            this.sacarProducto.setDisable(true);
        }else{
            this.sacarProducto.setDisable(false);
        }
    }

    @FXML
    private void abrirPagina(ActionEvent event) {
        this.ap.getHostServices().showDocument("https://github.com/k3v1nnnn/ventasComedor");
    }

    @FXML
    private void configProductoBoton(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Configuracion Producto");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Aplicacion.class.getResource("/vista/VentanaConfiguracionProducto.fxml"));
        Parent parent = loader.load();
        VentanaConfiguracionProductoController ventanaConfiguracionProducto = loader.getController();
        ventanaConfiguracionProducto.VentanaConfiguracionProductoController(stage,this.baseDatos);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
}
