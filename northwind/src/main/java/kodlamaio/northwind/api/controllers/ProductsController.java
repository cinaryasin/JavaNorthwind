package kodlamaio.northwind.api.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.northwind.business.abstracts.ProductService;
import kodlamaio.northwind.core.utilities.results.DataResult;
import kodlamaio.northwind.core.utilities.results.ErrorDataResult;
import kodlamaio.northwind.core.utilities.results.Result;
import kodlamaio.northwind.entities.concretes.Product;
import kodlamaio.northwind.entities.dtos.ProductWithCategoryDto;


@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductsController {

	private ProductService productService;

	
	@Autowired
	public ProductsController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping("/getall")
	public DataResult<List<Product>> getAll() {
		return this.productService.getAll();
	}

	@GetMapping("/getProductWithCategoryDetails")
	public DataResult<List<ProductWithCategoryDto>> getProductWithCategoryDetails() {
		return this.productService.getProductWithCategoryDetails();
	}

	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid Product product) {
		return this.productService.add(product);
	}

	@GetMapping("/getByProductName")
	public DataResult<Product> getByProductName(@RequestParam String productName) {

		return this.productService.getByProductName(productName);

	}

	@GetMapping("/getByProductNameAndCategoryId")
	public DataResult<Product> getByProductNameAndCategoryId(@RequestParam("productName") String productName,
			@RequestParam("categoryId") int categoryId) {
		return this.productService.getByProductNameAndCategoryId(productName, categoryId);
	}

	@GetMapping("/getByProductNameOrCategoryId")
	public DataResult<List<Product>> getByProductNameOrCategoryId(@RequestParam String productName,
			@RequestParam int categoryId) {
		return this.productService.getByProductNameOrCategoryId(productName, categoryId);
	}

	@GetMapping("/getByProductNameContains")
	public DataResult<List<Product>> getByProductNameContains(@RequestParam String productName) {
		return this.productService.getByProductNameContains(productName);
	}

	@GetMapping("/getAllByPage")
	public DataResult<List<Product>> getAll(int pageNo, int pageSize) {
		return this.productService.getAll(pageNo - 1, pageSize);
	}

	@GetMapping("/getAllASC")
	public DataResult<List<Product>> getAllSorted() {
		return this.productService.getAllSorted();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions) {
		Map<String, String> validationErrors = new HashMap<String, String>();
		for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, "Doğrulama Hataları");

		return errors;

	}
	
	
	
	

}
