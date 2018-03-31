package com.vsocolov.vendingmachine.productstore.impl;

import com.vsocolov.vendingmachine.productstore.ProductStore;
import com.vsocolov.vendingmachine.productstore.data.Product;
import com.vsocolov.vendingmachine.enums.ExceptionType;
import com.vsocolov.vendingmachine.exceptions.VendingMachineException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;

public class ArrayProductStoreTest {

    private ProductStore productStore;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        productStore = new ArrayProductStore(2);
    }

    @Test
    public void getProduct_should_return_product_if_productid_is_valid() {
        final Product product = productStore.getProduct(0);

        assertThat(product.getId(), equalTo(0));
        assertThat(product.getName(), is(not(emptyString())));
    }

    @Test
    public void getProduct_should_throw_exception_if_prouctid_is_invalid() {
        expectedException.expect(VendingMachineException.class);
        expectedException.expectMessage(equalTo(ExceptionType.INVALID_PRODUCT_SLOT.getMessage()));

        productStore.getProduct(111);
    }

    @Test
    public void saveProduct_should_update_product_with_new_product_fields() {
        //update product with id = 1
        final Product product = productStore.saveProduct(new Product(1, "Oranges", 150, 3));

        //get product with id = 1
        final Product updatedProduct = productStore.getProduct(1);

        assertThat(product, sameInstance(updatedProduct));
    }

    @Test
    public void saveProduct_should_throw_exception_if_we_try_tu_save_product_with_invalid_id() {
        expectedException.expect(VendingMachineException.class);
        expectedException.expectMessage(equalTo(ExceptionType.INVALID_PRODUCT_SLOT.getMessage()));

        productStore.saveProduct(new Product(100));
    }

}