/**
 * @author Javier Burón Gutiérrez.
 *
 */

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Controller implements ActionListener{
	private GUI gui= null;
	private String[][] matrizAdyacencia;
	
	public Controller(){
		gui= new GUI();
		gui.btnExaminar.addActionListener(this);
		gui.btnCalcular.addActionListener(this);
	}

	/**
	 * Obtener el contenido del archivo de texto.
	 * @param Ruta Ruta del archivo en el disco duro
	 * @return Contenido del archivo
	 * @throws IOException En caso de tener problemas con el archivo, lanza excepción
	 */
	private String obtenerContenidoArchivo(String ruta) throws IOException{
		String content= "";
		FileReader in= new FileReader(ruta);
		BufferedReader bfR= new BufferedReader(in);
		
		String line= null;
		while((line= bfR.readLine()) != null){
			content+= line + "\n";
		}
		
		bfR.close();
		return content;
	}
	
	/**
	 * Obtener vector con par de coordenadas y establece el nombre del archivo analizado a la GUI.
	 * @param contenidoFile Contenido del archivo de texto
	 * @return Vector con par de coordenadas.
	 */
	private Vector<String> obtenerParCoordenadas(String contenidoFile) {
		Vector<String> parCoordenadas= new Vector<String>();
		String name;
		
		String[] splitContentFile= contenidoFile.split("\n");
		name= splitContentFile[0].replaceAll("NAME", "");
		name= name.replace(':', ' ');
		name= name.trim();
		gui.lblTipoArchivo.setText(name.toUpperCase());
		
		String[] splitParCoord= null; 
		for(int i=6; i<splitContentFile.length-1; i++){
			splitParCoord= splitContentFile[i].split(" ");
			parCoordenadas.add(splitParCoord[1] + " " + splitParCoord[2]);
		}
		
		return parCoordenadas;
	}
	
	/**
	 * Obtner en Double las coordenadas de las coordenadas en String 
	 * @param Coordenadas Coordenadas en String
	 * @return Coordenadas en double
	 */
	private Double[] calcularCoordenadasNodo(String coordenadas){
		String[] splitCoordA= coordenadas.split(" ");
		Double[] par={
			Double.parseDouble(splitCoordA[0]),
			Double.parseDouble(splitCoordA[1])
		};
		
		return par;
	}
	
	/**
	 * Calcular peso entre nodo A y B
	 * @param coordA Par de coordenadas para el nodo A
	 * @param coordB Par de coordenadas para el nodo B
	 * @return Peso entre el nodo A y B
	 */
	private String calcularPeso(String coordA, String coordB) {
		double peso= 0;
		Double[] coordenadasA= calcularCoordenadasNodo(coordA);
		Double[] coordenadasB= calcularCoordenadasNodo(coordB);
		
		peso= Math.pow((coordenadasB[0] - coordenadasA[0]), 2) + Math.pow((coordenadasB[1] - coordenadasA[1]), 2);
		peso= Math.sqrt(peso);
		
		return String.valueOf(peso);
	}
	
	/**
	 * Inicializar matriz de adyacencia, cada elemento en null
	 * @param size Tamaño para la matriz de adyacencia
	 */
	private void inicializarMatrizAdyacencia(int size){
		matrizAdyacencia= new String[size][size];
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				matrizAdyacencia[i][j]= null;
			}
		}
	}
	
	/**
	 * Calcula la matriza de adyacencia utilizando los datos obtenidos del archivo
	 * @param parCoordenadas Par de coordenadas, obtenidas del archivo.
	 */
	private void calcularMatrizAdyacencia(Vector<String> parCoordenadas) {
		inicializarMatrizAdyacencia(parCoordenadas.size());
		for(int i=0; i<parCoordenadas.size() - 1; i++){
			for(int j=i+1; j<parCoordenadas.size(); j++){
				matrizAdyacencia[j][i] = 
						matrizAdyacencia[i][j]= calcularPeso(parCoordenadas.get(i), parCoordenadas.get(j));
			}
		}
	}
	
	/**
	 * Obtener contenido de archivo, obtener par de coordenadas y calcular matriza de adyacecia. Llamada a métodos.
	 * @throws IOException Selanza esta excepción cuando se llama al método <b>obtenerContenidoArchivo()</b>
	 */
	private void cargarContenidoEnMemoria() throws IOException{
		String ruta= gui.obtenerRutaArchivo();
		if(ruta == null){
			throw new IOException("Archivo no encontrado");
		}
		String contenidoFile= "";
		Vector<String> parCoordenadas= null;
		
		gui.textFieldPathFile.setText(ruta);
		contenidoFile= obtenerContenidoArchivo(ruta);
		if(contenidoFile != ""){
			parCoordenadas= obtenerParCoordenadas(contenidoFile);
			if(parCoordenadas.size() > 0){
				calcularMatrizAdyacencia(parCoordenadas);
			}
		}
	}
	
	/**
	 * Calcula el recorrido.
	 * @param nodoInicial Nodo para iniciar el recorrido.
	 * @return Recorrido.
	 */
	private Vector<Integer> calcularSolucion(int nodoInicial){
		int aleatorio= 1;
		Vector<Integer> nodosDisponibles = new Vector<Integer>();
		Vector<Integer> nodosHistorial= new Vector<Integer>();
		
		for(int i=0; i<matrizAdyacencia.length; i++){
			nodosDisponibles.add(i+1);
		}
		
		nodosHistorial.add(nodoInicial);
		nodosDisponibles.remove(nodoInicial-1);
		
		while(!nodosDisponibles.isEmpty()) {
			aleatorio= ThreadLocalRandom.current().nextInt(0, nodosDisponibles.size());
			nodosHistorial.add(nodosDisponibles.get(aleatorio));
			nodosDisponibles.remove(aleatorio);
		}
		
		nodosHistorial.add(nodoInicial);
		
		return nodosHistorial;
	}
	
	/**
	 * Calcular el costo total del recorrido, no determinista.
	 * @param nodoHistorial Vector de recorrido no determinista
	 * @return El costo total en base a el vector de recorridos
	 */
	private String calcularCostoTotal(Vector<Integer> nodoHistorial){
		double costoTotal= 0;
		for(int i=0; i<nodoHistorial.size()-1; i++){
			costoTotal+= Double.parseDouble(matrizAdyacencia[nodoHistorial.get(i)-1][nodoHistorial.get(i+1)-1]);
		}
		return String.valueOf(costoTotal);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(gui.btnExaminar)){
			try {
				gui.inicializar();
				cargarContenidoEnMemoria();
				gui.btnCalcular.setEnabled(true);
			} catch (IOException exeption) {
				matrizAdyacencia= null;
				gui.mostrarMensajeDialog(exeption.getMessage());
			}
		}else if(e.getSource().equals(gui.btnCalcular)){
			Vector<Integer> nodosRecorrido= calcularSolucion(Integer.parseInt(gui.textFieldNodoInicial.getText()));
			gui.textAreaRecorrido.setText(nodosRecorrido.toString());
			gui.textFieldCosto.setText(calcularCostoTotal(nodosRecorrido));
		}
	}
}
