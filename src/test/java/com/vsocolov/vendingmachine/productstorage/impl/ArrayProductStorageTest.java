package com.vsocolov.vendingmachine.productstorage.impl;

import com.vsocolov.vendingmachine.productstorage.ProductStorage;
import com.vsocolov.vendingmachine.data.Product;
import com.vsocolov.vendingmachine.enums.ExceptionType;
import com.vsocolov.vendingmachine.exceptions.VendingMachineException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;

public class ArrayProductStorageTest {

    private ProductStorage productStorage;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        productStorage = new ArrayProductStorage(2);
    }

    @Test
    public void getProduct_should_return_product_if_productid_is_valid() {
        final Product product = productStorage.getProduct(0);

        assertThat(product.getId(), equalTo(0));
        assertThat(product.getName(), is(not(emptyString())));
    }

    @Test
    public void getProduct_should_throw_exception_if_prouctid_is_invalid() {
        expectedException.expect(VendingMachineException.class);
        expectedException.expectMessage(equalTo(ExceptionType.INVALID_PRODUCT_SLOT.getMessage()));

        productStorage.getProduct(111);
    }

    @Test
    public void saveProduct_should_update_product_with_new_product_fields() {
        //update product with id = 1
        final Product product = productStorage.saveProduct(new Product(1, "Oranges", 150, 3));

        //get product with id = 1
        final Product updatedProduct = productStorage.getProduct(1);

        assertThat(product, sameInstance(updatedProduct));
    }

    @Test
    public void saveProduct_should_throw_exception_if_we_try_tu_save_product_with_invalid_id() {
        expectedException.expect(VendingMachineException.class);
        expectedException.expectMessage(equalTo(ExceptionType.INVALID_PRODUCT_SLOT.getMessage()));

        productStorage.saveProduct(new Product(100));
    }

}