package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.dtos.BatchRequest;
import co.ke.spsat.bowip.dtos.ProductRequest;
import co.ke.spsat.bowip.dtos.ProductResponse;
import co.ke.spsat.bowip.dtos.SupplierDTO;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.repositories.BatchRepository;
import co.ke.spsat.bowip.repositories.ProductCategoryRepository;
import co.ke.spsat.bowip.repositories.ProductsRepository;
import co.ke.spsat.bowip.repositories.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductsService {
    @Autowired
private ProductsRepository productsRepository;
    @Autowired
    private BatchRepository batchRepository;

  @Autowired
private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;

public ProductResponse createProducts(ProductRequest productDTO){

    Supplier supplier=new Supplier();
    Products products=new Products();
    ProductCategory category=new ProductCategory();
   ///  category=productCategoryRepository.findByCategoryId(productDTO.getCategoryId().getCategoryId());
    //category.setCategoryId(productDTO.getCategoryId().getCategoryId());
//    category.setDescription(productDTO.getCategoryId().getDescription());
    category.setCategoryName(productDTO.getCategoryId().getCategoryName());
    productCategoryRepository.save(category);
    //products.setCategoryId(productDTO.getCategoryId());
    products.setProductName(productDTO.getProductName());
    products.setProductCode(productDTO.getProductCode());
    products.setBrand(productDTO.getBrand());
    products.setColour(productDTO.getColour());
    products.setImage(productDTO.getImage());
    products.setLength(productDTO.getLength());
    products.setPrice(productDTO.getPrice());
    products.setSize(productDTO.getSize());
    products.setSKU(productDTO.getSKU());
    products.setWeight(productDTO.getWeight());
    products.setDescription(productDTO.getDescription());
    products.setUsageInstructions(productDTO.getUsageInstructions());
    if(!supplierRepository.findById(productDTO.getSupplier().getSupplierId()).isPresent()){
        supplier.setSupplierName(productDTO.getSupplier().getSupplierName());
        supplier.setStatus("INACTIVE");
        //  supplier.setContracts(productDTO.getSupplier().getContracts());
        supplier.setAddress(productDTO.getSupplier().getAddress());
        supplier.setContactEmail(productDTO.getSupplier().getContactEmail());
        supplier.setContactPhone(productDTO.getSupplier().getContactPhone());
        supplierRepository.save(supplier);
    products.setSupplier(supplier);
    }
    if(productDTO.getBatchRequests()!=null){
        List<Batch> batchList=new ArrayList<>();
        for (BatchRequest batchRequest: productDTO.getBatchRequests()){
            Batch batch=new Batch();
            batch.setProduct(products);
            batch.setBatchId(batchRequest.getBatchId());
            batch.setBatchName(batchRequest.getBatchName());
            batch.setExpirationDate(batchRequest.getExpiryDate());
            batch.setManufacturingDate(batchRequest.getManufacturingDate());
            batchList.add(batch);
        }
        batchRepository.saveAll(batchList);
        products.setBatches(batchList);
    }
    productsRepository.save(products);
ProductResponse response=new ProductResponse();
response.setResponseCode("00");
response.setMessage(productDTO.getProductName() + " created successfully");
    return response;
}


//    public List<Products> getListOfProducts(Integer pageNo, Integer pageSize, String sortBy) {
//        return productsRepository.findAllWithBatchesAndSupplier();
//    }

    public List<ProductRequest> getListOfProducts(Integer pageNo, Integer pageSize, String sortBy) {
        List<Products> productsList = productsRepository.findAllWithBatchesAndSupplier();
        List<ProductRequest> productDTOList = new ArrayList<>();

        for (Products products : productsList) {
            ProductRequest productDTO = new ProductRequest();
            productDTO.setProductId(products.getProductId());
            productDTO.setProductName(products.getProductName());
            productDTO.setImage(products.getImage());
            productDTO.setBrand(products.getBrand());
            productDTO.setLength(products.getLength());
            productDTO.setDescription(products.getDescription());
            productDTO.setProductCode(products.getProductCode());
            productDTO.setPrice(products.getPrice());
            productDTO.setQuantityInStock(products.getQuantityInStock());
            productDTO.setSKU(products.getSKU());
            productDTO.setDeleted(products.getDeleted());
            productDTO.setColour(products.getColour());
            // ... other fields

            // Map batches to BatchDTO
            List<BatchRequest> batchDTOList = new ArrayList<>();
            for (Batch batch : products.getBatches()) {
                BatchRequest batchDTO = new BatchRequest();
                batchDTO.setBatchId(batch.getBatchId());
                batchDTO.setBatchName(batch.getBatchName());
                batchDTO.setBatchNo(batch.getBatchNo());
                batchDTO.setExpiryDate(batch.getExpirationDate());
                batchDTO.setManufacturingDate(batch.getManufacturingDate());
                // ... other fields
                batchDTOList.add(batchDTO);
            }
            productDTO.setBatchRequests(batchDTOList);

            // Map supplier to SupplierDTO
            Supplier supplier = products.getSupplier();
            if (supplier != null) {
                SupplierDTO supplierDTO = new SupplierDTO();
                supplierDTO.setSupplierId(supplier.getSupplierId());
                supplierDTO.setSupplierName(supplier.getSupplierName());
                supplierDTO.setStatus(supplier.getStatus());
                supplierDTO.setContactEmail(supplierDTO.getContactEmail());
                supplierDTO.setContactPhone(supplier.getContactPhone());
                supplierDTO.setIsDeleted(supplier.getIsDeleted());
                supplierDTO.setAddress(supplier.getAddress());
                // ... other fields
                productDTO.setSupplier(supplierDTO);
            }

            productDTOList.add(productDTO);
        }

        return productDTOList;
    }
    public  List<Products> getProductByBatch(Long Id){

      return productsRepository.findByBatchId(Id);
    }
    public Products getProductById(Long productId){
        Optional<Products> products=productsRepository.findById(productId);
        if (!products.isPresent())
            throw new EntityNotFoundException("Product not found");
        return products.get();

    }

}
