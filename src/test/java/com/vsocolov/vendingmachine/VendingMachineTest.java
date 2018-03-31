package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.productstore.ProductStore;
import com.vsocolov.vendingmachine.productstore.data.Product;
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

    private ProductStore productStore;

    @Before
    public void setUp() {
        this.vendingMachine = new VendingMachine(5, Arrays.asList(ONE_PENNY, FIVE_PENCE, TEN_PENCE));
        this.productStore = mock(ProductStore.class);
        ReflectionTestUtils.setField(vendingMachine, "productStore", productStore);
    }

    @Test
    public void getQuantity_should_call_datasource_and_return_quantity() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStore.getProduct(productId)).thenReturn(product);

        assertThat(vendingMachine.getQuantity(productId), equalTo(product.getQuantity()));
    }

    @Test
    public void setQuantity_should_call_datastore_and_create_new_product_with_new_quantity() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStore.getProduct(productId)).thenReturn(product);
        final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        doReturn(product).when(productStore).saveProduct(productCaptor.capture());

        vendingMachine.setQuantity(productId, 55);

        final Product forUpdate = productCaptor.getValue();
        verify(productStore).saveProduct(forUpdate);
        assertThat(forUpdate.getName(), equalTo(product.getName()));
        assertThat(forUpdate.getId(), equalTo(product.getId()));
        assertThat(forUpdate.getPrice(), equalTo(product.getPrice()));
        assertThat(forUpdate.getQuantity(), equalTo(55));
    }

    @Test
    public void getPrice_should_call_datasource_and_return_price() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStore.getProduct(productId)).thenReturn(product);

        assertThat(vendingMachine.getPrice(productId), equalTo(product.getPrice()));
    }

    @Test
    public void setPrice_should_call_datastore_and_create_new_product_with_new_price() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStore.getProduct(productId)).thenReturn(product);
        final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        doReturn(product).when(productStore).saveProduct(productCaptor.capture());

        vendingMachine.setPrice(productId, 111);

        final Product forUpdate = productCaptor.getValue();
        verify(productStore).saveProduct(forUpdate);
        assertThat(forUpdate.getName(), equalTo(product.getName()));
        assertThat(forUpdate.getId(), equalTo(product.getId()));
        assertThat(forUpdate.getPrice(), equalTo(111));
        assertThat(forUpdate.getQuantity(), equalTo(product.getQuantity()));
    }

    @Test
    public void getName_should_call_datasource_and_return_name() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStore.getProduct(productId)).thenReturn(product);

        assertThat(vendingMachine.getName(productId), equalTo(product.getName()));
    }

    @Test
    public void setName_should_call_datastore_and_create_new_product_with_new_name() {
        final int productId = 1;
        final Product product = new Product(productId, "dummy", 100, 10);

        when(productStore.getProduct(productId)).thenReturn(product);
        final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        doReturn(product).when(productStore).saveProduct(productCaptor.capture());

        vendingMachine.setName(productId, "UpdatedName");

        final Product forUpdate = productCaptor.getValue();
        verify(productStore).saveProduct(forUpdate);
        assertThat(forUpdate.getName(), equalTo("UpdatedName"));
        assertThat(forUpdate.getId(), equalTo(product.getId()));
        assertThat(forUpdate.getPrice(), equalTo(product.getPrice()));
        assertThat(forUpdate.getQuantity(), equalTo(product.getQuantity()));
    }
}