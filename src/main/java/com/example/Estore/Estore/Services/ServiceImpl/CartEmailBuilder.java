package com.example.Estore.Estore.Services.ServiceImpl;

public class CartEmailBuilder {
    public String buildAbandondedCartContent(String name,String productName,int quantity,double totalPrice){

        return "<b><h4>Dear "+name+"...</h5>"
                +"<br><b><h1>Forgot Something?</h1></b>"
                + "<br><h2><b>You left this in your cart:</b></h2>"
                +"<br><h4><b>item:</b>"+productName+"</h4>"
                +"<h4><b>Qty:</b>"+quantity+"</h4>"
                +"<h4><b>totalPrice:</b>"+totalPrice+"</h4>"

                +"<br><h3><b>Would you like to complete your purchase?</b></h3>"
                +"<br><h3><b>Use discount code '10' for 10% discount on your order</b></h3>"
                +"<h3><b>Thank you for shopping with Estore</b></h3>";



    }
}
