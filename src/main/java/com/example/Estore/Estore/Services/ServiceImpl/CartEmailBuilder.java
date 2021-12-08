package com.example.Estore.Estore.Services.ServiceImpl;

public class CartEmailBuilder {
    public String buildAbandondedCartContent(String name,Long productId,String productName){

        return "<b><br><br>Dear "+name+", Greetings from Estore<b><br><br>"
                + "You have items in your cart pending to Order:<br>"
                + "<b>products in your cart are: <b>"+productName+"<br"
                +"<b> please proceed to orders";



    }
}
