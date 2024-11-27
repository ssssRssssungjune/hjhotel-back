package com.hjhotelback.controller.payment;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.sdk.PaypalServerSDKClient;
import com.paypal.sdk.controllers.OrdersController;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.http.response.ApiResponse;
import com.paypal.sdk.models.AmountWithBreakdown;
import com.paypal.sdk.models.CheckoutPaymentIntent;
import com.paypal.sdk.models.Order;
import com.paypal.sdk.models.OrderRequest;
import com.paypal.sdk.models.OrdersCaptureInput;
import com.paypal.sdk.models.OrdersCreateInput;
import com.paypal.sdk.models.PurchaseUnitRequest;

@Controller
@RequestMapping("/")
public class CheckoutController {
	private final ObjectMapper objectMapper;
    private final PaypalServerSDKClient client;

    public CheckoutController(ObjectMapper objectMapper, PaypalServerSDKClient client) {
      this.objectMapper = objectMapper;
      this.client = client;
    }

    
    @PostMapping("/api/orders")
    public ResponseEntity < Order > createOrder(@RequestBody Map < String, Object > request) {
      try {
        String cart = objectMapper.writeValueAsString(request.get("cart"));
        Order response = createOrder(cart);
        return new ResponseEntity < > (response, HttpStatus.OK);
      } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity < > (HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    private Order createOrder(String cart) throws IOException, ApiException {
      
      OrdersCreateInput ordersCreateInput = new OrdersCreateInput.Builder(
        null,
        new OrderRequest.Builder(
          CheckoutPaymentIntent.fromString("CAPTURE"),
          Arrays.asList(
            new PurchaseUnitRequest.Builder(
              new AmountWithBreakdown.Builder(
                "USD",
                "100"
              ).build()
            )

            .build()
          )
        )

        .build()
      ).build(); 
      OrdersController ordersController = client.getOrdersController();
      ApiResponse < Order > apiResponse = ordersController.ordersCreate(ordersCreateInput);
      return apiResponse.getResult();
    }

    

    
    @PostMapping("/api/orders/{orderID}/capture")
    public ResponseEntity < Order > captureOrder(@PathVariable String orderID) {
      try {
        Order response = captureOrders(orderID);
        return new ResponseEntity < > (response, HttpStatus.OK);
      } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity < > (HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    private Order captureOrders(String orderID) throws IOException, ApiException {
      OrdersCaptureInput ordersCaptureInput = new OrdersCaptureInput.Builder(
          orderID,
          null)
        .build();
      OrdersController ordersController = client.getOrdersController();
      ApiResponse < Order > apiResponse = ordersController.ordersCapture(ordersCaptureInput);
      return apiResponse.getResult();
    } 

  
}
