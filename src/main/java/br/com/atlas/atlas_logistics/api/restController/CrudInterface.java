package br.com.atlas.atlas_logistics.api.restController;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CrudInterface<createRequest,patchRequest,listResponse,listResponseItems,returnOneResponse,id> {

    public ResponseEntity<EntityModel<returnOneResponse>> save(createRequest dto);

    public ResponseEntity<EntityModel<returnOneResponse>> update(UUID id, createRequest dto);

    public ResponseEntity<EntityModel<returnOneResponse>> patch(UUID id, patchRequest dto);

    public ResponseEntity<Void> delete(UUID id);

    public ResponseEntity<EntityModel<returnOneResponse>> getOne(UUID id);

    public ResponseEntity<CollectionModel<EntityModel<listResponseItems>>> getAll(int pages, int items);

}
