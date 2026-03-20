package br.com.atlas.atlas_logistics.adapters.web.OpenApi;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Operation
@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/hal+json", array = @ArraySchema(schema = @Schema())))
@ApiResponses({@ApiResponse(responseCode = "401",description = "${atlas.api.error.401}",content = @Content),
        @ApiResponse(responseCode = "403",description = "${atlas.api.error.403}",content = @Content),
        @ApiResponse(responseCode = "404",description = "${atlas.api.error.404}",content = @Content),
        @ApiResponse(responseCode = "500",description = "${atlas.api.error.500}",content = @Content)})

public @interface AtlasMultipleReturnOperation {
    @AliasFor (annotation = Operation.class, attribute = "summary")
    String summary() default "";

    @AliasFor (annotation = ApiResponse.class, attribute = "description")
    String description() default "Operação concluida com sucesso";

    @AliasFor (annotation = Schema.class, attribute = "implementation")
    Class<?> implementation() default Void.class;


}
