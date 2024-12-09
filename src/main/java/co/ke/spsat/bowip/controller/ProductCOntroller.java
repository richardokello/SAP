package co.ke.spsat.bowip.controller;


import co.ke.spsat.bowip.dtos.ProductRequest;
import co.ke.spsat.bowip.dtos.ProductResponse;
import co.ke.spsat.bowip.entities.Discount;
import co.ke.spsat.bowip.entities.Products;
import co.ke.spsat.bowip.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    ProductsService productsService;

    @PostMapping("/createProduct")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Validated ProductRequest productRequest){
        ProductResponse response=new ProductResponse();
        try{

         response=  productsService.createProducts(productRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e){
            response.setResponseCode("501");
            response.setMessage(e.getMessage());
            return new ResponseEntity<ProductResponse>(response,HttpStatus.CONFLICT);
        }
    } @PostMapping("/setProductDiscount")
    public ResponseEntity<ProductResponse> setProductDiscount(@RequestBody @Validated Discount productRequest, Long productId){
        ProductResponse response;
        response=  productsService.setDiscount(productRequest, productId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<List<Products>> searchProductsByName(@RequestParam String name) {
        List<Products> products = productsService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter/category")
    public ResponseEntity<List<Products>> filterProductsByCategory(@RequestParam Long categoryId) {
        List<Products> products = productsService.filterProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter/price")
    public ResponseEntity<List<Products>> filterProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        List<Products> products = productsService.filterProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/productList")
    public ResponseEntity<List<ProductRequest>>getListOfProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy){
        try{
            List<ProductRequest> productList;
            productList=productsService.getListOfProducts(pageNo,pageSize,sortBy);
            if(!productList.isEmpty()){
                return new ResponseEntity<>(productList,new HttpHeaders(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new HttpHeaders(),HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

@GetMapping(path = "/productsByBatch/{batchId}")
    public ResponseEntity<List<Products>>getListOfProductsByBatch(@PathVariable Long batchId){
        try{
            List<Products>productList;
            productList=productsService.getProductByBatch(batchId);
            if(!productList.isEmpty()){
                return new ResponseEntity<>(productList,new HttpHeaders(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new HttpHeaders(),HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/getProductById/{id}",method = RequestMethod.GET)
    public ResponseEntity<Products>getProductById(@PathVariable Long id) {
        Products products = productsService.getProductById(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
