package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.coinsstorage.CoinsStorage;
import com.vsocolov.vendingmachine.data.Product;
import com.vsocolov.vendingmachine.enums.Coin;
import com.vsocolov.vendingmachine.enums.ExceptionType;
import com.vsocolov.vendingmachine.exceptions.VendingMachineException;
import com.vsocolov.vendingmachine.productstorage.ProductStorage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.vsocolov.vendingmachine.enums.Coin.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

public class VendingMachineTest {

    private VendingMachine vendingMachine;

    private ProductStorage productStorage;

    private CoinsStorage coinsStorage;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine(5, Arrays.asList(ONE_PENNY, FIVE_PENCE, TEN_PENCE));
        productStorage = mock(ProductStorage.class);
        coinsStorage = mock(CoinsStorage.class);
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

    @Test
    public void buyProduct_should_calculate_changes_decrease_product_quantity_and_coin_amount() {
        final List<Coin> coinsToPay = Arrays.asList(ONE_POUND, FIFTY_PENCE);
        final int productSlot = 1;
        final Product product = new Product(productSlot, "KitKat", 134, 1);
        final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        when(productStorage.getProduct(productSlot)).thenReturn(product);
        when(coinsStorage.getCoinAmount(any(Coin.class))).thenReturn(10);
        doReturn(product).when(productStorage).saveProduct(productCaptor.capture());

        final List<Coin> coinsChange = vendingMachine.buyProduct(productSlot, coinsToPay);

        assertThat(coinsChange, hasSize(3));
        assertThat(coinsChange, hasItems(TEN_PENCE, FIVE_PENCE, ONE_PENNY));
        assertThat(productCaptor.getValue().getQuantity(), equalTo(product.getQuantity() - 1));
        verify(productStorage).saveProduct(any(Product.class));
        verify(coinsStorage, times(2)).increaseCoinAmount(any(Coin.class), eq(1));
        verify(coinsStorage, times(3)).decreaseCoinAmount(any(Coin.class), eq(1));
    }

    @Test
    public void buyProduct_should_throw_exception_if_product_is_not_available() {
        final List<Coin> coinsToPay = Arrays.asList(ONE_POUND, FIFTY_PENCE);
        final int productSlot = 1;
        final Product product = new Product(productSlot, "KitKat", 134, 0);

        when(productStorage.getProduct(productSlot)).thenReturn(product);

        expectedException.expect(VendingMachineException.class);
        expectedException.expectMessage(equalTo(ExceptionType.PRODUCT_NOT_AVAILABLE.getMessage()));

        vendingMachine.buyProduct(productSlot, coinsToPay);
    }

    @Test
    public void buyProduct_should_return_empty_changes_if_paid_amount_is_the_same_as_product_amount() {
        final List<Coin> coinsToPay = Arrays.asList(ONE_POUND, FIFTY_PENCE);
        final int productSlot = 1;
        final Product product = new Product(productSlot, "KitKat", 150, 1);
        final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        when(productStorage.getProduct(productSlot)).thenReturn(product);
        when(coinsStorage.getCoinAmount(any(Coin.class))).thenReturn(10);
        doReturn(product).when(productStorage).saveProduct(productCaptor.capture());

        final List<Coin> coinsChange = vendingMachine.buyProduct(productSlot, coinsToPay);

        assertThat(coinsChange, is(empty()));
        assertThat(productCaptor.getValue().getQuantity(), equalTo(product.getQuantity() - 1));
        verify(productStorage).saveProduct(any(Product.class));
        verify(coinsStorage, times(2)).increaseCoinAmount(any(Coin.class), eq(1));
        verify(coinsStorage, never()).decreaseCoinAmount(any(Coin.class), eq(1));
    }

    @Test
    public void buyProduct_should_throw_exception_if_there_are_not_enough_money_to_buy_product() {
        final List<Coin> coinsToPay = Collections.singletonList(ONE_POUND);
        final int productSlot = 1;
        final Product product = new Product(productSlot, "KitKat", 134, 1);

        when(productStorage.getProduct(productSlot)).thenReturn(product);

        expectedException.expect(VendingMachineException.class);
        expectedException.expectMessage(equalTo(ExceptionType.NOT_ENOUGH_MONEY.getMessage()));

        vendingMachine.buyProduct(productSlot, coinsToPay);
    }

    @Test
    public void buyProduct_should_throw_exception_if_there_are_not_enough_change_to_give_customer() {
        final List<Coin> coinsToPay = Arrays.asList(ONE_POUND, FIFTY_PENCE);
        final int productSlot = 1;
        final Product product = new Product(productSlot, "KitKat", 134, 1);

        when(productStorage.getProduct(productSlot)).thenReturn(product);

        expectedException.expect(VendingMachineException.class);
        expectedException.expectMessage(equalTo(ExceptionType.NOT_ENOUGH_CHANGE.getMessage()));

        vendingMachine.buyProduct(productSlot, coinsToPay);
    }
}