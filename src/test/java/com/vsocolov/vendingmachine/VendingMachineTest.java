package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.coinsstorage.CoinsStorage;
import com.vsocolov.vendingmachine.productstorage.ProductStorage;
import com.vsocolov.vendingmachine.data.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static com.vsocolov.vendingmachine.enums.Coin.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class VendingMachineTest {

    private VendingMachine vendingMachine;

    private ProductStorage productStorage;

    private CoinsStorage coinsStorage;

    @Before
    public void setUp() {
        this.vendingMachine = new VendingMachine(5, Arrays.asList(ONE_PENNY, FIVE_PENCE, TEN_PENCE));
        this.productStorage = mock(ProductStorage.class);
        this.coinsStorage = mock(CoinsStorage.class);
        ReflectionTestUtils.setField(vendingMachine, "productStorage", productStorage);
        ReflectionTestUtils.setField(vendingMachine, "coinsStorage", coinsStorage);
    }

    @Test
    public void getQuantity_should_call_datastorage_and_return_quantity() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStorage.getProduct(productId)).thenReturn(product);

        assertThat(vendingMachine.getQuantity(productId), equalTo(product.getQuantity()));
    }

    @Test
    public void setQuantity_should_call_datastorage_and_create_new_product_with_new_quantity() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStorage.getProduct(productId)).thenReturn(product);
        final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        doReturn(product).when(productStorage).saveProduct(productCaptor.capture());

        vendingMachine.setQuantity(productId, 55);

        final Product forUpdate = productCaptor.getValue();
        verify(productStorage).saveProduct(forUpdate);
        assertThat(forUpdate.getName(), equalTo(product.getName()));
        assertThat(forUpdate.getId(), equalTo(product.getId()));
        assertThat(forUpdate.getPrice(), equalTo(product.getPrice()));
        assertThat(forUpdate.getQuantity(), equalTo(55));
    }

    @Test
    public void getPrice_should_call_datastorage_and_return_price() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStorage.getProduct(productId)).thenReturn(product);

        assertThat(vendingMachine.getPrice(productId), equalTo(product.getPrice()));
    }

    @Test
    public void setPrice_should_call_datastorage_and_create_new_product_with_new_price() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStorage.getProduct(productId)).thenReturn(product);
        final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        doReturn(product).when(productStorage).saveProduct(productCaptor.capture());

        vendingMachine.setPrice(productId, 111);

        final Product forUpdate = productCaptor.getValue();
        verify(productStorage).saveProduct(forUpdate);
        assertThat(forUpdate.getName(), equalTo(product.getName()));
        assertThat(forUpdate.getId(), equalTo(product.getId()));
        assertThat(forUpdate.getPrice(), equalTo(111));
        assertThat(forUpdate.getQuantity(), equalTo(product.getQuantity()));
    }

    @Test
    public void getName_should_call_datastorage_and_return_name() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStorage.getProduct(productId)).thenReturn(product);

        assertThat(vendingMachine.getName(productId), equalTo(product.getName()));
    }

    @Test
    public void setName_should_call_datastorage_and_create_new_product_with_new_name() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStorage.getProduct(productId)).thenReturn(product);
        final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        doReturn(product).when(productStorage).saveProduct(productCaptor.capture());

        vendingMachine.setName(productId, "UpdatedName");

        final Product forUpdate = productCaptor.getValue();
        verify(productStorage).saveProduct(forUpdate);
        assertThat(forUpdate.getName(), equalTo("UpdatedName"));
        assertThat(forUpdate.getId(), equalTo(product.getId()));
        assertThat(forUpdate.getPrice(), equalTo(product.getPrice()));
        assertThat(forUpdate.getQuantity(), equalTo(product.getQuantity()));
    }

    @Test
    public void getCoinAmount_should_call_coinsstorage_and_return_amount() {
        when(coinsStorage.getCoinAmount(FIVE_PENCE)).thenReturn(66);

        int coinAmount = vendingMachine.getCoinAmount(FIVE_PENCE);

        assertThat(coinAmount, equalTo(66));
    }

    @Test
    public void setCoinAmount_should_call_coinsstorage_and_set_new_amount() {
        vendingMachine.setCoinAmount(FIVE_PENCE, 66);

        verify(coinsStorage).setCoinAmount(FIVE_PENCE, 66);
    }
}