package net.sample.demo;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping()
	public String welcome() {
		return "Welcome";
	}
	
	@GetMapping("/products")
	public List<Product> getAll(){
		return service.getAllProducts();
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> get(@PathVariable Integer id) {
		try {
			Product product = service.getById(id);
			return new ResponseEntity<Product>(product,HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> save(@RequestBody Product product) {
		service.insert(product);
		return new ResponseEntity<>(product,HttpStatus.OK);
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> update(@RequestBody Product product,@PathVariable Integer id){
		try {
			Product dbProduct = service.getById(id);
			//System.out.println("testing");
//			service.delete(id);
//			Product obj=new Product();
//			obj.setId(product.getId());
//			obj.setName(product.getName());
//			obj.setPrice(product.getPrice());
			service.insert(product);
			
			return new ResponseEntity<>(product,HttpStatus.OK);
			
		}
		catch(NoSuchElementException e) {
			//service.insert(product);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Product> delete(@PathVariable Integer id){
		try {
			Product product = service.getById(id);
			service.delete(id);
			return new ResponseEntity<Product>(product,HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}
}
