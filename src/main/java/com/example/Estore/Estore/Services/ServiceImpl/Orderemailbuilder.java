package com.example.Estore.Estore.Services.ServiceImpl;

public class Orderemailbuilder {

    public String buildorderplacedcontent(String name,Long Id,String status,double orderamount,String address,String items){

        return "<b><br><br>Dear "+name+", Greetings from Estore<b><br><br>"
                +"<b>Your Order Details :<br><br> <b>OrderID<b> :"+Id+".<br>"
                +"<b>Current Status<b> :"+status+".<br><br>"
                +"<b>Items Ordered<b> :<br>"+items+"<br>"
                +"<b>Total Amount<b> :"+orderamount+".<br>"
                +"<b>Shipping Address<b> : "+address+".";

    }
}
