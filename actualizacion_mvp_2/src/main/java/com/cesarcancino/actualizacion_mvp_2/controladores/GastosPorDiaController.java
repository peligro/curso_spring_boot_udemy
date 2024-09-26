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

import com.cesarcancino.actualizacion_mvp_2.dto.GastosPorDiaRequestDto;
import com.cesarcancino.actualizacion_mvp_2.modelos.GastosPorDiaModel;
import com.cesarcancino.actualizacion_mvp_2.modelos.ProveedoresModel;
import com.cesarcancino.actualizacion_mvp_2.servicios.GastosPorDiaService;
import com.cesarcancino.actualizacion_mvp_2.servicios.ProveedoresService;
@CrossOrigin(origins="*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class GastosPorDiaController {
	@Autowired
	private GastosPorDiaService gastosPorDiaService;
	
	@Autowired
	private ProveedoresService proveedoresService;
	
	@GetMapping("/gastos-por-dia")
	public ResponseEntity<?> metodo_get_por_mes_actual()
	{
		LocalDate fechaActual = LocalDate.now(); 
		return ResponseEntity.status(HttpStatus.OK).body(this.gastosPorDiaService.listarPorMes(fechaActual.getMonthValue(), fechaActual.getYear()));
	}
	@GetMapping("/gastos-por-dia-por-mes/{mes}")
	public ResponseEntity<?> metodo_get_por_mes_dinamico(@PathVariable("mes") Integer mes)
	{
		LocalDate fechaActual = LocalDate.now(); 
		return ResponseEntity.status(HttpStatus.OK).body(this.gastosPorDiaService.listarPorMes(mes, fechaActual.getYear()));
	}
	
	/*{
    
    "neto": 100,
    "iva": 19,
    "total": 119, 
    "descripcion": "Notebook gammer",
    "proveedoresId": 1
}*/
	@SuppressWarnings("serial")
	@PostMapping("/gastos-por-dia")
	public ResponseEntity<?> metodo_post(@RequestBody GastosPorDiaRequestDto dto)
	{
		ProveedoresModel proveedor=this.proveedoresService.buscarPorId(dto.getProveedoresId());
		if(proveedor!=null) 
		{
			this.gastosPorDiaService.guardar(
					new GastosPorDiaModel( 
							dto.getNeto(),
							dto.getIva(),
							dto.getTotal(),
							new Date(),
							dto.getDescripcion(),
							proveedor
							)
					);
			return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<String, String>() {
				{
					put("mensaje","Se creó el registro exitosamente");
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
/*{
    
    "neto": 100,
    "iva": 19,
    "total": 119, 
    "descripcion": "Notebook gammer",
    "proveedoresId": 1
}*/
	@SuppressWarnings("serial")
	@PutMapping("/gastos-por-dia/{id}")
	public ResponseEntity<?> metodo_put(@PathVariable("id") Long id, @RequestBody GastosPorDiaRequestDto dto)
	{
		GastosPorDiaModel datos=this.gastosPorDiaService.buscarPorId(id);
		ProveedoresModel proveedor=this.proveedoresService.buscarPorId(dto.getProveedoresId());
		if(datos==null || proveedor==null) 
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {
				{
					put("mensaje","Ocurrió un error inesperado ");
				}
			});
		}else 
		{
			datos.setIva(dto.getIva());
			datos.setDescripcion(dto.getDescripcion());
			datos.setNeto(dto.getNeto());
			datos.setTotal(dto.getTotal());
			datos.setProveedoresId(proveedor);
			this.gastosPorDiaService.guardar(datos);
			return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>() {
				{
					put("mensaje","Se modificó el registro exitosamente");
				}
			});
		}
	}
	@SuppressWarnings("serial")
	@DeleteMapping("/gastos-por-dia/{id}")
	public ResponseEntity<?> metodo_delete(@PathVariable("id") Long id)
	{
		GastosPorDiaModel datos=this.gastosPorDiaService.buscarPorId(id);
		if(datos!=null) 
		{
			this.gastosPorDiaService.eliminar(id);
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





















