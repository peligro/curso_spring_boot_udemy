package cl.tamila.componentes;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cl.tamila.modelos.PaisModel;

@Component
public class ConvertirPaisId implements Converter<String, PaisModel> {

	@Override
	public PaisModel convert(String source) {
		
		Integer id = Integer.valueOf(source);
		PaisModel datos = new PaisModel();
		datos.setId(id);
		return datos;
	}
	

}
