package com.marwan.ecommerce.exception;

public class ExceptionCodes
{
    public static final String EmailExistsException = "user/emailExists";
    public static final String UserNotFoundException = "user/userNotFound";
    public static final String InvalidCredentialsException = "user/invalidCredentials";
    public static final String CategoryNotFoundException = "category/categoryNotFound";
    public static final String CategoryNameExistsException = "category/categoryNameExists";
    public static final String ProductNotFoundException = "product/productNotFound";
    public static final String SupplierNameExistsException = "supplier/supplierNameExists";
    public static final String SupplierEmailExistsException = "supplier/supplierEmailExists";
    public static final String SupplierNotFoundException = "supplier/supplierNotFound";
    public static final String PurchaseNotFoundException = "purchase/purchaseNotFound";
    public static final String InvalidTokenException = "auth/InvalidToken";
    public static final String CartNotFoundException = "cart/cartNotFound";
    public static final String CartWithUserIdNotFoundException = "cart/cartWithUserIdNotFound";
    public static final String NotEnoughProductException = "product/notEnoughProduct";
    public static final String CartAlreadyExistsForUserException = "cart/cartAlreadyExistsForUser";
    public static final String CartEmptyException = "cart/cartEmpty";
    public static final String OrderNotFoundException = "order/orderNotFound";
    public static final String PaymentException = "order/paymentException";
}

