package com.example.productviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product implements Parcelable {


    /**
     * product : {"id":"9203","name":"Glad Clingwrap Plastic Wrap, 200 sf","description":"Microwve-safe Easy to handle Contains no plasticizer","price":"2.24","unit_price":null,"product_type_id":null,"image_url":"http://i.walmartimages.com/i/p/00/01/25/87/00/0001258700020_500X500.jpg","shopping_list_item_id":null,"shopping_cart_item_id":null}
     * ProductMerchants : [{"Merchant":{"id":"23","name":"Walmart.com"},"MerchantProduct":{"id":"786804","price":"2.24","upc":"00012587000205","sku":"0001258700020","buy_url":"http://linksynergy.walmart.com/link?id=pvj9AP*vfGo&offerid=223073.12442826&type=15&murl=http%3A%2F%2Fwww.walmart.com%2Fip%2FGlad-Clingwrap-Plastic-Wrap-200-sf%2F12442826%3Fsourceid%3D0100000012230215302434","discount_percent":"0","unit_price":null},"ProductMerchant":{"id":"940928","product_id":"9203","upc":"00012587000205","sku":"0001258700020","created":"2012-01-19 11:48:23","modified":"2012-01-19 11:48:23","multiple_products_per_page":"0"}},{"Merchant":{"id":"37","name":"Soap.com"},"MerchantProduct":{"id":"1138519","price":"3.25","upc":"00012587000205","sku":"CXC-133","buy_url":"http://gan.doubleclick.net/gan_click?lid=41000000032029712&pid=CXC-133&adurl=http%3A%2F%2Fwww.soap.com%2Ftrack.aspx%3Fdest%3D%252fp%252fglad-clingwrap-clear-plastic-wrap-1ct-200-sq-ft-73826&usg=AFHzDLuxPvem_SNnlyfuZOpGNsgUQeNn4g&pubid=21000000000297346","discount_percent":"0","unit_price":null},"ProductMerchant":{"id":"2607225","product_id":"9203","upc":"00012587000205","sku":"CXC-133","created":"2012-05-12 03:42:14","modified":"2012-05-12 03:42:14","multiple_products_per_page":"0"}},{"Merchant":{"id":"7","name":"DrugStore.com"},"MerchantProduct":{"id":"18904","price":"3.79","upc":"00012587000205","sku":"194945","buy_url":"http://click.linksynergy.com/link?id=pvj9AP*vfGo&offerid=221686.194945&type=15&murl=http%3A%2F%2Fwww.drugstore.com%2Fproducts%2Fprod.asp%3Fpid%3D194945%26catid%3D184343","discount_percent":"0","unit_price":null},"ProductMerchant":{"id":"9230","product_id":"9203","upc":"00012587000205","sku":"194945","created":"2012-01-19 09:18:53","modified":"2012-01-19 09:18:53","multiple_products_per_page":"0"}}]
     */
    @SerializedName("Product")
    private ProductBean product;
    @SerializedName("ProductMerchants")
    private List<ProductMerchantsBean> productMerchants;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean Product) {
        this.product = Product;
    }

    public List<ProductMerchantsBean> getProductMerchants() {
        return productMerchants;
    }

    public void setProductMerchants(List<ProductMerchantsBean> productMerchants) {
        this.productMerchants = productMerchants;
    }

    public static class ProductBean implements Parcelable {
        /**
         * id : 9203
         * name : Glad Clingwrap Plastic Wrap, 200 sf
         * description : Microwve-safe Easy to handle Contains no plasticizer
         * price : 2.24
         * unit_price : null
         * product_type_id : null
         * image_url : http://i.walmartimages.com/i/p/00/01/25/87/00/0001258700020_500X500.jpg
         * shopping_list_item_id : null
         * shopping_cart_item_id : null
         */

        private String id;
        private String name;
        private String description;
        private double price;
        @SerializedName("unit_price")
        private Object unitPrice;
        @SerializedName("product_type_id")
        private Object productTypeId;
        @SerializedName("image_url")
        private String imageUrl;
        @SerializedName("shopping_list_item_id")
        private Object shoppingListItemId;
        @SerializedName("shopping_cart_item_id")
        private Object shoppingCartItemId;

        public ProductBean(Parcel in) {
            id = in.readString();
            name = in.readString();
            description = in.readString();
            price = Double.parseDouble(in.readString());
            imageUrl = in.readString();
        }

        public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
            @Override
            public ProductBean createFromParcel(Parcel in) {
                return new ProductBean(in);
            }

            @Override
            public ProductBean[] newArray(int size) {
                return new ProductBean[size];
            }
        };

        public ProductBean() {

        }
        private boolean isFromDatabase;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Object getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Object unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Object getProductTypeId() {
            return productTypeId;
        }

        public void setProductTypeId(Object productTypeId) {
            this.productTypeId = productTypeId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Object getShoppingListItemId() {
            return shoppingListItemId;
        }

        public void setShoppingListItemId(Object shoppingListItemId) {
            this.shoppingListItemId = shoppingListItemId;
        }

        public Object getShoppingCartItemId() {
            return shoppingCartItemId;
        }

        public void setShoppingCartItemId(Object shoppingCartItemId) {
            this.shoppingCartItemId = shoppingCartItemId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(description);
            dest.writeDouble(price);
            dest.writeString(imageUrl);
        }
    }

    public static class ProductMerchantsBean implements Parcelable{
        /**
         * Merchant : {"id":"23","name":"Walmart.com"}
         * MerchantProduct : {"id":"786804","price":"2.24","upc":"00012587000205","sku":"0001258700020","buy_url":"http://linksynergy.walmart.com/link?id=pvj9AP*vfGo&offerid=223073.12442826&type=15&murl=http%3A%2F%2Fwww.walmart.com%2Fip%2FGlad-Clingwrap-Plastic-Wrap-200-sf%2F12442826%3Fsourceid%3D0100000012230215302434","discount_percent":"0","unit_price":null}
         * ProductMerchant : {"id":"940928","product_id":"9203","upc":"00012587000205","sku":"0001258700020","created":"2012-01-19 11:48:23","modified":"2012-01-19 11:48:23","multiple_products_per_page":"0"}
         */

        @SerializedName("Merchant")
        private MerchantBean merchant;
        @SerializedName("MerchantProduct")
        private MerchantProductBean merchantProduct;
        @SerializedName("ProductMerchant")
        private ProductMerchantBean productMerchant;

        protected ProductMerchantsBean(Parcel in) {
        }

        public static final Creator<ProductMerchantsBean> CREATOR = new Creator<ProductMerchantsBean>() {
            @Override
            public ProductMerchantsBean createFromParcel(Parcel in) {
                return new ProductMerchantsBean(in);
            }

            @Override
            public ProductMerchantsBean[] newArray(int size) {
                return new ProductMerchantsBean[size];
            }
        };

        public MerchantBean getMerchant() {
            return merchant;
        }

        public void setMerchant(MerchantBean merchant) {
            this.merchant = merchant;
        }

        public MerchantProductBean getMerchantProduct() {
            return merchantProduct;
        }

        public void setMerchantProduct(MerchantProductBean merchantProduct) {
            this.merchantProduct = merchantProduct;
        }

        public ProductMerchantBean getProductMerchant() {
            return productMerchant;
        }

        public void setProductMerchant(ProductMerchantBean productMerchant) {
            this.productMerchant = productMerchant;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public static class MerchantBean implements Parcelable {
            /**
             * id : 23
             * name : Walmart.com
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.name);
            }

            public MerchantBean() {
            }

            protected MerchantBean(Parcel in) {
                this.id = in.readString();
                this.name = in.readString();
            }

            public static final Creator<MerchantBean> CREATOR = new Creator<MerchantBean>() {
                @Override
                public MerchantBean createFromParcel(Parcel source) {
                    return new MerchantBean(source);
                }

                @Override
                public MerchantBean[] newArray(int size) {
                    return new MerchantBean[size];
                }
            };
        }

        public static class MerchantProductBean implements Parcelable {
            /**
             * id : 786804
             * price : 2.24
             * upc : 00012587000205
             * sku : 0001258700020
             * buy_url : http://linksynergy.walmart.com/link?id=pvj9AP*vfGo&offerid=223073.12442826&type=15&murl=http%3A%2F%2Fwww.walmart.com%2Fip%2FGlad-Clingwrap-Plastic-Wrap-200-sf%2F12442826%3Fsourceid%3D0100000012230215302434
             * discount_percent : 0
             * unit_price : null
             */

            private String id;
            private String price;
            private String upc;
            private String sku;
            @SerializedName("buy_url")
            private String buyUrl;
            @SerializedName("discount_percent")
            private String discountPercent;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getUpc() {
                return upc;
            }

            public void setUpc(String upc) {
                this.upc = upc;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getBuyUrl() {
                return buyUrl;
            }

            public void setBuyUrl(String buyUrl) {
                this.buyUrl = buyUrl;
            }

            public String getDiscountPercent() {
                return discountPercent;
            }

            public void setDiscountPercent(String discountPercent) {
                this.discountPercent = discountPercent;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.price);
                dest.writeString(this.upc);
                dest.writeString(this.sku);
                dest.writeString(this.buyUrl);
                dest.writeString(this.discountPercent);

            }

            public MerchantProductBean() {
            }

            protected MerchantProductBean(Parcel in) {
                this.id = in.readString();
                this.price = in.readString();
                this.upc = in.readString();
                this.sku = in.readString();
                this.buyUrl = in.readString();
                this.discountPercent = in.readString();

            }

            public static final Creator<MerchantProductBean> CREATOR = new Creator<MerchantProductBean>() {
                @Override
                public MerchantProductBean createFromParcel(Parcel source) {
                    return new MerchantProductBean(source);
                }

                @Override
                public MerchantProductBean[] newArray(int size) {
                    return new MerchantProductBean[size];
                }
            };
        }

        public static class ProductMerchantBean {
            /**
             * id : 940928
             * product_id : 9203
             * upc : 00012587000205
             * sku : 0001258700020
             * created : 2012-01-19 11:48:23
             * modified : 2012-01-19 11:48:23
             * multiple_products_per_page : 0
             */

            private String id;
            @SerializedName("product_id")
            private String productId;
            private String upc;
            private String sku;
            private String created;
            private String modified;
            @SerializedName("multiple_products_per_page")
            private String multipleProductsPerPage;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getUpc() {
                return upc;
            }

            public void setUpc(String upc) {
                this.upc = upc;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }

            public String getMultipleProductsPerPage() {
                return multipleProductsPerPage;
            }

            public void setMultipleProductsPerPage(String multipleProductsPerPage) {
                this.multipleProductsPerPage = multipleProductsPerPage;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.product, flags);
        dest.writeList(this.productMerchants);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.product = in.readParcelable(ProductBean.class.getClassLoader());
        this.productMerchants = new ArrayList<ProductMerchantsBean>();
        in.readList(this.productMerchants, ProductMerchantsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
