package es.beatkapo.app.service;

import es.beatkapo.app.response.ProductoResponse;

public class GetProductoById extends BaseService<String, ProductoResponse>{
    @Override
    protected Class<ProductoResponse> getResponseClass() {
        return null;
    }
}
