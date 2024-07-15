package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.entities.CartItem;
import co.ke.spsat.bowip.entities.ShoppingCart;
import co.ke.spsat.bowip.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Create a new shopping cart
    @PostMapping("/create/{customerId}")
    public ResponseEntity<ShoppingCart> createCart(@PathVariable Long customerId) {
        ShoppingCart cart = shoppingCartService.createCart(customerId);
        return ResponseEntity.ok(cart);
    }

    // Add a product to the shopping cart
    @PostMapping("/{cartId}/add")
    public ResponseEntity<ShoppingCart> addProductToCart(
            @PathVariable Long cartId,
            @RequestParam Long productId,
            @RequestParam Long quantity) {
        shoppingCartService.addProductToCart(cartId, productId, quantity);
        ShoppingCart updatedCart = shoppingCartService.getCart(cartId);
        return ResponseEntity.ok(updatedCart);
    }
    @DeleteMapping("/{cartId}/clear")
    public void clearCart(@PathVariable Long cartId) {
        shoppingCartService.clearCart(cartId);
    }

    // Remove a product from the shopping cart
    @DeleteMapping("/{cartId}/remove")
    public ResponseEntity<ShoppingCart> removeProductFromCart(
            @PathVariable Long cartId,
            @RequestParam Long productId) {
        shoppingCartService.removeProductFromCart(cartId, productId);
        ShoppingCart updatedCart = shoppingCartService.getCart(cartId);
        return ResponseEntity.ok(updatedCart);
    }

    // Update the quantity of a product in the shopping cart
    @PutMapping("/{cartId}/update")
    public ResponseEntity<ShoppingCart> updateProductQuantity(
            @PathVariable Long cartId,
            @RequestParam Long productId,
            @RequestParam Long quantity) {
        shoppingCartService.updateProductQuantity(cartId, productId, quantity);
        ShoppingCart updatedCart = shoppingCartService.getCart(cartId);
        return ResponseEntity.ok(updatedCart);
    }    @PutMapping("/{cartId}/customerId")
    public ResponseEntity<ShoppingCart>getCartByCustomer(Long customerId){
       ShoppingCart cart= shoppingCartService.getCartByClientId(customerId);
       return ResponseEntity.ok(cart);
    }

    // Get the shopping cart
    @GetMapping("/{cartId}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable Long cartId) {
        ShoppingCart cart = shoppingCartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }

    // Get all cart items
    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        ShoppingCart cart = shoppingCartService.getCart(cartId);
        return ResponseEntity.ok(cart.getCartItems());
    }
}
