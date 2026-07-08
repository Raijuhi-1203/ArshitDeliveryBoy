package codesgesture.app.arshitdeliveryboy.Models;

import java.io.Serializable;

public class OrderModel implements Serializable {
    private String id;
    private String order_id_temp;
    private String order_id;
    private String sub_order_id_temp;
    private String sub_order_id;
    private String cart_no;
    private String order_date;
    private String order_time;
    private String customer_id;
    private String customer_name;
    private String customer_mobileno;
    private String customer_email;
    private String billing_address_line1;
    private String billing_address_line2;
    private String billing_city_id = null;
    private String billing_city_name;
    private String billing_state_id = null;
    private String billing_state_name;
    private String billing_pincode;
    private String billing_landmark = null;
    private String total_order_amount;
    private String coupan_value;
    private String coupan_code;
    private String payment_mode;
    private String order_delivery_date = null;
    private String order_delivery_time = null;
    private String order_cancel_date = null;
    private String order_cancel_time = null;
    private String order_return_reason = null;
    private String order_return_comment = null;
    private String order_status;
    private String order_section;
    private String product_shipping_charge;

    public String getProduct_shipping_charge() {
        return product_shipping_charge;
    }

    public void setProduct_shipping_charge(String product_shipping_charge) {
        this.product_shipping_charge = product_shipping_charge;
    }

    public String getProduct_discount_price() {
        return product_discount_price;
    }

    public void setProduct_discount_price(String product_discount_price) {
        this.product_discount_price = product_discount_price;
    }

    private String product_discount_price;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getOrder_id_temp() {
        return order_id_temp;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getSub_order_id_temp() {
        return sub_order_id_temp;
    }

    public String getSub_order_id() {
        return sub_order_id;
    }

    public String getCart_no() {
        return cart_no;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_mobileno() {
        return customer_mobileno;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getBilling_address_line1() {
        return billing_address_line1;
    }

    public String getBilling_address_line2() {
        return billing_address_line2;
    }

    public String getBilling_city_id() {
        return billing_city_id;
    }

    public String getBilling_city_name() {
        return billing_city_name;
    }

    public String getBilling_state_id() {
        return billing_state_id;
    }

    public String getBilling_state_name() {
        return billing_state_name;
    }

    public String getBilling_pincode() {
        return billing_pincode;
    }

    public String getBilling_landmark() {
        return billing_landmark;
    }

    public String getTotal_order_amount() {
        return total_order_amount;
    }

    public String getCoupan_value() {
        return coupan_value;
    }

    public String getCoupan_code() {
        return coupan_code;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public String getOrder_delivery_date() {
        return order_delivery_date;
    }

    public String getOrder_delivery_time() {
        return order_delivery_time;
    }

    public String getOrder_cancel_date() {
        return order_cancel_date;
    }

    public String getOrder_cancel_time() {
        return order_cancel_time;
    }

    public String getOrder_return_reason() {
        return order_return_reason;
    }

    public String getOrder_return_comment() {
        return order_return_comment;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_section() {
        return order_section;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setOrder_id_temp(String order_id_temp) {
        this.order_id_temp = order_id_temp;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setSub_order_id_temp(String sub_order_id_temp) {
        this.sub_order_id_temp = sub_order_id_temp;
    }

    public void setSub_order_id(String sub_order_id) {
        this.sub_order_id = sub_order_id;
    }

    public void setCart_no(String cart_no) {
        this.cart_no = cart_no;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setCustomer_mobileno(String customer_mobileno) {
        this.customer_mobileno = customer_mobileno;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public void setBilling_address_line1(String billing_address_line1) {
        this.billing_address_line1 = billing_address_line1;
    }

    public void setBilling_address_line2(String billing_address_line2) {
        this.billing_address_line2 = billing_address_line2;
    }

    public void setBilling_city_id(String billing_city_id) {
        this.billing_city_id = billing_city_id;
    }

    public void setBilling_city_name(String billing_city_name) {
        this.billing_city_name = billing_city_name;
    }

    public void setBilling_state_id(String billing_state_id) {
        this.billing_state_id = billing_state_id;
    }

    public void setBilling_state_name(String billing_state_name) {
        this.billing_state_name = billing_state_name;
    }

    public void setBilling_pincode(String billing_pincode) {
        this.billing_pincode = billing_pincode;
    }

    public void setBilling_landmark(String billing_landmark) {
        this.billing_landmark = billing_landmark;
    }

    public void setTotal_order_amount(String total_order_amount) {
        this.total_order_amount = total_order_amount;
    }

    public void setCoupan_value(String coupan_value) {
        this.coupan_value = coupan_value;
    }

    public void setCoupan_code(String coupan_code) {
        this.coupan_code = coupan_code;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public void setOrder_delivery_date(String order_delivery_date) {
        this.order_delivery_date = order_delivery_date;
    }

    public void setOrder_delivery_time(String order_delivery_time) {
        this.order_delivery_time = order_delivery_time;
    }

    public void setOrder_cancel_date(String order_cancel_date) {
        this.order_cancel_date = order_cancel_date;
    }

    public void setOrder_cancel_time(String order_cancel_time) {
        this.order_cancel_time = order_cancel_time;
    }

    public void setOrder_return_reason(String order_return_reason) {
        this.order_return_reason = order_return_reason;
    }

    public void setOrder_return_comment(String order_return_comment) {
        this.order_return_comment = order_return_comment;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setOrder_section(String order_section) {
        this.order_section = order_section;
    }
}