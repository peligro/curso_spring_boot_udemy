package cl.tamila.controladores;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import cl.tamila.modelos.ProductosModel;
import cl.tamila.service.ProductoService;
import cl.tamila.utilidades.Utilidades;
 

@Controller
@RequestMapping("/reportes")
public class ReportesController {
	
	@Value("${cesar.valores.base_url_upload}")
	private String base_url_upload;
	
	@Value("${cesar.valores.base_url}")
	private String base_url;
	
	@GetMapping("")
	public String home() 
	{
		return "reportes/home";
	}
	//para el excel
	private XSSFWorkbook workbook;
	
	//##########################PDF
	private final TemplateEngine templateEngine;
	
	public ReportesController(TemplateEngine templateEngine) 
	{
		this.templateEngine=templateEngine;
		this.workbook = new XSSFWorkbook();//esta línea es para el excel
	}
	@Autowired
	private ServletContext servletContext;
	
	@GetMapping("/pdf")
	public ResponseEntity<?> productos_pdf(HttpServletRequest request, HttpServletResponse response)
	{
		WebContext context = new WebContext(request, response, servletContext);
		context.setVariable("titulo", "PDF Dinámico desde Spring Boot");
		context.setVariable("ruta", this.base_url_upload);
		
		String html = this.templateEngine.process("reportes/pdf", context);
		
		ByteArrayOutputStream target = new ByteArrayOutputStream();
		
		ConverterProperties converterProperties =new ConverterProperties();
		converterProperties.setBaseUri(this.base_url);
		
		HtmlConverter.convertToPdf(html, target, converterProperties);
		
		byte[] bytes = target.toByteArray();
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytes);
	}
	//##########################EXCEL
	private XSSFSheet sheet;
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/excel")
	public void excel(HttpServletResponse response) throws IOException 
	{
		response.setContentType("application/octet-stream");
		long time = System.currentTimeMillis();
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=reporte_" + time + ".xlsx";
		
		response.setHeader(headerKey, headerValue);
		response.setHeader("time", time+"");
		
		this.sheet = this.workbook.createSheet("Hola 1"+time);
		
		CellStyle style = this.workbook.createCellStyle();
		
		XSSFFont font = this.workbook.createFont();
		// font.setBold(true);
		// font.setFontHeight(16);
		style.setFont(font);
		
		//generamos las filas del encabezado
		Row row = this.sheet.createRow(0);
		createCell(row, 0, "ID", style);
		createCell(row, 1, "Nombre", style);
		createCell(row, 2, "Descripción", style);
		createCell(row, 3, "Precio", style);
		createCell(row, 4, "Foto", style);
		createCell(row, 5, "Time", style);
		
		//generamos las filas dinámicas del reporte
		List<ProductosModel> datos = this.productoService.listar2();
		int rowCount = 1;
		
		for(ProductosModel dato: datos) 
		{
			row =this.sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, dato.getId(), style);
			createCell(row, columnCount++, dato.getNombre(), style);
			createCell(row, columnCount++, dato.getDescripcion(), style);
			createCell(row, columnCount++, Utilidades.numberFormat(dato.getPrecio()), style);
			createCell(row, columnCount++,this.base_url_upload+"producto2/"+dato.getFoto(), style);
			createCell(row, columnCount++, time+"", style);
		}
		
		//se formatea la salida
		ServletOutputStream outputStream = response.getOutputStream();
		this.workbook.write(outputStream);
		this.workbook.close();
		outputStream.close();
		
	}
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);

	}
	//##########################CSV
	@GetMapping("/csv")
	public void csv(HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/csv");
		long time = System.currentTimeMillis();
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=reporte_" + time + ".csv";
		response.setContentType("text/csv;charset=utf-8");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"ID", "Nombre", "Descripción", "precio", "foto"};
		String[] nameMapping = {"id", "nombre", "descripcion", "precio", "foto"};
		
		csvWriter.writeHeader(csvHeader);
		
		//formateamos las filas dinámicas
		List<ProductosModel> datos = this.productoService.listar2();
		for (ProductosModel dato : datos) 
		{
			csvWriter.write(dato, nameMapping);
		}
		//cerramos la comunicación
		csvWriter.close();
	}
	
	
	
	
	
	
	
	
	
	
}
