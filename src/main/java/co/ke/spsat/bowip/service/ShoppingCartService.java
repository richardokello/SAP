package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.Exception.ResourceNotFoundException;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductsRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StockRepository stockRepository;
    public ShoppingCart createCart(Long customerId) {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        ShoppingCart cart = new ShoppingCart();
        cart.setCustomer(customer);
        cart.setCreationDate(LocalDateTime.now());
        cart.setActive(true);
        cart.setTotalAmount(Double.valueOf(0));

        return shoppingCartRepository.save(cart);
    }

    public void addProductToCart(Long cartId, Long productId, Long quantity) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Stock stock=  stockRepository.findById(productId).orElseThrow
              (() -> new ResourceNotFoundException("Product not found in available stock"));
        if (stock.getQuantityOnHand()<=quantity) {
            throw new ResourceNotFoundException("Product not available in the requested quantity");
        }

//        if (product.getQuantityInStock() < quantity) {
//            throw new IllegalArgumentException("Product not available in the requested quantity");
//        }
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProducts().getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setItemPriceAmount(item.getItemPriceAmount()+(product.getSellingPrice()*(quantity)));
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProducts(product);
            newItem.setQuantity(quantity);
            newItem.setItemPriceAmount(product.getSellingPrice()*quantity);
            newItem.setCart(cart);
            cart.getCartItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        updateCartTotal(cart);
        shoppingCartRepository.save(cart);
    }

    public void removeProductFromCart(Long cartId, Long productId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cart.getCartItems().removeIf(item -> item.getProducts().getProductId().equals(productId));
        updateCartTotal(cart);
        shoppingCartRepository.save(cart);
    }

    public void updateProductQuantity(Long cartId, Long productId, Long quantity) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Stock stock=  stockRepository.findById(productId).orElseThrow
                (() -> new ResourceNotFoundException("Product not found in available stock"));
//        if (product.getQuantityInStock() < quantity) {
//            throw new IllegalArgumentException("Product not available in the requested quantity");
//        }
        if (stock.getQuantityOnHand()<=quantity) {
            throw new IllegalArgumentException("Product not available in the requested quantity");
        }
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProducts().getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(quantity);
            item.setItemPriceAmount(product.getSellingPrice()*(quantity));
            cartItemRepository.save(item);
        }

        updateCartTotal(cart);
        shoppingCartRepository.save(cart);
    }

    private void updateCartTotal(ShoppingCart cart) {
        Double total = cart.getCartItems().stream()
                .map(CartItem::getItemPriceAmount)
                .reduce((double) 0, Double::sum);

        cart.setTotalAmount(total);
    }
    public List<CartItem> getCartItems(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return cart.getCartItems();
    }
    public ShoppingCart getCart(Long cartId) {
        return shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }
    public void clearCart(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cartItemRepository.deleteAll(cart.getCartItems());
    }
    public ShoppingCart getCartByClientId(Long clientId) {
        Customers client = customerRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        return shoppingCartRepository.findByCustomerCustomerId(client.getCustomerId()).orElseGet(() -> {
            ShoppingCart cart = new ShoppingCart();
            cart.setCustomer(client);
            return shoppingCartRepository.save(cart);
        });
    }
}
