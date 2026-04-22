package br.com.atlas.atlas_logistics.adapters.web.graphqlController;

import br.com.atlas.atlas_logistics.adapters.web.dtos.response.product.ProductListDTO;
import br.com.atlas.atlas_logistics.adapters.web.dtos.response.product.ReturnProductDTO;
import jakarta.validation.constraints.Positive;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class ProductGraphqlController {

    @QueryMapping
    public ReturnProductDTO getProductById (@Argument UUID id){

        return null;

    }

    @QueryMapping
    public ReturnProductDTO getProductByName (@Argument String name){

        return null;
    }

    @QueryMapping
    public ProductListDTO getAllProducts (@Argument @Positive int page, @Argument @Positive int items){

        return null;
    }

}
