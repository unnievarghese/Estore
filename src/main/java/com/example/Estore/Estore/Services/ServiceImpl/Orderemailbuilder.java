package com.example.Estore.Estore.Services.ServiceImpl;

import java.time.LocalDate;

public class Orderemailbuilder {

    public String buildorderplacedcontent(String name, Long Id, String status, double orderamount, String address, String items,
                                          double tamount, double tax, double dc, LocalDate localDate){

        return "<b><br><br>Dear "+name+", Greetings from Estore<b><br><br>"
                +"<b>Order Details :<br><br> <b>OrderID<b> :"+Id+".<br>"
                +"<b>Current Status<b> :"+status+".<br><br>"
                +"<b>Items Ordered<b> :<br>"+items+"<br>"
                +"<b>Total Amount<b> :"+orderamount+".<br>"
                +"<b>Total Item Amount<b> :"+tamount+".<br>"
                +"<b>Total Tax<b> :"+tax+".<br>"
                +"<b>Delivery Charge<b> :"+dc+".<br><br>"
                +"<b>Delivery Date<b> :"+localDate+".<br>"
                +"<b>Shipping Address<b> : "+address+".";

    }
}
