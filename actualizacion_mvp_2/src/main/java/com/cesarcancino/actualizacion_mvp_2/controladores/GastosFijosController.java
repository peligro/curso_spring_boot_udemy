package com.cesarcancino.actualizacion_mvp_2.controladores;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesarcancino.actualizacion_mvp_2.dto.GastosFijosRequestDto;
import com.cesarcancino.actualizacion_mvp_2.modelos.GastosFijosModel;
import com.cesarcancino.actualizacion_mvp_2.modelos.ProveedoresModel;
import com.cesarcancino.actualizacion_mvp_2.servicios.EstadosService;
import com.cesarcancino.actualizacion_mvp_2.servicios.GastosFijosService;
import com.cesarcancino.actualizacion_mvp_2.servicios.ProveedoresService;
@CrossOrigin(origins="*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class GastosFijosController {
	@Autowired
	private GastosFijosService gastosFijosService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private ProveedoresService proveedoresService;
	
	@GetMapping("/gastos-fijos")
	public ResponseEntity<?> metodo_get_por_mes_actual()
	{
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity.status(HttpStatus.OK).body(this.gastosFijosService.listarPorMes(fechaActual.getMonthValue(), fechaActual.getYear()));
	}
	@GetMapping("/gastos-fijos-por-mes/{mes}")
	public ResponseEntity<?> metodo_get_por_mes_especifico(@PathVariable("mes") Integer mes)
	{
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity.status(HttpStatus.OK).body(this.gastosFijosService.listarPorMes(mes, fechaActual.getYear()));
	}
	/*
	 {
    "nombre":"Cuenta dsde postman ñandú",
    "monto":2345,
    "proveedoresId": 3,
    "estadosId":0
	}
	 */
	@SuppressWarnings("serial")
	@PostMapping("/gastos-fijos")
	public ResponseEntity<?> metodo_post(@RequestBody GastosFijosRequestDto dto)
	{
		ProveedoresModel proveedor =this.proveedoresService.buscarPorId(dto.getProveedoresId());
		if(proveedor==null) 
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
				{
					put("mensaje","Ocurrió un error inesperado ");
				}
			});
		}else 
		{
			try {
				
				this.gastosFijosService.guardar(
						new GastosFijosModel
							(
								dto.getNombre(),
								dto.getMonto(),
								new Date(),
								this.estadosService.buscarPorId(4L),
								proveedor
							)
						);
				return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<String, String>() {
					{
						put("mensaje","Se creó el registro exitosamente");
					}
				});
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
					{
						put("mensaje","Ocurrió un error inesperado ");
					}
				});
			}
		}
	}
	/*
	 {
   "nombre":"Cuenta dsde postman ñandú",
   "monto":2345,
   "proveedoresId": 3,
   "estadosId":3-4
	}
	 */
	@SuppressWarnings("serial")
	@PutMapping("/gastos-fijos/{id}")
	public ResponseEntity<?> metodo_post(@PathVariable("id") Long id, @RequestBody GastosFijosRequestDto dto)
	{
		GastosFijosModel datos=this.gastosFijosService.buscarPorId(id);
		ProveedoresModel proveedor =this.proveedoresService.buscarPorId(dto.getProveedoresId());
		if(datos==null || proveedor==null) 
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
				{
					put("mensaje","Ocurrió un error inesperado ");
				}
			});	
		}else 
		{
			datos.setEstadosId(this.estadosService.buscarPorId(dto.getEstadosId()));
			datos.setMonto(dto.getMonto());
			datos.setNombre(dto.getNombre());
			datos.setProveedoresId(proveedor);
			this.gastosFijosService.guardar(datos);
			return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>() {
				{
					put("mensaje","Se modificó el registro exitosamente");
				}
			});
		}
	}
	@SuppressWarnings("serial")
	@DeleteMapping("/gastos-fijos/{id}")
	public ResponseEntity<?> metodo_post(@PathVariable("id") Long id)
	{
		GastosFijosModel datos=this.gastosFijosService.buscarPorId(id);
		if(datos!=null) 
		{
			this.gastosFijosService.eliminar(id);
			return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>() {
				{
					put("mensaje","Se eliminó el registro exitosamente");
				}
			});
		}else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
				{
					put("mensaje","Ocurrió un error inesperado ");
				}
			});
		}
	}
}





















