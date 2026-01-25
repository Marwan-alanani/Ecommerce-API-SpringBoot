package com.marwan.ecommerce.exception;

public class ExceptionCodes
{
    public static final String EmailExistsException = "user/emailExists";
    public static final String UserIdNotFoundException = "user/userIdNotFound";
    public static final String InvalidCredentialsException = "user/invalidCredentials";
    public static final String CategoryIdNotFoundException = "category/categoryIdNotFound";
    public static final String CategoryNameExistsException = "category/categoryNameExists";
    public static final String ProductIdNotFoundException = "product/productIdNotFound";
    public static final String SupplierNameExistsException = "supplier/supplierNameExists";
    public static final String SupplierEmailExistsException = "supplier/supplierEmailExists";
    public static final String SupplierIdNotFoundException = "supplier/supplierIdNotFound";
    public static final String PurchaseIdNotFoundException = "purchase/purchaseIdNotFound";
    public static final String InvalidTokenException = "auth/InvalidToken";
}
