package br.com.atlas.atlas_logistics.application.intent;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class ProductPatch {
    private final Optional<String> name;
    private final Optional<String> sku;
    private final Optional<BigDecimal> value;


}
